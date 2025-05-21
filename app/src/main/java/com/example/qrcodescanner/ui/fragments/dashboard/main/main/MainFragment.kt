package com.example.qrcodescanner.ui.fragments.dashboard.main.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentMainBinding
import com.example.qrcodescanner.ui.adapter.ViewPagerAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.create.CreateFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.history.HistoryFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.main.qrscanner.QRScaneFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.settings.SettingsFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.style.StyleFragment
import com.example.qrcodescanner.utils.showPermissionRationaleDialog
import com.example.qrcodescanner.utils.showScanInstructionsDialog

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var isFirstOpen = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        checkAndRequestPermissions()
        binding.bottomNavigationView.background = null
        setupViewPager()
        handleFabVisibility(true)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.create -> binding.viewPager.currentItem = 0

                R.id.history -> binding.viewPager.currentItem = 1

                R.id.style -> binding.viewPager.currentItem = 3
                R.id.settings -> binding.viewPager.currentItem = 4
            }
            handleFabVisibility(true)
            true
        }


        binding.fab.setOnClickListener {
            binding.viewPager.currentItem = 2


        }


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.e("MainFragment", "onPageSelected:............. position ${position}", )

                if (position != 2) {
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                    handleFabVisibility(true)
                } else {
                    Log.e("MainFragment", "onPageSelected:............. isFirstOpen ${isFirstOpen}", )

                    handleFabVisibility(isFirstOpen)
                    deselectBottomNav()
                    isFirstOpen = false
                }
            }
        })

        binding.viewPager.setCurrentItem(2, false)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val currentItem = binding.viewPager.currentItem
            if (currentItem != 2) {
                binding.viewPager.setCurrentItem(2, false)
            } else {
                requireActivity().finish()
            }
        }

    }

    private fun setupViewPager() {
        val fragments = listOf(
            CreateFragment(),
            HistoryFragment(),
            QRScaneFragment(),
            StyleFragment(),
            SettingsFragment()
        )

        viewPagerAdapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.offscreenPageLimit = fragments.size
    }

    private fun handleFabVisibility(show: Boolean) {
        Log.e("MainFragment", "onPageSelected handleFabVisibility called  ${show}", )

        binding.fab.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun deselectBottomNav() {
        binding.bottomNavigationView.menu.setGroupCheckable(0, true, false)
        for (i in 0 until binding.bottomNavigationView.menu.size()) {
            binding.bottomNavigationView.menu.getItem(i).isChecked = false
        }
        binding.bottomNavigationView.menu.setGroupCheckable(0, true, true)
    }

    private fun checkAndRequestPermissions() {
        val needed = mutableListOf(android.Manifest.permission.CAMERA).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                add(android.Manifest.permission.RECORD_AUDIO)
            }
        }
        val toRequest = needed.filter { perm ->
            ContextCompat.checkSelfPermission(requireContext(), perm) !=
                    PackageManager.PERMISSION_GRANTED
        }

        if (toRequest.isNotEmpty()) {
            permissionLauncher.launch(toRequest.toTypedArray())
        }
    }


    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val cameraGranted = results[android.Manifest.permission.CAMERA] == true
        val audioGranted  = results[android.Manifest.permission.RECORD_AUDIO] == true

        if (!cameraGranted || !audioGranted) {

            showPermissionRationaleDialog { checkAndRequestPermissions() }

        } else {
            showScanInstructionsDialog()
        }
    }

}
