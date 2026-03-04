package com.kaanf.auth.presentation.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.button.OrisButtonStyle
import com.kaanf.core.designsystem.component.layout.OrisAdaptiveFormLayout
import com.kaanf.core.designsystem.component.layout.OrisSnackbarScaffold
import com.kaanf.core.designsystem.component.textfield.OrisPasswordTextField
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.email
import oris.feature.auth.presentation.generated.resources.email_placeholder
import oris.feature.auth.presentation.generated.resources.login
import oris.feature.auth.presentation.generated.resources.password_hint
import oris.feature.auth.presentation.generated.resources.register
import oris.feature.auth.presentation.generated.resources.select_a_username
import oris.feature.auth.presentation.generated.resources.username
import oris.feature.auth.presentation.generated.resources.username_hint
import oris.feature.auth.presentation.generated.resources.username_placeholder

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    OrisSnackbarScaffold(
        snackbarHostState = snackbarHostState
    ) {
        OrisAdaptiveFormLayout(
            headerText = stringResource(Res.string.select_a_username),
            descriptionText = "Choose a unique username that people will use to find and recognize you on Oris.",
            onBack = if (state.step != RegisterStep.Username) {
                { onAction(RegisterAction.OnBackClick) }
            } else null,
            formContent = {
                when (state.step) {
                    RegisterStep.Username -> {
                        OrisTextField(
                            state = state.usernameTextState,
                            placeholder = stringResource(Res.string.username_placeholder),
                            title = stringResource(Res.string.username),
                            supportingText = state.usernameError?.asString()
                                ?: stringResource(Res.string.username_hint),
                            isError = state.usernameError != null,
                            onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) }
                        )
                    }

                    RegisterStep.Email -> {
                        OrisTextField(
                            state = state.emailTextState,
                            placeholder = stringResource(Res.string.email_placeholder),
                            title = stringResource(Res.string.email),
                            supportingText = state.emailError?.asString(),
                            isError = state.emailError != null,
                            onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) }
                        )
                    }

                    RegisterStep.Password -> {
                        OrisPasswordTextField(
                            state = state.passwordTextState,
                            placeholder = stringResource(Res.string.password_hint),
                            title = stringResource(Res.string.password_hint),
                            supportingText = state.passwordError?.asString()
                                ?: stringResource(Res.string.password_hint),
                            isError = state.passwordError != null,
                            onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) },
                            onToggleVisibility = { onAction(RegisterAction.OnTogglePasswordVisibilityClick) },
                            isPasswordVisible = state.isPasswordVisible
                        )
                    }
                }
            },
            bottomBar = {
                if (!state.isLastStep) {
                    OrisButton(
                        text = "Next",
                        onClick = { onAction(RegisterAction.OnNextClick) },
                        enabled = state.canGoNext,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    OrisButton(
                        text = stringResource(Res.string.register),
                        onClick = { onAction(RegisterAction.OnRegisterClick) },
                        enabled = state.canGoNext, // password valid ise
                        isLoading = state.isRegistering,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OrisTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}
