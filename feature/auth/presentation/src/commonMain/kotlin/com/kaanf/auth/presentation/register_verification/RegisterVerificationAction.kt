package com.kaanf.auth.presentation.register_verification

sealed interface RegisterVerificationAction {
    data object OnLoginClick : RegisterVerificationAction

    data object OnResendVerificationEmailClick : RegisterVerificationAction
}
