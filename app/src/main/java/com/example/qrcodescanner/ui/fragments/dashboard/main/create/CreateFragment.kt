package com.example.qrcodescanner.ui.fragments.dashboard.main.create

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.model.QRItem
import com.example.qrcodescanner.databinding.FragmentCreateBinding
import com.example.qrcodescanner.databinding.FragmentMainBinding
import com.example.qrcodescanner.ui.adapter.QRAdapter
import com.example.qrcodescanner.ui.fragments.dashboard.main.main.qrscanner.QRScaneFragment

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnTools: Button
    private lateinit var btnSocial: Button
    private lateinit var adapter: QRAdapter

    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }
    private val toolsList by lazy {
        listOf(
            QRItem(getString(R.string.email), R.drawable.ic_email),
            QRItem(getString(R.string.website), R.drawable.ic_itemcode),
            QRItem(getString(R.string.wifi), R.drawable.ic_wifi),
            QRItem(getString(R.string.contact), R.drawable.ic_contact),
            QRItem(getString(R.string.cell_phone), R.drawable.ic_cellphone),
            QRItem(getString(R.string.sms), R.drawable.ic_sms),
            QRItem(getString(R.string.my_card), R.drawable.ic_mycard),
            QRItem(getString(R.string.calendar), R.drawable.ic_calendar),
            QRItem(getString(R.string.gps), R.drawable.ic_whatsapp),
            QRItem(getString(R.string.clipboard), R.drawable.ic_clipboard),
            QRItem(getString(R.string.text), R.drawable.ic_text),
            QRItem(getString(R.string.item_code), R.drawable.ic_itemcode)
        )
    }

    private val socialList by lazy {
        listOf(
            QRItem(getString(R.string.facebook), R.drawable.ic_facebook),
            QRItem(getString(R.string.youtube), R.drawable.ic_youtube),
            QRItem(getString(R.string.whatsapp), R.drawable.ic_whatsapp),
            QRItem(getString(R.string.paypal), R.drawable.ic_paypal),
            QRItem(getString(R.string.twitter), R.drawable.ic_twitter),
            QRItem(getString(R.string.instagram), R.drawable.ic_instagram),
            QRItem(getString(R.string.spotify), R.drawable.ic_spotify),
            QRItem(getString(R.string.tiktok), R.drawable.ic_tiktok),
            QRItem(getString(R.string.viber), R.drawable.ic_viber),
            QRItem(getString(R.string.discord), R.drawable.ic_discord)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        myAdapter.onItemClick = { item -> onQRItemClick(item) }
        recyclerView = view.findViewById(R.id.qrRecyclerView)
        btnTools = view.findViewById(R.id.btnTools)
        btnSocial = view.findViewById(R.id.btnSocial)

        setupRecyclerView()

        switchList(toolsList)
        btnTools.setOnClickListener {
            highlightTab(isTools = true)
            switchList(toolsList)

        }
        btnSocial.setOnClickListener {
            highlightTab(isTools = false)
            switchList(socialList)
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = QRAdapter(emptyList()) { item -> onQRItemClick(item) }
        recyclerView.adapter = adapter
    }
    private fun switchList(list: List<QRItem>) {
        adapter = QRAdapter(list){ item -> onQRItemClick(item) }
        recyclerView.adapter = adapter
    }
    private fun highlightTab(isTools: Boolean) {
        btnTools.setBackgroundTintList(
            ContextCompat.getColorStateList(requireContext(), if (isTools) R.color.green_200 else R.color.button_color)
        )
        btnSocial.setBackgroundTintList(
            ContextCompat.getColorStateList(requireContext(), if (isTools) R.color.button_color else R.color.green_200)
        )
    }


    private fun onQRItemClick(item: QRItem) {
        Log.d("CreateFragment", "email currentDestination ${findNavController().currentDestination}")

        when (item.title) {

            "Email" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_emailFragment4)
            "Clipboard" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_clipboardFragment2)
            "GPS" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_GPSFragment)
            "Website" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_websiteFragment)
            "Wi-Fi" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_wiFiFragment)
            "Contact" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_contactFragment)
            "Cell Phone" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_cellPhoneFragment)
            "SMS" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_SMSFragment)
            "My Card" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_myCardFragment)
            "Calendar" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_calenderFragment)
            "Text" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_textFragment)
            "Item Code" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_itemCodeFragment)


            "Facebook" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_facebookFragment)
            "YouTube" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_youtubeFragment)
            "WhatsApp" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_whatsappFragment)
            "Paypal" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_paypalFragment)
            "Twitter" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_twitterFragment)
            "Instagram" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_instagramFragment)
            "Spotify" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_spotifyFragment)
            "TikTok" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_tiktokFragment)
            "Viber" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_viberFragment)
            "Discord" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_discordFragment)


            else -> Toast.makeText(requireContext(), "No fragment found for ${item.title}", Toast.LENGTH_SHORT).show()

        }
        Log.d("CreateFragment", "email navigation ${item}")

    }

}