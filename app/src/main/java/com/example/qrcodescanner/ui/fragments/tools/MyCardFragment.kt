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
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentMyCardBinding


class MyCardFragment : Fragment() {

    private lateinit var binding: FragmentMyCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupTextWatchers()

        binding.btnCreate.setOnClickListener {
            val name = binding.etMyName.text.toString()
            val phone = binding.etMyPhone.text.toString()
            val email = binding.etMyEmail.text.toString()
            val address = binding.etMyAddress.text.toString()
            val birthday = binding.etMyBirthday.text.toString()
            val org = binding.etMyOrg.text.toString()
            val note = binding.etMyNote.text.toString()


            val cardInfo = buildString {
                appendLine("Name: $name")
                appendLine("Phone: $phone")
                appendLine("Email: $email")
                appendLine("Address: $address")
                appendLine("Birthday: $birthday")
                appendLine("Organization: $org")
                appendLine("Note: $note")
            }

            val action = MyCardFragmentDirections.actionMyCardFragmentToCreatedQRFragment(
                qrData = cardInfo,
                qrType = "My Card"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = validateForm()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        with(binding) {
            etMyName.addTextChangedListener(textWatcher)
            etMyPhone.addTextChangedListener(textWatcher)
            etMyEmail.addTextChangedListener(textWatcher)
            etMyAddress.addTextChangedListener(textWatcher)
            etMyBirthday.addTextChangedListener(textWatcher)
            etMyOrg.addTextChangedListener(textWatcher)
            etMyNote.addTextChangedListener(textWatcher)
        }
    }

    private fun validateForm() {
        val isFormValid = binding.etMyName.text.toString().isNotBlank()
                && binding.etMyPhone.text.toString().isNotBlank()
                && isValidEmail(binding.etMyEmail.text.toString())

        binding.btnCreate.isEnabled = isFormValid
        binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f

        if (!isValidEmail(binding.etMyEmail.text.toString()))
            binding.etMyEmail.error = "Enter a valid email"
        else
            binding.etMyEmail.error = null
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
