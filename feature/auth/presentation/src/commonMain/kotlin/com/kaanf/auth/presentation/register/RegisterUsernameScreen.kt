package com.kaanf.auth.presentation.register

import androidx.compose.runtime.Composable
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.username
import oris.feature.auth.presentation.generated.resources.username_hint
import oris.feature.auth.presentation.generated.resources.username_placeholder

@Composable
fun RegisterUsernameContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    OrisTextField(
        state = state.usernameTextState,
        placeholder = stringResource(Res.string.username_placeholder),
        title = stringResource(Res.string.username),
        singleLine = true,
        supportingText = state.usernameError?.asString()
            ?: stringResource(Res.string.username_hint),
        isError = state.usernameError != null,
        onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) }
    )
}

@Preview
@Composable
private fun RegisterUsernameContentPreview() {
    OrisTheme {
        RegisterUsernameContent(
            state = RegisterState(),
            onAction = {}
        )
    }
}
