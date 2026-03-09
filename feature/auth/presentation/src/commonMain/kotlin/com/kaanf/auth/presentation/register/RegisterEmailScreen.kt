package com.kaanf.auth.presentation.register

import androidx.compose.runtime.Composable
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.email
import oris.feature.auth.presentation.generated.resources.email_hint
import oris.feature.auth.presentation.generated.resources.email_placeholder

@Composable
fun RegisterEmailContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    OrisTextField(
        state = state.emailTextState,
        placeholder = stringResource(Res.string.email_placeholder),
        title = stringResource(Res.string.email),
        supportingText = state.emailError?.asString()
            ?: stringResource(Res.string.email_hint),
        isError = state.emailError != null,
        onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) }
    )
}

@Preview
@Composable
private fun RegisterEmailContentPreview() {
    OrisTheme {
        RegisterEmailContent(
            state = RegisterState(step = RegisterStep.Email),
            onAction = {}
        )
    }
}
