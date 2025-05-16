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
import com.example.qrcodescanner.databinding.FragmentSpotifyBinding


class SpotifyFragment : Fragment() {

    private lateinit var binding: FragmentSpotifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpotifyBinding.inflate(inflater, container, false)
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

        binding.etSpotify.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val spoUrl = binding.etSpotify.text.toString().trim()

            if (spoUrl.isBlank() || !isValidSpotifyUrl(spoUrl)) {
                binding.etSpotify.error = "Enter a valid Spotify URL"
                return@setOnClickListener
            }

            val action = SpotifyFragmentDirections.actionSpotifyFragmentToCreatedQRFragment(
                qrData = spoUrl,
                qrType = "Spotify"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun isValidSpotifyUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(spotify\\.com|spoti\\.fy)/.+$")
        return regex.matches(url.trim())
    }

    private fun validateForm() {
        val spoUrl = binding.etSpotify.text.toString()
        val isValid = spoUrl.isNotBlank() && isValidSpotifyUrl(spoUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
