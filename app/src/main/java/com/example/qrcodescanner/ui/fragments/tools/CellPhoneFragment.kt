package com.example.qrcodescanner.ui.fragments.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.databinding.FragmentCellPhoneBinding
import com.example.qrcodescanner.ui.fragments.tools.CellPhoneFragmentDirections.Companion.actionCellPhoneFragmentToCreatedQRFragment

class CellPhoneFragment : Fragment() {

    private lateinit var binding: FragmentCellPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCellPhoneBinding.inflate(inflater, container, false)
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
                val number = binding.etContactNumber.text.toString().trim()

                if (!isValidPhoneNumber(number)) {
                    binding.etContactNumber.error = "Enter a valid phone number"
                } else {
                    binding.etContactNumber.error = null
                }

                val isFormValid = isValidPhoneNumber(number)
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etContactNumber.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val number = binding.etContactNumber.text.toString().trim()

            if (!isValidPhoneNumber(number)) {
                binding.etContactNumber.error = "Enter a valid phone number"
                return@setOnClickListener
            }

            val formattedNumber = number.replace("[^\\d]".toRegex(), "")
            val qrData = "Cell Phone: $formattedNumber"

            val action = CellPhoneFragmentDirections.actionCellPhoneFragmentToCreatedQRFragment(
                qrData = qrData,
                qrType = "Phone"
            )
            findNavController().navigate(action)
        }
    }

    private fun isValidPhoneNumber(number: String): Boolean {
        val digitsOnly = number.replace("[^\\d]".toRegex(), "")
        return digitsOnly.length in 10..15 && digitsOnly.matches(Regex("^\\d{10,15}$"))
    }
}

