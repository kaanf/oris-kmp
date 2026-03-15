package com.kaanf.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.component.button.OrisButton
import com.kaanf.core.designsystem.component.button.OrisButtonStyle
import com.kaanf.core.designsystem.component.icon.OrisGlowIcon
import com.kaanf.core.designsystem.component.layout.OrisAdaptiveResultLayout
import com.kaanf.core.designsystem.component.layout.OrisSimpleResultLayout
import com.kaanf.core.designsystem.theme.OrisTheme
import com.kaanf.core.designsystem.theme.Primary400
import com.kaanf.core.designsystem.theme.Red400
import com.kaanf.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import oris.feature.auth.presentation.generated.resources.Res
import oris.feature.auth.presentation.generated.resources.close
import oris.feature.auth.presentation.generated.resources.email_verified_failed
import oris.feature.auth.presentation.generated.resources.email_verified_failed_desc
import oris.feature.auth.presentation.generated.resources.email_verified_successfully
import oris.feature.auth.presentation.generated.resources.email_verified_successfully_desc
import oris.feature.auth.presentation.generated.resources.login
import oris.feature.auth.presentation.generated.resources.verifying_account

@Composable
fun EmailVerificationRoot(viewModel: EmailVerificationViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EmailVerificationScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun EmailVerificationScreen(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit,
) {
    OrisAdaptiveResultLayout(
        content = {
            when {
                state.isVerifying -> {
                    VerifyingContent(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    )
                }

                state.isVerified -> {
                    OrisSimpleResultLayout(
                        title = stringResource(Res.string.email_verified_successfully),
                        description = stringResource(Res.string.email_verified_successfully_desc),
                        icon = {
                            OrisGlowIcon(
                                icon = Icons.Default.Check,
                                themeColor = Primary400,
                            )
                        },
                    )
                }

                else -> {
                    OrisSimpleResultLayout(
                        title = stringResource(Res.string.email_verified_failed),
                        description = stringResource(Res.string.email_verified_failed_desc),
                        icon = {
                            OrisGlowIcon(
                                icon = Icons.Default.Close,
                                themeColor = Red400,
                            )
                        },
                    )
                }
            }
        },
        bottomBar = {
            when {
                state.isVerifying -> {
                    VerifyingContent(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    )
                }

                state.isVerified -> {
                    OrisButton(
                        text = stringResource(Res.string.login),
                        onClick = {
                            onAction(EmailVerificationAction.OnLoginClick)
                        },
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(horizontal = 24.dp),
                    )
                }

                else -> {
                    OrisButton(
                        text = stringResource(Res.string.close),
                        onClick = {
                            onAction(EmailVerificationAction.OnCloseClick)
                        },
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(horizontal = 24.dp),
                        style = OrisButtonStyle.SECONDARY,
                    )
                }
            }
        },
    )
}

@Composable
private fun VerifyingContent(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .heightIn(min = 200.dp)
                .padding(16.dp),
        verticalArrangement =
            Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier =
                Modifier
                    .size(64.dp),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(Res.string.verifying_account),
            color = MaterialTheme.colorScheme.extended.textSecondary,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun EmailVerificationErrorPreview() {
    OrisTheme(isDarkTheme = true) {
        EmailVerificationScreen(
            state = EmailVerificationState(),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun EmailVerificationVerifyingPreview() {
    OrisTheme(isDarkTheme = true) {
        EmailVerificationScreen(
            state =
                EmailVerificationState(
                    isVerifying = true,
                ),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun EmailVerificationSuccessPreview() {
    OrisTheme(isDarkTheme = true) {
        EmailVerificationScreen(
            state =
                EmailVerificationState(
                    isVerified = true,
                ),
            onAction = {},
        )
    }
}
