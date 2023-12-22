package com.example.mvi.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvi.databinding.ActivityMainBinding
import com.example.mvi.sealdclasses.MainIntent
import com.example.mvi.sealdclasses.MainState
import com.example.mvi.viewmodels.NumberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val numberViewModel by viewModels<NumberViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.increaseBtn.setOnClickListener {
            lifecycleScope.launch {
                numberViewModel.mainIntentChannel.send(MainIntent.Increas)
            }
        }
        binding.decreaseBtn.setOnClickListener {
            lifecycleScope.launch {
                numberViewModel.mainIntentChannel.send(MainIntent.Decreas)
            }
        }

        renderText()
    }

    private fun renderText() {
        lifecycleScope.launch {
            numberViewModel.viewStateFlow.collect {
                when (it) {
                    is MainState.Ideal -> setText("idel")
                    is MainState.Result -> setText(it.number.toString())
                    is MainState.Error -> setText(it.errorMessage)
                }
            }
        }
    }

    private fun setText(text: String) {
        binding.numTv.text = text
    }
}