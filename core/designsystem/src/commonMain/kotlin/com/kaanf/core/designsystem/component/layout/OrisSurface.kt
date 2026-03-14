package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisSurface(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            header()

            Spacer(Modifier.height(24.dp))

            content()

            bottomBar()
        }
    }
}

@Composable
@Preview
fun OrisSurfacePreview() {
    OrisTheme(isDarkTheme = true) {
        OrisSurface(
            header = {
                Text(
                    text = "What’s your name?",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Write your name. You can change it back in settings.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            content = {
                OrisTextField(
                    state = rememberTextFieldState(),
                    placeholder = "test@test.com",
                    title = "Email"
                )
            },
        )
    }
}