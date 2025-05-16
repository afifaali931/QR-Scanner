package com.example.qrcodescanner.ui.fragments.dashboard.main.main.qrscanner

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import com.example.qrcodescanner.databinding.FragmentQRResultBinding
import com.example.qrcodescanner.databinding.FragmentQRScaneBinding
import com.example.qrcodescanner.utils.Constants.QR_CREATED
import com.example.qrcodescanner.utils.Constants.QR_SCANNED
import com.example.qrcodescanner.viewmodel.QrCodeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class QRResultFragment : Fragment() {

    private lateinit var binding: FragmentQRResultBinding
    private val qrCodeViewModel: QrCodeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQRResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        displayResult()
        binding.btnSave.setOnClickListener {
            saveQrCode()
        }


        binding.btnShare.setOnClickListener {
            shareQrCode()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayResult() {
        val resultText = arguments?.getString("qr_result")
        val byteArray = arguments?.getByteArray("qr_bitmap")

        if (resultText != null) {
            binding.tvQrResult.text = resultText
        }

        if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.ivQrScanned.setImageBitmap(bitmap)
        }
    }

    private fun saveQrCode() {
        val drawable = binding.ivQrScanned.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap


            val filename = "QR_${System.currentTimeMillis()}.png"
            val file = File(requireContext().filesDir, filename)
            try {
                FileOutputStream(file).use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }


                val qrContent = binding.tvQrResult.text.toString()
                val createdDate = getCurrentDateTime()
                val qrType = "QR-Code"

                val qrCode = SavedQrCodeEntity(
                    imagePath = file.absolutePath,
                    qrType = qrType,
                    content = qrContent,
                    createdDate = createdDate,
                    isBookmarked = false,
                    generatedQrType = QR_SCANNED
                )

                qrCodeViewModel.saveQrCode(qrCode)
                Toast.makeText(requireContext(), "QR Code saved!", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to save QR Code", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "QR Code image not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(java.util.Date())
    }


    private fun shareQrCode() {
        val drawable = binding.ivQrScanned.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "qr_code_${System.currentTimeMillis()}.png")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val contentResolver = requireContext().contentResolver
            val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (imageUri != null) {
                contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(imageUri, contentValues, null, null)

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    putExtra(Intent.EXTRA_TEXT, "Here's the QR code I created!")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                startActivity(Intent.createChooser(shareIntent, "Share QR Code via"))
            } else {
                Toast.makeText(requireContext(), "Unable to share QR Code", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "QR Code not available to share", Toast.LENGTH_SHORT).show()
        }
    }

}
