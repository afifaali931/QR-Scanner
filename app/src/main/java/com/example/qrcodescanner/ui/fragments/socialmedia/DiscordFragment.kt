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
import com.example.qrcodescanner.databinding.FragmentDiscordBinding
import com.example.qrcodescanner.databinding.FragmentPaypalBinding


class DiscordFragment : Fragment() {

    private lateinit var binding: FragmentDiscordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscordBinding.inflate(inflater, container, false)
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

        binding.etDiscord.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val disUrl = binding.etDiscord.text.toString().trim()

            if (disUrl.isBlank() || !isValidDiscordUrl(disUrl)) {
                binding.etDiscord.error = "Enter a valid Discordl URL"
                return@setOnClickListener
            }

            val action = DiscordFragmentDirections.actionDiscordFragmentToCreatedQRFragment(
                qrData = disUrl,
                qrType = "Discord"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }
    private fun isValidDiscordUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(discord\\.com|disco\\.rd)/.+$")
        return regex.matches(url.trim())
    }

    private fun validateForm() {
        val disUrl = binding.etDiscord.text.toString()
        val isValid = disUrl.isNotBlank() && isValidDiscordUrl(disUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}