package com.kaanf.oris

import androidx.compose.runtime.Composable
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.oris.navigation.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    OrisTheme {
        NavigationRoot()
    }
}
