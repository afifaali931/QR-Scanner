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

    private val toolsList = listOf(
        QRItem("Email", R.drawable.ic_email),
        QRItem("Website", R.drawable.ic_itemcode),
        QRItem("Wi-Fi", R.drawable.ic_wifi),
        QRItem("Contact", R.drawable.ic_contact),
        QRItem("Cell Phone", R.drawable.ic_cellphone),
        QRItem("SMS", R.drawable.ic_sms),
        QRItem("My Card", R.drawable.ic_mycard),
        QRItem("Calendar", R.drawable.ic_calendar),
        QRItem("GPS", R.drawable.ic_whatsapp),
        QRItem("Clipboard", R.drawable.ic_clipboard),
        QRItem("Text", R.drawable.ic_text),
        QRItem("Item Code", R.drawable.ic_itemcode)
    )
    private val socialList = listOf(
        QRItem("Facebook", R.drawable.ic_facebook),
        QRItem("Youtube", R.drawable.ic_youtube),
        QRItem("Whatsapp", R.drawable.ic_whatsapp),
        QRItem("Paypal", R.drawable.ic_paypal),
        QRItem("Twitter", R.drawable.ic_twitter),
        QRItem("Instagram", R.drawable.ic_instagram),
        QRItem("Spotify", R.drawable.ic_spotify),
        QRItem("Tiktok", R.drawable.ic_tiktok),
        QRItem("Viber", R.drawable.ic_viber),
        QRItem("Discord", R.drawable.ic_discord)
    )
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
            "Youtube" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_youtubeFragment)
            "Whatsapp" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_whatsappFragment)
            "Paypal" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_paypalFragment)
            "Twitter" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_twitterFragment)
            "Instagram" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_instagramFragment)
            "Spotify" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_spotifyFragment)
            "Tiktok" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_tiktokFragment)
            "Viber" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_viberFragment)
            "Discord" -> parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_discordFragment)


            else -> Toast.makeText(requireContext(), "No fragment found for ${item.title}", Toast.LENGTH_SHORT).show()

        }
        Log.d("CreateFragment", "email navigation ${item}")

    }

}