package com.kaanf.auth.presentation.register_verification

data class RegisterVerificationState(
    val registeredEmail: String = "",
    val isResendingVerificationEmail: Boolean = false,
)
