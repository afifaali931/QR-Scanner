package com.example.qrcodescanner.ui.fragments.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentContactBinding
import com.example.qrcodescanner.databinding.FragmentGPSBinding


class GPSFragment : Fragment() {
    private lateinit var binding: FragmentGPSBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGPSBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupCreateButton()

        binding.btnCreate.isEnabled = false
        binding.btnCreate.alpha = 0.5f

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val gps = binding.etGps.text.toString().trim()

                if (gps.isBlank()) {
                    binding.etGps.error = "GPS is required"
                } else {
                    binding.etGps.error = null
                }

                val isFormValid = gps.isNotEmpty()
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etGps.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val gps = binding.etGps.text.toString().trim()

            if (gps.isEmpty()) {
                binding.etGps.error = "GPS is required"
                return@setOnClickListener
            }


            val gpsData = "GPS: $gps"

            val action = GPSFragmentDirections.actionGPSFragmentToCreatedQRFragment(
                qrData = gpsData,
                qrType = "GPS"
            )
            findNavController().navigate(action)
        }
    }

}