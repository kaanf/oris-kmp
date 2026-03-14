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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.error_account_exists
import oris.feature.auth.presentation.generated.resources.error_email_exists
import oris.feature.auth.presentation.generated.resources.error_invalid_username
import oris.feature.auth.presentation.generated.resources.error_username_exists

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnNextClick -> {
                when (_state.value.step) {
                    RegisterStep.Username -> checkUsername()
                    RegisterStep.Email -> checkEmail()
                    else -> Unit
                }
            }

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
        if (_state.value.step != RegisterStep.Password)
            return

        if (_state.value.isRegistering)
            return

        if (!_state.value.isPasswordValid)
            return

        register()
    }

    private fun checkUsername() = viewModelScope.launch {
        val currentState = _state.value

        if (!currentState.isUsernameValid) {
            eventChannel.send(
                RegisterEvent.UsernameValidationFailure(
                    UIText.Resource(Res.string.error_invalid_username)
                )
            )

            return@launch
        }

        _state.update {
            it.copy(
                isRegistering = true,
                registrationError = null
            )
        }

        val username = currentState.usernameTextState.text.toString()

        authService
            .isUsernameExists(username)
            .onSuccess { isExists ->
                if (!isExists) {
                    _state.update {
                        it.copy(
                            isRegistering = false,
                            step = RegisterStep.Email
                        )
                    }

                    eventChannel.send(RegisterEvent.UsernameValidationSuccess)
                } else {
                    _state.update {
                        it.copy(
                            isRegistering = false,
                        )
                    }

                    eventChannel.send(
                        RegisterEvent.UsernameValidationFailure(
                            UIText.Resource(Res.string.error_username_exists)
                        )
                    )
                }

            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        isRegistering = false,
                        registrationError = error.toUiText()
                    )
                }

                eventChannel.send(
                    RegisterEvent.UsernameValidationFailure(
                        error.toUiText()
                    )
                )
            }
    }

    private fun checkEmail() = viewModelScope.launch {
        _state.update {
            it.copy(
                isRegistering = true,
                registrationError = null
            )
        }

        val currentState = _state.value

        val email = currentState.emailTextState.text.toString()

        authService
            .isEmailExists(email)
            .onSuccess { isExists ->
                if (!isExists) {
                    _state.update {
                        it.copy(
                            isRegistering = false,
                            step = RegisterStep.Password
                        )
                    }

                    eventChannel.send(RegisterEvent.MailValidationSuccess)
                } else {
                    _state.update {
                        it.copy(
                            isRegistering = false,
                        )
                    }

                    eventChannel.send(
                        RegisterEvent.MailValidationFailure(
                            UIText.Resource(Res.string.error_email_exists)
                        )
                    )
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        isRegistering = false,
                        registrationError = error.toUiText()
                    )
                }
            }
    }

    private fun register() = viewModelScope.launch {
        _state.update {
            it.copy(
                isRegistering = true,
                registrationError = null
            )
        }

        val currentState = _state.value

        val email = currentState.emailTextState.text.toString()
        val username = currentState.usernameTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()

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
                    )
                }

                eventChannel.send(RegisterEvent.Success(email))
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
