package com.example.qrcodescanner.ui.fragments.tools


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.databinding.FragmentClipboardBinding

class ClipboardFragment : Fragment() {
    private lateinit var binding: FragmentClipboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClipboardBinding.inflate(inflater, container, false)
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
                val message = binding.etMessage.text.toString().trim()

                if (message.isBlank()) {
                    binding.etMessage.error = "Content required"
                } else {
                    binding.etMessage.error = null
                }

                val isFormValid = message.isNotEmpty()
                binding.btnCreate.isEnabled = isFormValid
                binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etMessage.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupCreateButton() {
        binding.btnCreate.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()

            if (message.isEmpty()) {
                binding.etMessage.error = "Content required"
                return@setOnClickListener
            }


            val contData = " $message"

            val action = ClipboardFragmentDirections.actionClipboardFragmentToCreatedQRFragment(
                qrData = contData,
                qrType = "Clipboard"
            )
            findNavController().navigate(action)
        }
    }

}