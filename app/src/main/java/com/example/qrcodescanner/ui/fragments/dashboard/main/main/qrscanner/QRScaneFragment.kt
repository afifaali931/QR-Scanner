package com.example.qrcodescanner.ui.fragments.dashboard.main.main.qrscanner

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentHistoryBinding
import com.example.qrcodescanner.databinding.FragmentQRScaneBinding
import com.example.qrcodescanner.ui.fragments.dashboard.main.create.CreateFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import java.io.ByteArrayOutputStream


class QRScaneFragment : Fragment() {

private lateinit var binding: FragmentQRScaneBinding
    private lateinit var codeScanner: CodeScanner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQRScaneBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scannerView = binding.scannerView
        val activity = requireActivity()

        codeScanner = CodeScanner(activity, scannerView).apply {
            decodeCallback = DecodeCallback { result ->
                val scannedText = result.text


                val bitmap = generateQRBitmap(scannedText)


                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                activity.runOnUiThread {
                    val bundle = Bundle().apply {
                        putString("qr_result", scannedText)
                        putByteArray("qr_bitmap", byteArray)
                    }
                    findNavController().navigate(R.id.action_mainFragment_to_QRResultFragment, bundle)
                }
            }

            errorCallback = ErrorCallback { error ->
                activity.runOnUiThread {
                    Toast.makeText(activity, "Camera error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            }
        }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
//        val activity = requireActivity()
//
//
//
//        codeScanner = CodeScanner(activity, scannerView).apply {
//            decodeCallback = DecodeCallback { result ->
//
//                Log.e("QRScaneFragment", "onViewCreated: result.text ${result.text} " )
//                Log.e("QRScaneFragment", "onViewCreated: result.resultPoints ${result.resultPoints} " )
//                Log.e("QRScaneFragment", "onViewCreated: result.resultMetadata ${result.resultMetadata.values} " )
//                Log.e("QRScaneFragment", "onViewCreated: result.timestamp ${result.timestamp} " )
//
//                activity.runOnUiThread {
//                    findNavController().navigate(R.id.action_mainFragment_to_QRResultFragment)
//                }
//            }
//
//            errorCallback = ErrorCallback { error ->
//                activity.runOnUiThread {
//                    Toast.makeText(activity, "Camera error: ${error.message}", Toast.LENGTH_LONG).show()
//                }
//            }
//        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun generateQRBitmap(content: String): Bitmap {
        val size = 512
        val hints = hashMapOf<EncodeHintType, Any>(
            EncodeHintType.MARGIN to 1,
            EncodeHintType.CHARACTER_SET to "UTF-8"
        )

        val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}