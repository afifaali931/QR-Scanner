package com.example.qrcodescanner.ui.fragments.dashboard.main.create.qrcreated

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.print.PrintHelper
import com.example.qrcodescanner.R
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import com.example.qrcodescanner.databinding.FragmentCreatedQRBinding
import com.example.qrcodescanner.utils.Constants.QR_CREATED
import com.example.qrcodescanner.viewmodel.QrCodeViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class CreatedQRFragment : Fragment() {

    private lateinit var binding: FragmentCreatedQRBinding
    private val args: CreatedQRFragmentArgs by navArgs()
    private val qrCodeViewModel: QrCodeViewModel by viewModel()
    private val timestamp = System.currentTimeMillis()

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatedQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        val qrType = args.qrType.ifEmpty { "QR Code" }
        Log.e("CreatedQRFragment", "onViewCreated: ${qrType}" )
        val qrData = args.qrData
        Log.e("CreatedQRFragment", "onViewCreated:..... ${qrData}" )

        binding.tvQrType.text = qrType
        handleDataDesc(qrData, qrType)
        generateQrCodeBitmap(qrData)

        val qrBitmap = generateQrCodeBitmap(qrData)
        binding.imgQrCode.setImageBitmap(qrBitmap)


        binding.tvSeeMore.setOnClickListener {
            isExpanded = !isExpanded
            binding.tvQrContent.maxLines = if (isExpanded) Int.MAX_VALUE else 3
            binding.tvSeeMore.text = if (isExpanded) "See Less" else "See More"
        }

        val source = args.source
        if (source == "history") {
            binding.layoutSave.visibility = View.GONE
            Log.d("CreatedQRFragment", "User came from HistoryFragment, hiding Save button")
        } else {
            Log.d("CreatedQRFragment", "User came from CreateFragment, showing Save button")
        }


        binding.layoutShare.setOnClickListener{
            shareQrCode()
        }


        binding.layoutSave.setOnClickListener {
            saveQrCodeToGalleryAndDatabase()
        }
        binding.layoutStyle.setOnClickListener {

            findNavController().navigate(R.id.action_createdQRFragment_to_styleFragment)
            Log.e("CreatedQRFragment", "navigateToStyleFragmentfrom Created Fragment", )
        }

        binding.layoutPrint.setOnClickListener {
            printQrCode()
        }

    }

    private fun generateQrCodeBitmap(data: String): Bitmap {
        val size = 512
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size)
        return BarcodeEncoder().createBitmap(bitMatrix)
    }

    private fun saveQrCodeToGalleryAndDatabase() {
        val drawable = binding.imgQrCode.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val filename = "QR_${timestamp}.png"
            val file = File(requireContext().filesDir, filename)

            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }

//            val qrType = binding.tvQrType.text.toString()
//            val qrContent = binding.tvQrContent.text.toString()
//            val createdDate = getCurrentDateTime()

            val qrCode = SavedQrCodeEntity(
                imagePath = file.absolutePath,
                qrType = binding.tvQrType.text.toString(),
                content = binding.tvQrContent.text.toString(),
                createdDate = getCurrentDateTime(),
                isBookmarked = false,
                generatedQrType = QR_CREATED
            )

            qrCodeViewModel.saveQrCode(qrCode)
            Toast.makeText(requireContext(), "QR Code saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "QR Code not available to save", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(java.util.Date())
    }


    fun handleDataDesc(data: String, dataType: String){
        if (dataType == "Email"){
            val desc = parseMailToUri(data)
            Log.e("CreatedQRFragment", "handleDataDesc:..... ${desc}" )
            binding.imgTypeIcon.setImageResource(R.drawable.ic_email)
            binding.tvQrContent.text=desc

        } else if (dataType == "Website"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_website)
            binding.tvQrContent.text= data

        } else if (dataType == "Wifi"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_wifi)
            binding.tvQrContent.text= data

        } else if (dataType == "Contact"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_contact)
            binding.tvQrContent.text= data

        } else if (dataType == "Phone"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_cellphone)
            binding.tvQrContent.text= data

        } else if (dataType == "SMS"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_sms)
            binding.tvQrContent.text= data

        } else if (dataType == "MyCard"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_mycard)
            binding.tvQrContent.text= data

        } else if (dataType == "Calendar"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_calendar)
            binding.tvQrContent.text= data

        } else if (dataType == "GPS"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_gps)
            binding.tvQrContent.text= data

        } else if (dataType == "Clipboard"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_clipboard)
            binding.tvQrContent.text= data

        } else if (dataType == "Text"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_text)
            binding.tvQrContent.text= data

        } else if (dataType == "Item Code"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_itemcode)
            binding.tvQrContent.text= data

        } else if (dataType == "Facebook"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_facebook)
            binding.tvQrContent.text= data

        } else if (dataType == "Youtube"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_youtube)
            binding.tvQrContent.text= data

        } else if (dataType == "Whatsapp"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_whatsapp)
            binding.tvQrContent.text= data

        } else if (dataType == "Paypal"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_paypal)
            binding.tvQrContent.text= data

        } else if (dataType == "Twitter"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_twitter)
            binding.tvQrContent.text= data

        } else if (dataType == "Instagram"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_instagram)
            binding.tvQrContent.text= data

        } else if (dataType == "Spotify"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_spotify)
            binding.tvQrContent.text= data

        } else if (dataType == "Tiktok"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_tiktok)
            binding.tvQrContent.text= data

        } else if (dataType == "Viber"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_viber)
            binding.tvQrContent.text= data

        } else if (dataType == "Discord"){
            binding.imgTypeIcon.setImageResource(R.drawable.ic_discord)
            binding.tvQrContent.text= data
        }
        else
        {
            binding.tvQrContent.text= data
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun printQrCode() {
        val drawable = binding.imgQrCode.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val printHelper = PrintHelper(requireContext())
            printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
            printHelper.printBitmap("QR_Code", bitmap)
        } else {
            Toast.makeText(requireContext(), "QR Code not available to print", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareQrCode() {
        val drawable = binding.imgQrCode.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "qr_code_${timestamp}.png")
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


    fun parseMailToUri(uri: String): String {
        val cleanedUri = uri.removePrefix("mailto:")
        val parts = cleanedUri.split("?")
        val email = parts[0]

        var subject = ""
        var body = ""

        if (parts.size > 1) {
            val query = parts[1]
            val params = query.split("&")
            for (param in params) {
                val keyValue = param.split("=")
                if (keyValue.size == 2) {
                    when (keyValue[0]) {
                        "subject" -> subject = Uri.decode(keyValue[1])
                        "body" -> body = Uri.decode(keyValue[1])
                    }
                }
            }
        }

        return """
        Mail   : $email
        Subject: $subject
        Body   : $body
    """.trimIndent()
    }
}

//val  options = createQrVectorOptions {
//
//            padding = .125f
//
////            background {
////                drawable = ContextCompat
////                    .getDrawable(context, R.drawable.ic_frame_one)
////            }
//
//            logo {
//                drawable = ContextCompat
//                    .getDrawable(context, R.drawable.ic_scan_round)
//                size = .25f
//                padding = QrVectorLogoPadding.Natural(.2f)
//                shape = QrVectorLogoShape
//                    .Circle
//            }
//            colors {
//                dark = QrVectorColor
//                    .Solid(Color(0xff345288))
//                ball = QrVectorColor.Solid(
//                    ContextCompat.getColor(context, R.color.green_200)
//                )
//                frame = QrVectorColor.LinearGradient(
//                    colors = listOf(
//                        0f to android.graphics.Color.RED,
//                        1f to android.graphics.Color.BLUE,
//                    ),
//                    orientation = QrVectorColor.LinearGradient
//                        .Orientation.LeftDiagonal
//                )
//            }
//            shapes {
//                darkPixel = QrVectorPixelShape
//                    .RoundCorners(.5f)
//                ball = QrVectorBallShape
//                    .RoundCorners(.25f)
//                frame = QrVectorFrameShape
//                    .RoundCorners(.25f)
//            }
//        }
//
//        val data = QrData.Url(qrType)
//
//        val drawable : Drawable = QrCodeDrawable(data, options)
//