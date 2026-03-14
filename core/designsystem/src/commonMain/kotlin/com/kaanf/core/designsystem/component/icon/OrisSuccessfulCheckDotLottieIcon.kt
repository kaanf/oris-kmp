package com.kaanf.core.designsystem.component.icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrisSuccessfulCheckDotLottieIcon(contentDescription: String? = null) {
    /*
    val composition by rememberLottieComposition {
        LottieCompositionSpec.DotLottie(
            Res.readBytes("files/oris_check.lottie")
        )
    }

    Image(
        modifier = Modifier.size(256.dp),
        painter = rememberLottiePainter(
            composition = composition,
            iterations = 1
        ),
        contentDescription = contentDescription
    )
     */
}

@Preview
@Composable
fun OrisSuccessfulCheckDotLottieIconPreview() {
    OrisSuccessfulCheckDotLottieIcon(null)
}
