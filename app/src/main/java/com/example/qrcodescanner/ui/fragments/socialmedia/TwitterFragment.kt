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
import com.example.qrcodescanner.databinding.FragmentTwitterBinding


class TwitterFragment : Fragment() {

    private lateinit var binding: FragmentTwitterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwitterBinding.inflate(inflater, container, false)
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

        binding.etTwitter.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val twitUrl = binding.etTwitter.text.toString().trim()

            if (twitUrl.isBlank() || !isValidTwitterkUrl(twitUrl)) {
                binding.etTwitter.error = "Enter a valid Twitter URL"
                return@setOnClickListener
            }

            val action = TwitterFragmentDirections.actionTwitterFragmentToCreatedQRFragment(
                qrData = twitUrl,
                qrType = "Twitter"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }
    private fun isValidTwitterkUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(twitter\\.com|twitt\\.er)/.+$")
        return regex.matches(url.trim())
    }


    private fun validateForm() {
        val twitUrl = binding.etTwitter.text.toString()
        val isValid = twitUrl.isNotBlank() && isValidTwitterkUrl(twitUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
