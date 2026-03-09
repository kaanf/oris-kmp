package com.kaanf.auth.presentation.register

import androidx.compose.runtime.Composable
import com.kaanf.core.designsystem.component.textfield.OrisPasswordTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.password
import oris.feature.auth.presentation.generated.resources.password_hint

@Composable
fun RegisterPasswordContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    OrisPasswordTextField(
        state = state.passwordTextState,
        placeholder = stringResource(Res.string.password),
        title = stringResource(Res.string.password),
        supportingText = state.passwordError?.asString()
            ?: stringResource(Res.string.password_hint),
        isError = state.passwordError != null,
        onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) },
        onToggleVisibility = { onAction(RegisterAction.OnTogglePasswordVisibilityClick) },
        isPasswordVisible = state.isPasswordVisible
    )
}

@Preview
@Composable
private fun RegisterPasswordContentPreview() {
    OrisTheme {
        RegisterPasswordContent(
            state = RegisterState(step = RegisterStep.Password),
            onAction = {}
        )
    }
}
