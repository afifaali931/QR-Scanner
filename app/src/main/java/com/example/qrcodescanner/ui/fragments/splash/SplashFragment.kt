package com.example.qrcodescanner.ui.fragments.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val totalDuration = 5000L
    private var remainingTime = totalDuration
    private var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remainingTime = savedInstanceState?.getLong("remaining_time") ?: totalDuration
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.max = totalDuration.toInt()
    }

    override fun onResume() {
        super.onResume()
        startProgressBar()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("remaining_time", remainingTime)
    }

    private fun startProgressBar() {

        binding.progressBar.progress = (totalDuration - remainingTime).toInt()

        timer = object : CountDownTimer(remainingTime, 100) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished

                binding.progressBar.progress = (totalDuration - remainingTime).toInt()

            }

            override fun onFinish() {
                binding.progressBar.progress = totalDuration.toInt()

                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        } .start()
    }
}
