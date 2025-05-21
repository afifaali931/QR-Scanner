package com.example.qrcodescanner.ui.fragments.dashboard.main.style


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.qrcodescanner.databinding.FragmentStyleBinding
import com.example.qrcodescanner.model.QrStyleModel
import com.example.qrcodescanner.ui.adapter.style.StyleAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.create.qrcreated.CreatedQRFragmentArgs
import com.github.alexzhirkevich.customqrgenerator.style.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class StyleFragment : Fragment() {

    private lateinit var binding: FragmentStyleBinding
//    private val args: CreatedQRFragmentArgs by navArgs()

//    private val qrStyles = listOf(
//
//        QrStyleModel(0xFF0EAB9B.toInt(), 0xFF0EAB9B.toInt(), 0xFFFFFFFF.toInt()),
//
//        QrStyleModel(0xFFF6B54A.toInt(), 0xFFE14D2A.toInt(), 0xFFFFFFFF.toInt()),
//        QrStyleModel(0xFFC257E9.toInt(), 0xFFF2515D.toInt(), 0xFFFFFFFF.toInt()),
//        QrStyleModel(0xFF2A9D8F.toInt(), 0xFF009EFD.toInt(), 0xFFFFFFFF.toInt()),
//        QrStyleModel(0xFF2AF958.toInt(), 0xFF009EFD.toInt(), 0xFFFFFFFF.toInt())
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStyleBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val qrData = args.qrData.ifEmpty { "QR Code"  }
//        Log.e("StyleFragment", "onViewCreated: Data is  called ${qrData}", )
//
//        val qrType = args.qrType.ifEmpty { "QR Code"  }
//        Log.e("StyleFragment", "onViewCreated: qrType is  called...... ${qrType}", )
//
//        generateAndSetQRCode(qrData, null)
//        binding.tvQrType.text = qrType
//        setupStyleRecyclerView(qrData)
//    }
//
//    private fun setupStyleRecyclerView(qrData: String) {
//        val adapter = StyleAdapter(qrStyles) { selectedStyle ->
//
//            findNavController().previousBackStackEntry?.savedStateHandle
//                ?.set("selectedStyle", selectedStyle)
//            findNavController().popBackStack()
//        }
//
//        binding.recyclerViewSavedQr.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerViewSavedQr.adapter = adapter
//
//
//        findNavController().currentBackStackEntry?.savedStateHandle
//            ?.getLiveData<QrStyleModel>("selectedStyle")
//            ?.observe(viewLifecycleOwner) { selectedStyle ->
//                generateAndSetQRCode(qrData, selectedStyle)
//            }
//    }
//
//    private fun generateAndSetQRCode(data: String, style: QrStyleModel?) {
//        val qrBitmap = if (style != null) {
//            generateStyledQrCodeBitmap(data, style)
//        } else {
//            generateDefaultQrCodeBitmap(data)
//        }
//        binding.imgQrCode.setImageBitmap(qrBitmap)
//    }
//
//    private fun generateDefaultQrCodeBitmap(data: String): Bitmap {
//        val size = 512
//        val bitMatrix = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size)
//        return BarcodeEncoder().createBitmap(bitMatrix)
//    }
//
//    private fun generateStyledQrCodeBitmap(data: String, style: QrStyleModel): Bitmap {
//        val size = 512
//        val bitMatrix = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size)
//        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
//
//        for (x in 0 until size) {
//            for (y in 0 until size) {
//                val color = if (bitMatrix[x, y]) style.fgStartColor else style.backgroundColor
//                bitmap.setPixel(x, y, color)
//            }
//        }
//
//        return bitmap
//    }
}






//class StyleFragment : Fragment() {
//
//    private lateinit var binding: FragmentStyleBinding
//
//        val qrStyles = listOf(
//        QrStyleModel(Color(0xF6B54A), Color(0xFFFFFFFF)),
//        QrStyleModel(Color(0x478EC4), Color(0xFFFFFFFF)),
//        QrStyleModel(Color(0x2AF598+0x009EFD), Color(0xFFFFFFFF)),
//        QrStyleModel(Color(0xFF00FF00), Color(0xFFFFFFFF)),
//        QrStyleModel(Color(0xFFFF00FF), Color(0xFFFFFFFF)),
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentStyleBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        val adapter = StyleAdapter(qrStyles) { selectedStyle ->
//            findNavController().previousBackStackEntry?.savedStateHandle
//                ?.set("selectedStyle", selectedStyle)
//            findNavController().popBackStack()
//        }
//
//        binding.recyclerViewSavedQr.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerViewSavedQr.adapter = adapter
//    }
//
//}



