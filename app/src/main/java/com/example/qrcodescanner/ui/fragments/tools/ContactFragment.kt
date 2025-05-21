package com.example.qrcodescanner.ui.fragments.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentCellPhoneBinding
import com.example.qrcodescanner.databinding.FragmentContactBinding
import com.example.qrcodescanner.databinding.FragmentCreateBinding


class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupCreateButton()

        binding.btnCreate.isEnabled = false
        binding.btnCreate.alpha = 0.5f

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val contactName = binding.etContactName.text.toString().trim()
                val contactNumber = binding.etContactNumber.text.toString().trim()

                if (contactName.isBlank()) {
                    binding.etContactName.error = "Name is required"
                } else {
                    binding.etContactName.error = null
                }

                if (contactNumber.isBlank()) {
                    binding.etContactNumber.error = "Number is required"
                } else {
                    binding.etContactNumber.error = null
                }

                val isFormValid = contactName.isNotEmpty()
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etContactName.addTextChangedListener(textWatcher)
        binding.etContactNumber.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val contactName = binding.etContactName.text.toString().trim()
            val contactNumber = binding.etContactNumber.text.toString().trim()

            if (contactName.isEmpty()) {
                binding.etContactName.error = "Name is required"
                return@setOnClickListener
            }
            if (contactNumber.isEmpty()) {
                binding.etContactNumber.error = "Number is required is required"
                return@setOnClickListener
            }

            val contactData = "Contact Name: $contactName\nContact Number: $contactNumber"

            val action = ContactFragmentDirections.actionContactFragmentToCreatedQRFragment(
                qrData = contactData,
                qrType = "Contact"
            )
            findNavController().navigate(action)
        }
    }

}