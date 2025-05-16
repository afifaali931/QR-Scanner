package com.example.qrcodescanner.ui.fragments.socialmedia

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentWhatsappBinding


class WhatsappFragment : Fragment() {

    private lateinit var binding: FragmentWhatsappBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhatsappBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateForm()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etWhatsapp.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val number = binding.etWhatsapp.text.toString().trim()

            if (!isValidWhatsappNumber(number)) {
                binding.etWhatsapp.error = "Enter a valid WhatsApp number"
                return@setOnClickListener
            }

            val formattedNumber = number.replace("[^\\d]".toRegex(), "")
            val qrData = "https://wa.me/$formattedNumber"

            val action = WhatsappFragmentDirections.actionWhatsappFragmentToCreatedQRFragment(
                qrData = qrData,
                qrType = "WhatsApp"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun validateForm() {
        val number = binding.etWhatsapp.text.toString().trim()
        val isValid = isValidWhatsappNumber(number)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun isValidWhatsappNumber(number: String): Boolean {
        val digitsOnly = number.replace("[^\\d]".toRegex(), "")
        return digitsOnly.length in 10..15 && digitsOnly.matches(Regex("^\\d{10,15}$"))
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
