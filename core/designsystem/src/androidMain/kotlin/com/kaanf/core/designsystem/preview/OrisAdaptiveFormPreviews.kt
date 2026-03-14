package com.kaanf.core.designsystem.preview

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.kaanf.core.designsystem.component.layout.OrisAdaptiveFormLayout
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme

@Composable
@PreviewLightDark
@PreviewScreenSizes
fun OrisAdaptiveFormLayoutPreview() {
    OrisTheme {
        OrisAdaptiveFormLayout(
            headerText = "What’s your name?",
            descriptionText = "Write your name. You can change it back in settings.",
            onBack = {},
            content = {
                OrisTextField(
                    state = rememberTextFieldState(),
                    placeholder = "test@test.com",
                    title = "Email"
                )
            }
        )
    }
}