package com.example.qrcodescanner.ui.fragments.tools


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.databinding.FragmentCalenderBinding
import java.util.Calendar

class CalenderFragment : Fragment() {

        private lateinit var binding: FragmentCalenderBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentCalenderBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupToolbar()
            setupTextWatchers()

            binding.etStartDateTime.inputType = InputType.TYPE_NULL
            binding.etEndDateTime.inputType = InputType.TYPE_NULL

            binding.etStartDateTime.setOnClickListener {
                showDatePickerDialog { date ->
                    binding.etStartDateTime.setText(date)
                }
            }

            binding.etEndDateTime.setOnClickListener {
                showDatePickerDialog { date ->
                    binding.etEndDateTime.setText(date)
                }
            }

            binding.btnCreate.setOnClickListener {
                val title = binding.etTitle.text.toString()
                val startDate = binding.etStartDateTime.text.toString()
                val endDate = binding.etEndDateTime.text.toString()
                val message = binding.etMessage.text.toString()

                val calendarData = buildString {
                    appendLine("Title: $title")
                    appendLine("Start: $startDate")
                    appendLine("End: $endDate")
                    appendLine("Message: $message")
                }

                val action = CalenderFragmentDirections.actionCalenderFragmentToCreatedQRFragment(
                    qrData = calendarData,
                    qrType = "Calendar"
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
                etTitle.addTextChangedListener(textWatcher)
                etStartDateTime.addTextChangedListener(textWatcher)
                etEndDateTime.addTextChangedListener(textWatcher)
                etMessage.addTextChangedListener(textWatcher)
            }
        }
        private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)

            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                val selectedDate = String.format("%02d/%02d/%04d", d, m + 1, y)
                onDateSelected(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        private fun validateForm() {
            val isFormValid = binding.etTitle.text.toString().isNotBlank()
                    && binding.etStartDateTime.text.toString().isNotBlank()
                    && binding.etEndDateTime.text.toString().isNotBlank()

            binding.btnCreate.isEnabled = isFormValid
            binding.btnCreate.alpha = if (isFormValid) 1f else 0.5f
        }

        private fun setupToolbar() {
            binding.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


