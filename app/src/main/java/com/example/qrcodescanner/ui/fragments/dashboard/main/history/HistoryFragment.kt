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
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import com.example.qrcodescanner.databinding.FragmentHistoryBinding
import com.example.qrcodescanner.ui.adapter.savedqrcode.HistoryAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.main.MainFragmentDirections
import com.example.qrcodescanner.viewmodel.QrCodeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


//class HistoryFragment : Fragment() {
//
//    private lateinit var binding: FragmentHistoryBinding
//    private val viewModel: QrCodeViewModel by viewModel()
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
//        setupRecyclerView()
//        setupButtonListeners()
//        highlightSelectedButton(binding.btnCreate)
//        observeCreatedQrCodes()
//    }
//
//    private fun setupRecyclerView() {
//
//        adapter = HistoryAdapter(
//            onBookmarkClick = { viewModel.addedToBookmark(it) },
//            onDeleteClick = { viewModel.deleteQrCode(it) },
//            onItemClick = { qrEntity ->
//                val action = MainFragmentDirections
//                    .actionMainFragmentToCreatedQRFragment(
//                        qrType = qrEntity.qrType,
//                        qrData = qrEntity.content,
//                        source = "history"
//                    )
//                parentFragment?.findNavController()?.navigate(action)
//            }
//        )
//
//        binding.recyclerViewSavedQr.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerViewSavedQr.adapter = adapter
//
//    }
//
//
//    private fun setupButtonListeners() {
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
//            showEmptyStateIfNeeded(it, R.drawable.no_create_history, )
//        }
//    }
//
//    private fun observeScannedQrCodes() {
//        viewModel.scannedQrCodes.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//            showEmptyStateIfNeeded(it, R.drawable.no_scan_history)
//        }
//    }
//
//    private fun observeBookmarkedQrCodes() {
//        viewModel.bookmarkedQrCodes.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//            showEmptyStateIfNeeded(it, R.drawable.no_bookmark_history)
//        }
//    }
//
//    private fun showEmptyStateIfNeeded(list: List<SavedQrCodeEntity>, imageResId: Int) {
//        if (list.isEmpty()) {
//            binding.emptyStateLayout.visibility = View.VISIBLE
//            binding.recyclerViewSavedQr.visibility = View.GONE
//            binding.emptyStateImage.setImageResource(imageResId)
//
//        } else {
//            binding.emptyStateLayout.visibility = View.GONE
//            binding.recyclerViewSavedQr.visibility = View.VISIBLE
//        }
//    }
//
//
//    private fun highlightSelectedButton(selectedButton: Button) {
//        val buttons = listOf(binding.btnCreate, binding.btnScan, binding.btnBookmark)
//        buttons.forEach { button ->
//            val colorRes = if (button == selectedButton) R.color.green_200 else R.color.gray
//            button.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
//        }
//    }
//}




class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: QrCodeViewModel by viewModel()
    private lateinit var adapter: HistoryAdapter


    private enum class Tab {
        CREATE, SCAN, BOOKMARK
    }

    private var selectedTab = Tab.CREATE

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
        selectTab(Tab.CREATE)
        observeAllQrCodes()
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
            }
        )
        binding.recyclerViewSavedQr.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSavedQr.adapter = adapter
    }

    private fun setupButtonListeners() {
        binding.btnCreate.setOnClickListener {
            selectTab(Tab.CREATE)
        }

        binding.btnScan.setOnClickListener {
            selectTab(Tab.SCAN)
        }

        binding.btnBookmark.setOnClickListener {
            selectTab(Tab.BOOKMARK)
        }
    }

    private fun selectTab(tab: Tab) {
        selectedTab = tab

        when (tab) {
            Tab.CREATE -> {
                highlightSelectedButton(binding.btnCreate)
                viewModel.createdQrCodes.value?.let {
                    adapter.submitList(it)
                    showEmptyStateIfNeeded(it, R.drawable.no_create_history)
                }
            }

            Tab.SCAN -> {
                highlightSelectedButton(binding.btnScan)
                viewModel.scannedQrCodes.value?.let {
                    adapter.submitList(it)
                    showEmptyStateIfNeeded(it, R.drawable.no_scan_history)
                }
            }

            Tab.BOOKMARK -> {
                highlightSelectedButton(binding.btnBookmark)
                viewModel.bookmarkedQrCodes.value?.let {
                    adapter.submitList(it)
                    showEmptyStateIfNeeded(it, R.drawable.no_bookmark_history)
                }
            }
        }
    }

    private fun observeAllQrCodes() {
        viewModel.createdQrCodes.observe(viewLifecycleOwner) {
            if (selectedTab == Tab.CREATE) {
                adapter.submitList(it)
                showEmptyStateIfNeeded(it, R.drawable.no_create_history)
            }
        }

        viewModel.scannedQrCodes.observe(viewLifecycleOwner) {
            if (selectedTab == Tab.SCAN) {
                adapter.submitList(it)
                showEmptyStateIfNeeded(it, R.drawable.no_scan_history)
            }
        }

        viewModel.bookmarkedQrCodes.observe(viewLifecycleOwner) {
            if (selectedTab == Tab.BOOKMARK) {
                adapter.submitList(it)
                showEmptyStateIfNeeded(it, R.drawable.no_bookmark_history)
            }
        }
    }

    private fun showEmptyStateIfNeeded(list: List<SavedQrCodeEntity>, imageResId: Int) {
        if (list.isEmpty()) {
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.recyclerViewSavedQr.visibility = View.GONE
            binding.emptyStateImage.setImageResource(imageResId)
        } else {
            binding.emptyStateLayout.visibility = View.GONE
            binding.recyclerViewSavedQr.visibility = View.VISIBLE
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
