package com.kaanf.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.error_account_exists
import oris.feature.auth.presentation.generated.resources.error_invalid_email
import oris.feature.auth.presentation.generated.resources.error_invalid_password
import oris.feature.auth.presentation.generated.resources.error_invalid_username

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            /* Don't analyze this field. */
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
            RegisterAction.OnRegisterClick -> submitRegistration()
            RegisterAction.OnBackClick -> goBack()

            RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            else -> {
                /* Don't analyze this field. */
            }
        }
    }

    private fun submitRegistration() {
        if (_state.value.step != RegisterStep.Password) return
        if (_state.value.isRegistering) return
        if (!_state.value.isPasswordValid) return

        register()
    }

    private fun register() = viewModelScope.launch {
        _state.update {
            it.copy(
                isRegistering = true,
                registrationError = null
            )
        }

        val email = state.value.emailTextState.text.toString()
        val username = state.value.usernameTextState.text.toString()
        val password = state.value.passwordTextState.text.toString()

        authService
            .register(
                email = email,
                username = username,
                password = password
            )
            .onSuccess {
                _state.update {
                    it.copy(
                        isRegistering = false,
                        step = RegisterStep.Verification
                    )
                }
            }
            .onFailure { error ->
                val registrationError = when (error) {
                    DataError.Remote.CONFLICT -> UIText.Resource(Res.string.error_account_exists)
                    else -> error.toUiText()
                }

                _state.update {
                    it.copy(
                        isRegistering = false,
                        registrationError = registrationError
                    )
                }
            }
    }

    private fun goNext() {
        val s = _state.value
        val isValid = when (s.step) {
            RegisterStep.Username -> s.isUsernameValid
            RegisterStep.Email -> s.isEmailValid
            RegisterStep.Password -> s.isPasswordValid
            RegisterStep.Verification -> false
        }

        val next = when (s.step) {
            RegisterStep.Username -> RegisterStep.Email
            RegisterStep.Email -> RegisterStep.Password
            RegisterStep.Password -> RegisterStep.Verification
            RegisterStep.Verification -> RegisterStep.Verification
        }

        _state.update {
            it.copy(
                usernameError = if (s.step == RegisterStep.Username && !isValid)
                    UIText.Resource(Res.string.error_invalid_username) else null,
                emailError = if (s.step == RegisterStep.Email && !isValid)
                    UIText.Resource(Res.string.error_invalid_email) else null,
                passwordError = if (s.step == RegisterStep.Password && !isValid)
                    UIText.Resource(Res.string.error_invalid_password) else null,
                registrationError = null,
                step = if (isValid) next else s.step
            )
        }
    }

    private fun goBack() {
        val s = _state.value
        val prev = when (s.step) {
            RegisterStep.Username -> RegisterStep.Username
            RegisterStep.Email -> RegisterStep.Username
            RegisterStep.Password -> RegisterStep.Email
            RegisterStep.Verification -> RegisterStep.Password
        }
        _state.update { it.copy(step = prev) }
    }
}
