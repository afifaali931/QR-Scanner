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
import com.example.qrcodescanner.databinding.FragmentSMSBinding
import com.example.qrcodescanner.databinding.FragmentWiFiBinding


class WiFiFragment : Fragment() {

    private lateinit var binding: FragmentWiFiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWiFiBinding.inflate(inflater, container, false)
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
                val networkName = binding.etNetworkName.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                if (networkName.isBlank()) {
                    binding.etNetworkName.error = "Network name is required"
                } else {
                    binding.etNetworkName.error = null
                }

                if (password.isBlank()) {
                    binding.etPassword.error = "Password is required"
                } else {
                    binding.etPassword.error = null
                }

                val isFormValid = networkName.isNotEmpty()
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etNetworkName.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val networkName = binding.etNetworkName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (networkName.isEmpty()) {
                binding.etNetworkName.error = "Please enter a valid network name"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPassword.error = "Password is required"
                return@setOnClickListener
            }

            val wifiData = "Network Name: $networkName\nPassword: $password"
//            val wifiData = "WIFI:T:WPA;S:$networkName;P:$password;;"

            val action = WiFiFragmentDirections.actionWiFiFragmentToCreatedQRFragment(
                qrData = wifiData,
                qrType = "Wi-Fi"
            )
            findNavController().navigate(action)
        }
    }
}
