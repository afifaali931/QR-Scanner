package com.example.qrcodescanner.ui.fragments.dashboard.main.style

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qrcodescanner.databinding.FragmentStyleBinding


class StyleFragment : Fragment() {

    private lateinit var binding: FragmentStyleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStyleBinding.inflate(inflater, container, false)
        return binding.root
    }


}