package com.example.qrcodescanner.ui.fragments.dashboard.main.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Index
import androidx.room.util.TableInfo
import androidx.viewpager2.widget.ViewPager2
import com.example.qrcodescanner.R
import com.example.qrcodescanner.data.sharedviewmodel.SharedViewModel
import com.example.qrcodescanner.databinding.FragmentMainBinding
import com.example.qrcodescanner.ui.adapter.ViewPagerAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.create.CreateFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.history.HistoryFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.main.qrscanner.QRScaneFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.settings.SettingsFragment
import com.example.qrcodescanner.ui.fragments.dashboard.main.style.StyleFragment
import com.example.qrcodescanner.utils.showPermissionRationaleDialog
import com.example.qrcodescanner.utils.showScanInstructionsDialog


//class MainFragment : Fragment() {
//
//    lateinit var binding: FragmentMainBinding
//
//    val sharedViewModel: SharedViewModel by viewModels()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//
//        binding = FragmentMainBinding.inflate(inflater, container, false)
//        return  binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        binding.bottomNavigationView.background = null
//        replaceFragment(QRScaneFragment())
//
//        handleFabVisibility(false)
//
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.create   -> findNavController().navigate(R.id.createFragment)
//                R.id.history  -> findNavController().navigate(R.id.historyFragment)
//                R.id.style    -> findNavController().navigate(R.id.styleFragment)
//                R.id.settings -> findNavController().navigate(R.id.settingsFragment)
//            }
//            handleFabVisibility(true)
//            true
//        }
//
//        binding.fab.setOnClickListener{
//            replaceFragment(QRScaneFragment())
//            handleFabVisibility(false)
//        }
//    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.viewPager, fragment)
//            .commit()
//    }
//
//    private fun handleFabVisibility(show: Boolean){
//        if (show){
//
//            binding.fab.visibility = View.VISIBLE
//
//        } else{
//
//            binding.bottomNavigationView.menu.setGroupCheckable(0, true, false)
//            for (i in 0 until binding.bottomNavigationView.menu.size()) {
//                binding.bottomNavigationView.menu.getItem(i).isChecked = false
//            }
//            binding.bottomNavigationView.menu.setGroupCheckable(0, true, true)
//        }
//    }
//}












class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

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

            handleFabVisibility(true)

        }


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.e("MainFragment", "onPageSelected:............. position ${position}", )

                if (position != 2) {
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                    handleFabVisibility(true)
                } else {
                    handleFabVisibility(false)
                    deselectBottomNav()
                }
            }
        })

        binding.viewPager.setCurrentItem(2, false)


//        findNavController().navigate(R.id.action_mainFragment_to_createFragment2)
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
