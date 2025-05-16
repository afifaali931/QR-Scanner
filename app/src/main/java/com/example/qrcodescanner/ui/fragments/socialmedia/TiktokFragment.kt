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
import com.example.qrcodescanner.databinding.FragmentTiktokBinding


class TiktokFragment : Fragment() {

    private lateinit var binding: FragmentTiktokBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTiktokBinding.inflate(inflater, container, false)
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

        binding.ettiktok.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val tikUrl = binding.ettiktok.text.toString().trim()

            if (tikUrl.isBlank() || !isValidTiktokUrl(tikUrl)) {
                binding.ettiktok.error = "Enter a valid Tiktok URL"
                return@setOnClickListener
            }

            val action = TiktokFragmentDirections.actionTiktokFragmentToCreatedQRFragment(
                qrData = tikUrl,
                qrType = "Tiktok"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun isValidTiktokUrl(url: String): Boolean {
        val regex = Regex("^(https?://)?(www\\.)?(tiktok\\.com|tikt\\.ok)/.+$")
        return regex.matches(url.trim())
    }

    private fun validateForm() {
        val tikUrl = binding.ettiktok.text.toString()
        val isValid = tikUrl.isNotBlank() && isValidTiktokUrl(tikUrl)
        binding.btnCreate.isEnabled = isValid
        binding.btnCreate.alpha = if (isValid) 1f else 0.5f
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
