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
import com.example.qrcodescanner.databinding.FragmentFacebookBinding


class FacebookFragment : Fragment() {

    private lateinit var binding: FragmentFacebookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFacebookBinding.inflate(inflater, container, false)
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

        binding.etFacebook.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val fbUrl = binding.etFacebook.text.toString().trim()

            if (fbUrl.isBlank() || !isValidFacebookUrl(fbUrl)) {
                binding.etFacebook.error = "Enter a valid Facebook URL"
                return@setOnClickListener
            }

            val action = FacebookFragmentDirections.actionFacebookFragmentToCreatedQRFragment(
                qrData = fbUrl,
                qrType = "Facebook"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun isValidFacebookUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(facebook\\.com|face\\.book)/.+$")
        return regex.matches(url.trim())
    }
    private fun validateForm() {
        val fbUrl = binding.etFacebook.text.toString()
        val isValid = fbUrl.isNotBlank() && isValidFacebookUrl(fbUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
