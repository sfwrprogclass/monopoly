package com.dallascollege.monopoly.ui

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarManager {
    private val _messages = MutableSharedFlow<String>()
    val messages = _messages.asSharedFlow()

    suspend fun showMessage(message: String, duration: SnackbarDuration) {
        _messages.emit(message)
    }
}