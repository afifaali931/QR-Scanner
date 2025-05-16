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
import com.example.qrcodescanner.databinding.FragmentItemCodeBinding

class ItemCodeFragment : Fragment() {
    private lateinit var binding: FragmentItemCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemCodeBinding.inflate(inflater, container, false)
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
                val itemCode = binding.etItemCode.text.toString().trim()

                if (itemCode.isBlank()) {
                    binding.etItemCode.error = "Code is required"
                } else {
                    binding.etItemCode.error = null
                }

                val isFormValid = itemCode.isNotEmpty()
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etItemCode.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val itemCode = binding.etItemCode.text.toString().trim()

            if (itemCode.isEmpty()) {
                binding.etItemCode.error = "Code is required"
                return@setOnClickListener
            }


            val itemCodeData = "Item Code: $itemCode"

            val action = ItemCodeFragmentDirections.actionItemCodeFragmentToCreatedQRFragment(
                qrData = itemCodeData,
                qrType = "Product Code"
            )
            findNavController().navigate(action)
        }
    }

}