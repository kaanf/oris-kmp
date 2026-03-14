package com.kaanf.auth.presentation.register.content

import androidx.compose.runtime.Composable
import com.kaanf.auth.presentation.register.RegisterAction
import com.kaanf.auth.presentation.register.RegisterState
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.username_screen_hint
import oris.feature.auth.presentation.generated.resources.username_screen_label
import oris.feature.auth.presentation.generated.resources.username_screen_placeholder

@Composable
fun RegisterUsernameContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    OrisTextField(
        state=state.usernameTextState,
        title = stringResource(Res.string.username_screen_label),
        placeholder = stringResource(Res.string.username_screen_placeholder),
        supportingText = state.usernameError?.asString()
            ?: stringResource(Res.string.username_screen_hint),
        isError = state.usernameError != null,
        onFocusChanged = { onAction(RegisterAction.OnInputTextFocusGain) },
        singleLine = true
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
