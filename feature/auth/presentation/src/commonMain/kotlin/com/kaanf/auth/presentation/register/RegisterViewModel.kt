package com.kaanf.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.validation.PasswordValidator
import com.kaanf.core.presentation.util.UIText
import com.kaanf.domain.EmailValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.error_invalid_email
import oris.feature.auth.presentation.generated.resources.error_invalid_password
import oris.feature.auth.presentation.generated.resources.error_invalid_username

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
            RegisterAction.OnNextClick,
            RegisterAction.OnRegisterClick -> goNext()
            RegisterAction.OnBackClick -> goBack()
            RegisterAction.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
            else -> {}
        }
    }

    private fun clearAllTextFieldErrors() {
        _state.update {
            it.copy(
                emailError = null,
                usernameError = null,
                passwordError = null,
                registrationError = null
            )
        }
    }

    private fun clearEmailFieldError() {
        _state.update {
            it.copy(
                emailError = null,
                registrationError = null
            )
        }
    }

    private fun clearPasswordFieldError() {
        _state.update {
            it.copy(
                passwordError = null,
                registrationError = null
            )
        }
    }

    private fun clearUsernameFieldError() {
        _state.update {
            it.copy(
                usernameError = null,
                registrationError = null
            )
        }
    }

    private fun isEmailValid(): Boolean {
        clearEmailFieldError()

        val currentState = state.value

        val email = currentState.emailTextState.text.toString()
        val isEmailValid = EmailValidator.validate(email)

        val emailError = if (!isEmailValid) {
            UIText.Resource(Res.string.error_invalid_email)
        } else null

        _state.update {
            it.copy(
                emailError = emailError,
                isEmailValid = isEmailValid
            )
        }

        return isEmailValid
    }

    private fun isPasswordValid(): Boolean {
        clearPasswordFieldError()

        val currentState = state.value

        val password = currentState.passwordTextState.text.toString()
        val passwordValidationState = PasswordValidator.validate(password)

        val passwordError = if (!passwordValidationState.isValidPassword) {
            UIText.Resource(Res.string.error_invalid_password)
        } else null

        _state.update {
            it.copy(
                passwordError = passwordError,
                isPasswordValid = passwordValidationState.isValidPassword
            )
        }

        return passwordValidationState.isValidPassword
    }

    private fun isUsernameValid(): Boolean {
        clearUsernameFieldError()

        val currentState = state.value

        val username = currentState.usernameTextState.text.toString()
        val isUsernameValid = username.length in 3..20

        val usernameError = if (!isUsernameValid) {
            UIText.Resource(Res.string.error_invalid_username)
        } else null

        _state.update {
            it.copy(
                usernameError = usernameError,
                isUsernameValid = isUsernameValid
            )
        }

        return isUsernameValid
    }

    private fun goNext() {
        val s = _state.value
        val isCurrentStepValid = when (s.step) {
            RegisterStep.Username -> isUsernameValid()
            RegisterStep.Email -> isEmailValid()
            RegisterStep.Password -> isPasswordValid()
            RegisterStep.Verification -> false
        }

        if (!isCurrentStepValid) return

        val next = when (s.step) {
            RegisterStep.Username -> RegisterStep.Email
            RegisterStep.Email -> RegisterStep.Password
            RegisterStep.Password -> RegisterStep.Verification
            RegisterStep.Verification -> RegisterStep.Verification
        }

        _state.update { it.copy(step = next) }
    }

    /*
    private fun goNext() {
        val s = _state.value
        if (!s.canGoNext) return

        val next = when (s.step) {
            RegisterStep.Username -> RegisterStep.Email
            RegisterStep.Email -> RegisterStep.Password
            RegisterStep.Password -> RegisterStep.Verification
            RegisterStep.Verification -> RegisterStep.Verification
        }

        _state.update { it.copy(step = next) }
    }
     */
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

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }
}
