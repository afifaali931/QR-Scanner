package com.example.qrcodescanner.ui.fragments.dashboard.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentHistoryBinding
import com.example.qrcodescanner.ui.adapter.savedqrcode.HistoryAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.main.MainFragmentDirections
import com.example.qrcodescanner.viewmodel.QrCodeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: QrCodeViewModel by viewModel()
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtonListeners()
        highlightSelectedButton(binding.btnCreate)
        observeCreatedQrCodes()
    }

    private fun setupRecyclerView() {

        adapter = HistoryAdapter(
            onBookmarkClick = { viewModel.addedToBookmark(it) },
            onDeleteClick = { viewModel.deleteQrCode(it) },
            onItemClick = { qrEntity ->
                val action = MainFragmentDirections
                    .actionMainFragmentToCreatedQRFragment(
                        qrType = qrEntity.qrType,
                        qrData = qrEntity.content,
                        source = "history"
                    )
                parentFragment?.findNavController()?.navigate(action)
//                parentFragment?.findNavController()?.navigate(action)
            }
        )

        binding.recyclerViewSavedQr.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSavedQr.adapter = adapter

    }


    private fun setupButtonListeners() {
        binding.btnCreate.setOnClickListener {
            highlightSelectedButton(binding.btnCreate)
            observeCreatedQrCodes()
        }

        binding.btnScan.setOnClickListener {
            highlightSelectedButton(binding.btnScan)
            observeScannedQrCodes()
        }

        binding.btnBookmark.setOnClickListener {
            highlightSelectedButton(binding.btnBookmark)
            observeBookmarkedQrCodes()
        }
    }

    private fun observeCreatedQrCodes() {
        viewModel.createdQrCodes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun observeScannedQrCodes() {
        viewModel.scannedQrCodes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun observeBookmarkedQrCodes() {
        viewModel.bookmarkedQrCodes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun highlightSelectedButton(selectedButton: Button) {
        val buttons = listOf(binding.btnCreate, binding.btnScan, binding.btnBookmark)
        buttons.forEach { button ->
            val colorRes = if (button == selectedButton) R.color.green_200 else R.color.gray
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
        }
    }
}








//class HistoryFragment : Fragment() {
//
//    private lateinit var binding: FragmentHistoryBinding
//    private val qrCodeViewModel: QrCodeViewModel by viewModel()
//    private lateinit var adapter: HistoryAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentHistoryBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.recyclerViewSavedQr.layoutManager = GridLayoutManager(requireContext(), 3)
//        adapter = HistoryAdapter()
//
//        binding.btnCreate.setOnClickListener {
//            highlightSelectedButton(binding.btnCreate)
//            observeCreatedQrCodes()
//        }
//
//        binding.btnScan.setOnClickListener {
//            highlightSelectedButton(binding.btnScan)
//            observeScannedQrCodes()
//        }
//
//        binding.btnBookmark.setOnClickListener {
//            highlightSelectedButton(binding.btnBookmark)
//            observeBookmarkedQrCodes()
//        }
//    }
//
//    private fun observeCreatedQrCodes() {
//        viewModel.createdQrCodes.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
//    }
//    private fun observeScannedQrCodes() {
//        viewModel.scannedQrCodes.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
//    }
//
//    private fun observeBookmarkedQrCodes() {
//        viewModel.bookmarkedQrCodes.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
//    }
//
//    private fun highlightSelectedButton(selectedButton: Button) {
//        val buttons = listOf(binding.btnCreate, binding.btnScan, binding.btnBookmark)
//
//        buttons.forEach { button ->
//            val colorRes = if (button == selectedButton) R.color.green_200 else R.color.gray
//            button.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
//        }
//    }
//
//}