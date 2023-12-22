package com.example.mvi.sealdclasses

sealed class MainState {
    object Ideal : MainState()
    data class Result(val number:Int) : MainState()
    data class Error(val errorMessage:String) : MainState()
}