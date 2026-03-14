package com.kaanf.auth.presentation.register_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterVerificationViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val eventChannel = Channel<RegisterVerificationEvent>()
    val events = eventChannel.receiveAsFlow()

    private val email = savedStateHandle.get<String>("email")
        ?: throw IllegalStateException("No email passed to register success screen")

    private val _state = MutableStateFlow(
        RegisterVerificationState(
            registeredEmail = email
        )
    )
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterVerificationState()
        )

    fun onAction(action: RegisterVerificationAction) {
        when (action) {
            RegisterVerificationAction.OnLoginClick -> {}
            RegisterVerificationAction.OnResendVerificationEmailClick -> resendVerificationLink()
        }
    }

    private fun resendVerificationLink() {
        if (_state.value.isResendingVerificationEmail) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(isResendingVerificationEmail = true)
            }

            authService
                .resendVerificationMail(email)
                .onSuccess {
                    _state.update {
                        it.copy(isResendingVerificationEmail = false)
                    }

                    eventChannel.send(
                        RegisterVerificationEvent.ResendVerificationEmailSuccess
                    )
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isResendingVerificationEmail = false)
                    }
                    // Add error state

                    eventChannel.send(
                        RegisterVerificationEvent.ResendVerificationEmailFailure(
                            error.toUiText().toString()
                        )
                    )
                }
        }
    }

}
