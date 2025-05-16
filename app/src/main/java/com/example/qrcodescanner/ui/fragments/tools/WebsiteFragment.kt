package com.example.qrcodescanner.ui.fragments.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.databinding.FragmentWebsiteBinding


class WebsiteFragment : Fragment() {

    private lateinit var binding: FragmentWebsiteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebsiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupQuickButtons()
        setupCreateButton()

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val website = binding.etWebsite.text.toString()

                if (website.isBlank()) {
                    binding.etWebsite.error = "Website URL can't be empty"
                } else if (!isValidWebsite(website)) {
                    binding.etWebsite.error = "Enter a valid URL"
                } else {
                    binding.etWebsite.error = null
                }

                val isFormValid = isValidWebsite(website)
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etWebsite.addTextChangedListener(textWatcher)

        binding.btnCreate.isEnabled = false
        binding.btnCreate.alpha = 0.5f
    }

    private fun isValidWebsite(url: String): Boolean {
        val trimmed = url.trim()
        return Patterns.WEB_URL.matcher(trimmed).matches()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupQuickButtons() {
        binding.btnHttp.setOnClickListener {
            insertTextIfNotPresent("https://")
        }
        binding.btnWww.setOnClickListener {
            insertTextIfNotPresent("www.")
        }
        binding.btnCom.setOnClickListener {
            val text = binding.etWebsite.text.toString()
            if (!text.endsWith(".com")) {
                binding.etWebsite.setText(text + ".com")
                binding.etWebsite.setSelection(binding.etWebsite.text?.length ?: 0)
            }
        }
    }

    private fun insertTextIfNotPresent(textToInsert: String) {
        val currentText = binding.etWebsite.text?.toString() ?: ""
        if (!currentText.contains(textToInsert)) {
            binding.etWebsite.setText(textToInsert + currentText)
            binding.etWebsite.setSelection(binding.etWebsite.text?.length ?: 0)
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val websiteUrl = binding.etWebsite.text.toString().trim()
            if (!isValidWebsite(websiteUrl)) {
                binding.etWebsite.error = "Please enter a valid website URL"
                return@setOnClickListener
            }

            val action = WebsiteFragmentDirections
                .actionWebsiteFragmentToCreatedQRFragment(
                    qrType = "Website",
                    qrData = websiteUrl
                )
            findNavController().navigate(action)
        }
    }

}
