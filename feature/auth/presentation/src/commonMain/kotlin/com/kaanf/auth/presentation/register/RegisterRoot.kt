package com.kaanf.auth.presentation.register

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaanf.auth.presentation.register.layout.RegisterAdaptiveScaffold
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.icon.OrisGlowIcon
import com.kaanf.core.designsystem.component.layout.OrisSimpleSuccessLayout
import com.kaanf.core.designsystem.component.layout.OrisSnackbarScaffold
import com.kaanf.core.designsystem.theme.Primary400
import org.jetbrains.compose.resources.stringResource
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.next
import oris.feature.auth.presentation.generated.resources.register
import oris.feature.auth.presentation.generated.resources.select_a_username
import oris.feature.auth.presentation.generated.resources.select_a_username_description
import oris.feature.auth.presentation.generated.resources.write_your_email
import oris.feature.auth.presentation.generated.resources.write_your_email_description
import oris.feature.auth.presentation.generated.resources.write_your_password
import oris.feature.auth.presentation.generated.resources.write_your_password_description

@Composable
fun RegisterRoot(viewModel: RegisterViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

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
            RegisterStep.Username -> stringResource(Res.string.select_a_username)
            RegisterStep.Email -> stringResource(Res.string.write_your_email)
            RegisterStep.Password -> stringResource(Res.string.write_your_password)
            RegisterStep.Verification -> ""
        },
        description = when (state.step) {
            RegisterStep.Username -> stringResource(Res.string.select_a_username_description)
            RegisterStep.Email -> stringResource(Res.string.write_your_email_description)
            RegisterStep.Password -> stringResource(Res.string.write_your_password_description)
            RegisterStep.Verification -> ""
        },
        onBack = if (state.step == RegisterStep.Username || state.step == RegisterStep.Verification) null else {
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
                enabled = state.canGoNext,
                isLoading = state.step == RegisterStep.Password && state.isRegistering,
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

        RegisterStep.Verification -> OrisSimpleSuccessLayout(
            title = "Account created!",
            description = "We've sent an email verification link to your registered email address. Please check your inbox.",
            icon = {
                OrisGlowIcon(
                    icon = Icons.Default.Email,
                    themeColor = Primary400
                )
            },
        )
    }
}

private val RegisterStep.index: Int
    get() = when (this) {
        RegisterStep.Username -> 0
        RegisterStep.Email -> 1
        RegisterStep.Password -> 2
        RegisterStep.Verification -> 3
    }
