package com.kaanf.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnNextClick -> goNext()
            RegisterAction.OnBackClick -> goBack()
            else -> {}
        }
    }

    private fun goNext() {
        val s = _state.value
        if (!s.canGoNext) return

        val next = when (s.step) {
            RegisterStep.Username -> RegisterStep.Email
            RegisterStep.Email -> RegisterStep.Password
            RegisterStep.Password -> RegisterStep.Password
        }
        _state.update { it.copy(step = next) }
    }

    private fun goBack() {
        val s = _state.value
        val prev = when (s.step) {
            RegisterStep.Username -> RegisterStep.Username
            RegisterStep.Email -> RegisterStep.Username
            RegisterStep.Password -> RegisterStep.Email
        }
        _state.update { it.copy(step = prev) }
    }
}