package com.kaanf.auth.presentation.register.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.kaanf.auth.presentation.register.RegisterAction
import com.kaanf.auth.presentation.register.RegisterState
import com.kaanf.auth.presentation.register.RegisterStep
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.mail_screen_hint
import oris.feature.auth.presentation.generated.resources.mail_screen_label
import oris.feature.auth.presentation.generated.resources.mail_screen_placeholder

@Composable
fun RegisterEmailContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    OrisTextField(
        state = state.emailTextState,
        title = stringResource(Res.string.mail_screen_label),
        placeholder = stringResource(Res.string.mail_screen_placeholder),
        supportingText =
            state.emailError?.asString()
                ?: stringResource(Res.string.mail_screen_hint),
        keyboardType = KeyboardType.Email,
        isError = state.emailError != null,
        onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) },
    )
}

@Preview
@Composable
private fun RegisterEmailContentPreview() {
    OrisTheme {
        RegisterEmailContent(
            state = RegisterState(step = RegisterStep.Email),
            onAction = {},
        )
    }
}
