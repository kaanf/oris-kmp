package com.kaanf.auth.presentation.register

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.auth.presentation.register.content.RegisterEmailContent
import com.kaanf.auth.presentation.register.content.RegisterPasswordContent
import com.kaanf.auth.presentation.register.content.RegisterUsernameContent
import com.kaanf.auth.presentation.register.layout.RegisterAdaptiveScaffold
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.layout.OrisSnackbarScaffold
import com.kaanf.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.mail_screen_description
import oris.feature.auth.presentation.generated.resources.mail_screen_title
import oris.feature.auth.presentation.generated.resources.next
import oris.feature.auth.presentation.generated.resources.password_screen_description
import oris.feature.auth.presentation.generated.resources.password_screen_title
import oris.feature.auth.presentation.generated.resources.register
import oris.feature.auth.presentation.generated.resources.username_screen_description
import oris.feature.auth.presentation.generated.resources.username_screen_title

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterSuccess: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Success -> {
                onRegisterSuccess(event.email)
            }

            is RegisterEvent.UsernameValidationSuccess -> {

            }

            is RegisterEvent.MailValidationSuccess -> {

            }

            is RegisterEvent.UsernameValidationFailure, -> {
                snackbarHostState.showSnackbar(
                    event.message.asStringAsync()
                )
            }

            is RegisterEvent.MailValidationFailure -> {
                snackbarHostState.showSnackbar(
                    event.message.asStringAsync()
                )
            }
        }
    }

    OrisSnackbarScaffold(snackbarHostState) { innerPadding ->
        RegisterScreen(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            onAction = viewModel::onAction,
        )
    }
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier
) {
    RegisterAdaptiveScaffold(
        modifier = modifier,
        title = when (state.step) {
            RegisterStep.Username -> stringResource(Res.string.username_screen_title)
            RegisterStep.Email -> stringResource(Res.string.mail_screen_title)
            RegisterStep.Password -> stringResource(Res.string.password_screen_title)
        },
        description = when (state.step) {
            RegisterStep.Username -> stringResource(Res.string.username_screen_description)
            RegisterStep.Email -> stringResource(Res.string.mail_screen_description)
            RegisterStep.Password -> stringResource(Res.string.password_screen_description)
        },
        onBack = if (state.step == RegisterStep.Username) null else {
            { onAction(RegisterAction.OnBackClick) }
        },
        content = {
            RegisterStepContent(
                state = state,
                onAction = onAction
            )
        },
        bottomBar = {
            OrisButton(
                text = if (state.step == RegisterStep.Password) {
                    stringResource(Res.string.register)
                } else {
                    stringResource(Res.string.next)
                },
                onClick = {
                    if (state.step == RegisterStep.Password) {
                        onAction(RegisterAction.OnRegisterClick)
                    } else {
                        onAction(RegisterAction.OnNextClick)
                    }
                },
                enabled = when (state.step) {
                    RegisterStep.Username -> state.isUsernameValid
                    RegisterStep.Email -> state.isEmailValid
                    RegisterStep.Password -> !state.isRegistering && state.isPasswordValid
                },
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun RegisterStepContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    when (state.step) {
        RegisterStep.Username -> RegisterUsernameContent(
            state = state,
            onAction = onAction
        )

        RegisterStep.Email -> RegisterEmailContent(
            state = state,
            onAction = onAction
        )

        RegisterStep.Password -> RegisterPasswordContent(
            state = state,
            onAction = onAction
        )
    }
}