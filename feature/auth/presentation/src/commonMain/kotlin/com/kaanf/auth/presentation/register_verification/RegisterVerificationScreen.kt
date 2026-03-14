package com.kaanf.auth.presentation.register_verification

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.button.OrisButtonStyle
import com.kaanf.core.designsystem.component.icon.OrisGlowIcon
import com.kaanf.core.designsystem.component.layout.OrisAdaptiveResultLayout
import com.kaanf.core.designsystem.component.layout.OrisSimpleResultLayout
import com.kaanf.core.designsystem.component.layout.OrisSnackbarScaffold
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary400
import com.kaanf.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.login
import oris.feature.auth.presentation.generated.resources.verification_successful_screen_description
import oris.feature.auth.presentation.generated.resources.verification_successful_screen_resend_link
import oris.feature.auth.presentation.generated.resources.verification_successful_screen_title

@Composable
fun RegisterVerificationRoot(
    viewModel: RegisterVerificationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegisterVerificationEvent.ResendVerificationEmailSuccess -> snackbarHostState.showSnackbar(
                message = "Verification email resent"
            )

            is RegisterVerificationEvent.ResendVerificationEmailFailure -> snackbarHostState.showSnackbar(
                message = event.error
            )
        }
    }

    RegisterVerificationScreen(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun RegisterVerificationScreen(
    state: RegisterVerificationState,
    onAction: (RegisterVerificationAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    OrisSnackbarScaffold(snackbarHostState) {
        OrisAdaptiveResultLayout(
            content = {
                OrisSimpleResultLayout(
                    title = stringResource(Res.string.verification_successful_screen_title),
                    description = stringResource(
                        Res.string.verification_successful_screen_description,
                        state.registeredEmail
                    ),
                    icon = {
                        OrisGlowIcon(
                            icon = Icons.Default.Email,
                            themeColor = Primary400
                        )
                    },
                )
            },
            bottomBar = {
                OrisButton(
                    text = stringResource(Res.string.login),
                    onClick = {
                        onAction(RegisterVerificationAction.OnLoginClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OrisButton(
                    text = stringResource(Res.string.verification_successful_screen_resend_link),
                    onClick = {
                        onAction(RegisterVerificationAction.OnResendVerificationEmailClick)
                    },
                    style = OrisButtonStyle.SECONDARY,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    enabled = !state.isResendingVerificationEmail,
                    isLoading = state.isResendingVerificationEmail
                )
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OrisTheme {
        RegisterVerificationScreen(
            state = RegisterVerificationState(),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}