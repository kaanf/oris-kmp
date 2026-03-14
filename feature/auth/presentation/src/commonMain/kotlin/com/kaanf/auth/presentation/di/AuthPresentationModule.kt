package com.kaanf.auth.presentation.di

import com.kaanf.auth.presentation.email_verification.EmailVerificationViewModel
import com.kaanf.auth.presentation.register.RegisterViewModel
import com.kaanf.auth.presentation.register_verification.RegisterVerificationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterVerificationViewModel)
    viewModelOf(::EmailVerificationViewModel)
}