package com.example.qrcodescanner.ui.fragments.dashboard.main.settings


import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentSettingsBinding
import com.example.qrcodescanner.databinding.ItemSettingSwitchBinding
import com.example.qrcodescanner.utils.PreferenceHelper
import java.util.Locale


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var languageTextView: TextView
    private lateinit var languageLayout: LinearLayout
    private lateinit var rateUsLayout: LinearLayout
    private lateinit var privacyLayout: LinearLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSettingsBinding.bind(view)


        setupToggle(
            binding.itemSetting.vibrationSetting,
            getString(R.string.setting_vibration_title),
            getString(R.string.setting_vibration_desc),
            "pref_vibration",
            false,
            R.drawable.ic_vibration
        )

        setupToggle(
            binding.itemSetting.soundSetting,
            getString(R.string.setting_sound_title),
            getString(R.string.setting_sound_desc),
            "pref_sound",
            false,
            R.drawable.ic_sound
        )

        setupToggle(
            binding.itemSetting.autoCopySetting,
            getString(R.string.setting_auto_copy_title),
            getString(R.string.setting_auto_copy_desc),
            "pref_auto_copy",
            false,
            R.drawable.ic_autocopy
        )

        setupToggle(
            binding.itemSetting.autoWebSetting,
            getString(R.string.setting_auto_web_title),
            getString(R.string.setting_auto_web_desc),
            "pref_auto_web",
            false,
            R.drawable.ic_web_search
        )

        setupToggle(
            binding.itemSetting.saveHistorySetting,
            getString(R.string.setting_save_history_title),
            getString(R.string.setting_save_history_desc),
            "pref_save_history",
            false,
            R.drawable.ic_auto_save_history
        )

        rateUsLayout = view.findViewById(R.id.rateUsTextLayout)
        privacyLayout = view.findViewById(R.id.privacyTextLayout)
        languageTextView = view.findViewById(R.id.languageTextView)
        languageLayout = view.findViewById(R.id.layoutLanguage)


     setupListeners()
    }



    private fun setupListeners(){
        rateUsLayout.setOnClickListener {
            openPlayStore()
        }

        privacyLayout.setOnClickListener {
            openPrivacyPolicy()
        }


        languageLayout.setOnClickListener {
                showLanguageDialog()
            }
    }

    private fun setupToggle(
        settingToggleBinding: ItemSettingSwitchBinding,
        title: String,
        subtitle: String,
        prefKey: String,
        defaultState: Boolean,
        mIcon : Int
    ) {

        val savedState = PreferenceHelper.getBoolean(prefKey, defaultState)


        settingToggleBinding.settingTitle.text = title
        settingToggleBinding.settingSubtitle.text = subtitle
        settingToggleBinding.settingSwitch.isChecked = savedState
        settingToggleBinding.imgIcon.setImageResource(mIcon)


        settingToggleBinding.settingSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceHelper.putBoolean(prefKey, isChecked)
            Toast.makeText(requireContext(), "$title: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openPlayStore() {
        val packageName = requireContext().packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            startActivity(intent)
        }
    }

    private fun openPrivacyPolicy() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://qrcodescanner.com/privacy"))
        startActivity(intent)
    }


    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Hindi", "Spanish", "Greek")
        val languageCodes = arrayOf("en", "hi", "es", "el")

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_language))
            .setItems(languages) { _, which ->
                languageTextView.text = languages[which]
                setAppLocale(languageCodes[which])
            }
            .show()
    }
    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = requireContext().resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, resources.displayMetrics)


        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("app_language", languageCode).apply()

        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }


}







//class SettingsFragment : Fragment(R.layout.fragment_settings) {
//
//    private lateinit var binding: FragmentSettingsBinding
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding = FragmentSettingsBinding.bind(view)
//
//        setupToggle(binding.itemSetting.vibrationSetting, "Vibration", "Turn On / Off Mobile Vibration", false)
//        setupToggle(binding.itemSetting.soundSetting, "Sound", "Turn On / Off Mobile Sound", false)
//        setupToggle(binding.itemSetting.autoCopySetting,  "Auto Copy to clipboard", "Turn On / Off Auto Copy", false)
//        setupToggle(binding.itemSetting.autoWebSetting, "Auto Web Search", "Turn On / Off Auto Search", false)
//        setupToggle(binding.itemSetting.saveHistorySetting, "Save History", "Turn On / Off Save History", false)
//    }
//
//    private fun setupToggle(settingToggleBinding: ItemSettingSwitchBinding, title: String, subtitle: String, defaultState: Boolean) {
//
//
//        settingToggleBinding.settingTitle.text = title
//        settingToggleBinding.settingSubtitle.text = subtitle
//        settingToggleBinding.settingSwitch.isChecked = defaultState
//
//        settingToggleBinding.settingSwitch.setOnCheckedChangeListener { _, isChecked ->
//
//            Toast.makeText(requireContext(), "$title: $isChecked", Toast.LENGTH_SHORT).show()
//        }
//    }
//}


