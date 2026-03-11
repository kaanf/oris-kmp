package com.kaanf.oris

import androidx.compose.runtime.Composable
import com.kaanf.auth.presentation.register.RegisterRoot
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    OrisTheme {
        RegisterRoot(
            onRegisterSuccess = {}
        )
    }
}
