package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.component.icon.OrisGlowIcon
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary400
import com.kaanf.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisSimpleResultLayout(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            color = MaterialTheme.colorScheme.extended.primaryTextColor,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = description,
            color = MaterialTheme.colorScheme.extended.secondaryTextColor,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun OrisSimpleSuccessLayoutPreview() {
    OrisTheme(isDarkTheme = true) {
        OrisSimpleResultLayout(
            title = "Account created!",
            description = "We've sent an email verification link to your registered email address. Please check your inbox.",
            icon = {
                OrisGlowIcon(
                    icon = Icons.Default.Email,
                    themeColor = Primary400
                )
            },
        )
    }
}