package com.example.qrcodescanner.ui.fragments.dashboard.main.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentSettingsBinding
import com.example.qrcodescanner.databinding.ItemSettingSwitchBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSettingsBinding.bind(view)

        setupToggle(binding.itemSetting.vibrationSetting, "Vibration", "Turn On / Off Mobile Vibration", false)
        setupToggle(binding.itemSetting.soundSetting, "Sound", "Turn On / Off Mobile Sound", false)
        setupToggle(binding.itemSetting.autoCopySetting,  "Auto Copy to clipboard", "Turn On / Off Auto Copy", false)
        setupToggle(binding.itemSetting.autoWebSetting, "Auto Web Search", "Turn On / Off Auto Search", false)
        setupToggle(binding.itemSetting.saveHistorySetting, "Save History", "Turn On / Off Save History", false)
    }

    private fun setupToggle(settingToggleBinding: ItemSettingSwitchBinding, title: String, subtitle: String, defaultState: Boolean) {


        settingToggleBinding.settingTitle.text = title
        settingToggleBinding.settingSubtitle.text = subtitle
        settingToggleBinding.settingSwitch.isChecked = defaultState

        settingToggleBinding.settingSwitch.setOnCheckedChangeListener { _, isChecked ->

            Toast.makeText(requireContext(), "$title: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }
}


