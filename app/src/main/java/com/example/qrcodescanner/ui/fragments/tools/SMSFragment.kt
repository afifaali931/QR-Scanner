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
import com.example.qrcodescanner.databinding.FragmentCellPhoneBinding
import com.example.qrcodescanner.databinding.FragmentSMSBinding


class SMSFragment : Fragment() {
    private lateinit var binding: FragmentSMSBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSMSBinding.inflate(inflater, container, false)
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
                    val phoneNumber = binding.etSmsNumber.text.toString().trim()
                    val message = binding.etSmsMessage.text.toString().trim()

                    if (phoneNumber.isBlank()) {
                        binding.etSmsNumber.error = "Phone Number is required"
                    } else {
                        binding.etSmsNumber.error = null
                    }

                    if (message.isBlank()) {
                        binding.etSmsMessage.error = "Message is required"
                    } else {
                        binding.etSmsMessage.error = null
                    }

                    val isFormValid = phoneNumber.isNotEmpty()
                    binding.btnCreate.isEnabled = isFormValid
                    binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }

            binding.etSmsNumber.addTextChangedListener(textWatcher)
            binding.etSmsMessage.addTextChangedListener(textWatcher)
        }

        private fun setupToolbar() {
            binding.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        private fun setupCreateButton() {
            binding.btnCreate.setOnClickListener {
                val phoneNumber = binding.etSmsNumber.text.toString().trim()
                val message = binding.etSmsMessage.text.toString().trim()

                if (phoneNumber.isEmpty()) {
                    binding.etSmsNumber.error = "Please enter a valid network name"
                    return@setOnClickListener
                }
                if (message.isEmpty()) {
                    binding.etSmsMessage.error = "Message is required"
                    return@setOnClickListener
                }

                val smsData = "Phone Number: $phoneNumber\nMessage: $message"

                val action = SMSFragmentDirections.actionSMSFragmentToCreatedQRFragment(
                    qrData = smsData,
                    qrType = "SMS"
                )
                findNavController().navigate(action)
            }
        }

}