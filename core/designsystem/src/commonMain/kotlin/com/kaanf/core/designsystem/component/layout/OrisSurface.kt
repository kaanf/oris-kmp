package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.button.OrisIconButton
import com.kaanf.core.designsystem.component.textfield.OrisTextField
import com.kaanf.core.designsystem.theme.OrisTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisSurface(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            header()

            content()

            Spacer(modifier = Modifier.weight(1f))

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
                Spacer(Modifier.height(24.dp))

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
                Spacer(Modifier.height(24.dp))

                OrisTextField(
                    state = rememberTextFieldState(),
                    placeholder = "test@test.com",
                    title = "Email"
                )

            },
        )
    }
}