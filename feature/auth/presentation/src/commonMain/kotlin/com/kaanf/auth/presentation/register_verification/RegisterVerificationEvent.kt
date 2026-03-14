package com.kaanf.auth.presentation.register_verification

sealed class RegisterVerificationEvent {
    data object ResendVerificationEmailSuccess: RegisterVerificationEvent()
    data class ResendVerificationEmailFailure(val error: String): RegisterVerificationEvent()
}