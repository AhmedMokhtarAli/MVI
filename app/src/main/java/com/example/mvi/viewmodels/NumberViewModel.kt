package com.example.mvi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.sealdclasses.MainIntent
import com.example.mvi.sealdclasses.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumberViewModel @Inject constructor() : ViewModel() {
    private var number = 0

    val mainIntentChannel = Channel<MainIntent>()

    private val _viewStateFlow = MutableStateFlow<MainState>(MainState.Ideal)
    val viewStateFlow get() = _viewStateFlow.asStateFlow()

    init {
        processMainIntent()
    }
    private fun processMainIntent() {
        viewModelScope.launch {
            mainIntentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.Increas -> increaseNum()
                    is MainIntent.Decreas -> decreaseNum()
                }
            }
        }
    }

    private fun increaseNum() {
        viewModelScope.launch {
            try {
                _viewStateFlow.value = MainState.Result(++number)
            } catch (e: Exception) {
                _viewStateFlow.value = MainState.Error(errorMessage = e.message.toString())
            }
        }
    }

    private fun decreaseNum() {
        viewModelScope.launch {
          try {
              _viewStateFlow.value = MainState.Result(--number)
          } catch (e:Exception){
              _viewStateFlow.value = MainState.Error(e.message.toString())
          }
        }
    }
}