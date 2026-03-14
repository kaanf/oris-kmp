package com.kaanf.auth.presentation.register

import com.kaanf.core.presentation.util.UIText

sealed interface RegisterEvent {
    data class UsernameValidationFailure(val message: UIText) : RegisterEvent
    data class MailValidationFailure(val message: UIText) : RegisterEvent
    object UsernameValidationSuccess: RegisterEvent
    object MailValidationSuccess: RegisterEvent
    data class Success(val email: String): RegisterEvent
}