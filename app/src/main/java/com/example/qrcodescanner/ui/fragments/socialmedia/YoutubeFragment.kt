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
import com.example.qrcodescanner.databinding.FragmentYoutubeBinding


class YoutubeFragment : Fragment() {

    private lateinit var binding: FragmentYoutubeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYoutubeBinding.inflate(inflater, container, false)
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

        binding.etYoutube.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {

            val youUrl = binding.etYoutube.text.toString().trim()

            if (youUrl.isBlank() || !isValidYoutubeUrl(youUrl)) {
                binding.etYoutube.error = "Enter a valid YouTube URL"
                return@setOnClickListener
            }


            val action = YoutubeFragmentDirections.actionYoutubeFragmentToCreatedQRFragment(
                qrData = youUrl,
                qrType = "Youtube"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun isValidYoutubeUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(youtube\\.com|youtu\\.be)/.+$")
        return regex.matches(url.trim())
    }

    private fun validateForm() {
        val youUrl = binding.etYoutube.text.toString()
        val isValid = youUrl.isNotBlank() && isValidYoutubeUrl(youUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
