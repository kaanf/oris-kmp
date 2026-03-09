package com.kaanf.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.core.presentation.util.UIText

enum class RegisterStep { Username, Email, Password, Verification }

enum class InputField { Username, Email, Password }

data class RegisterState(
    val step: RegisterStep = RegisterStep.Username,
    val emailTextState: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val emailError: UIText? = null,
    val passwordTextState: TextFieldState = TextFieldState(),
    val isPasswordValid: Boolean = false,
    val passwordError: UIText? = null,
    val usernameTextState: TextFieldState = TextFieldState(),
    val isUsernameValid: Boolean = true,
    val usernameError: UIText? = null,
    val registrationError: UIText? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
    val isPasswordVisible: Boolean = false
) {
    val canGoNext: Boolean
        get() = when (step) {
            RegisterStep.Username -> true
            RegisterStep.Email -> true
            RegisterStep.Password -> true
            RegisterStep.Verification -> false
        }

    val isLastStep: Boolean get() = step == RegisterStep.Password
}