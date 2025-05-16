package com.example.qrcodescanner.ui.fragments.tools


import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {

    private lateinit var binding: FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = binding.etEmail.text.toString()
                val subject = binding.etSubject.text.toString()
                val message = binding.etMessage.text.toString()

                if (!isValidEmail(email)) {
                    binding.etEmail.error = "Enter a valid email"
                } else {
                    binding.etEmail.error = null
                }

                if (subject.isBlank()) {
                    binding.etSubject.error = "Subject can't be empty"
                } else {
                    binding.etSubject.error = null
                }

                if (message.isBlank()) {
                    binding.etMessage.error = "Message can't be empty"
                } else {
                    binding.etMessage.error = null
                }

                validateForm()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }


        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etSubject.addTextChangedListener(textWatcher)
        binding.etMessage.addTextChangedListener(textWatcher)

        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val subject = binding.etSubject.text.toString()
            val body = binding.etMessage.text.toString()

            var hasError = false

            if (!isValidEmail(email)) {
                binding.etEmail.error = "Enter a valid email"
                hasError = true
            }

            if (subject.isBlank()) {
                binding.etSubject.error = "Subject can't be empty"
                hasError = true
            }

            if (body.isBlank()) {
                binding.etMessage.error = "Message can't be empty"
                hasError = true
            }

            if (hasError) return@setOnClickListener

            val emailUri = buildString {
                append("mailto:$email")
                if (subject.isNotBlank() || body.isNotBlank()) {
                    append("?")
                    if (subject.isNotBlank()) append("subject=${Uri.encode(subject)}")
                    if (subject.isNotBlank() && body.isNotBlank()) append("&")
                    if (body.isNotBlank()) append("body=${Uri.encode(body)}")
                }
            }

            val action = EmailFragmentDirections.actionEmailFragmentToCreatedQRFragment(
                qrData = emailUri,
                qrType = "Email"
            )
            findNavController().navigate(action)
        }

        validateForm()
    }


    private fun validateForm() {
        val email = binding.etEmail.text.toString()
        val subject = binding.etSubject.text.toString()
        val message = binding.etMessage.text.toString()

        val isFormValid = isValidEmail(email) && subject.isNotBlank() && message.isNotBlank()

        binding.btnCreate.isEnabled = isFormValid
        binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
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

//
//class EmailFragment : Fragment() {
//
//    private lateinit var binding: FragmentEmailBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentEmailBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupToolbar()
//        binding.btnCreate.isEnabled = false
//        binding.btnCreate.alpha     = 0.5f
//
//        val textWatcher = object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                val isFormValid = binding.etEmail.text?.isNotBlank() == true ||
//                        binding.etSubject.text?.isNotBlank() == true ||
//                        binding.etMessage.text?.isNotBlank() == true
//
//                binding.btnCreate.isEnabled = isFormValid
//                binding.btnCreate.alpha     = if (isFormValid) 1f else 0.5f
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                val isFormValid = binding.etEmail.text?.isNotBlank() == true ||
////                    binding.etSubject.text?.isNotBlank() == true ||
////                    binding.etMessage.text?.isNotBlank() == true
////
////                binding.btnCreate.isEnabled = isFormValid
////                binding.btnCreate.alpha = if (isFormValid) 1.0f else 0.5f
//
//            }
//        }
//
//        binding.etEmail.addTextChangedListener(textWatcher)
//        binding.etSubject.addTextChangedListener(textWatcher)
//        binding.etMessage.addTextChangedListener(textWatcher)
//
//
//        binding.btnCreate.setOnClickListener {
//            val email = binding.etEmail.text.toString()
//            val subject = binding.etSubject.text.toString()
//            val body = binding.etMessage.text.toString()
//
//            val emailUri = buildString {
//                append("mailto:$email")
//                if (subject.isNotBlank() || body.isNotBlank()) {
//                    append("?")
//                    if (subject.isNotBlank()) append("subject=${Uri.encode(subject)}")
//                    if (subject.isNotBlank() && body.isNotBlank()) append("&")
//                    if (body.isNotBlank()) append("body=${Uri.encode(body)}")
//                }
//            }
//
//            val action = EmailFragmentDirections.actionEmailFragmentToCreatedQRFragment(
//                qrData = emailUri,
//                qrType = "Email"
//            )
//            findNavController().navigate(action)
//        }
//    }
//    private fun setupToolbar() {
//        binding.toolbar.setNavigationOnClickListener {
//            findNavController().navigateUp()
//        }
//    }
//}









//class EmailFragment : Fragment() {
//
//    private lateinit var binding: FragmentEmailBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentEmailBinding.inflate(inflater, container, false)
//
//        binding.btnCreate.setOnClickListener {
//            Log.d("EmailFragment", "email currentDestination.............. ${findNavController().currentDestination}")
//
//            val email = binding.etEmail.text.toString().trim()
//            val subject = binding.etSubject.text.toString().trim()
//            val message = binding.etMessage.text.toString().trim()
//
//            if (email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            val qrData = "mailto:$email?subject=$subject&body=$message"
//
//
//            val action = EmailFragmentDirections.actionEmailFragmentToCreatedQRFragment(qrData)
//            findNavController().navigate(action)
//
//        }
//
//        return binding.root
//    }
//}

