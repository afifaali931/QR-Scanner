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
import com.example.qrcodescanner.databinding.FragmentInstagramBinding


class InstagramFragment : Fragment() {

    private lateinit var binding: FragmentInstagramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstagramBinding.inflate(inflater, container, false)
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

        binding.etInstagram.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val instaUrl = binding.etInstagram.text.toString().trim()

            if (instaUrl.isBlank() || !isValidInstagramUrl(instaUrl)) {
                binding.etInstagram.error = "Enter a valid Instagram URL"
                return@setOnClickListener
            }

            val action = InstagramFragmentDirections.actionInstagramFragmentToCreatedQRFragment(
                qrData = instaUrl,
                qrType = "Instagram"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }
    private fun isValidInstagramUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(instagram\\.com)/.+$")
        return regex.matches(url.trim())
    }


    private fun validateForm() {
        val instaUrl = binding.etInstagram.text.toString()
        val isValid = instaUrl.isNotBlank() && isValidInstagramUrl(instaUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
