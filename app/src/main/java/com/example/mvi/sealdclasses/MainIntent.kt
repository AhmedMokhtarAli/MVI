package com.example.mvi.sealdclasses

sealed class MainIntent {
    object Increas : MainIntent()
    object Decreas:MainIntent()
}