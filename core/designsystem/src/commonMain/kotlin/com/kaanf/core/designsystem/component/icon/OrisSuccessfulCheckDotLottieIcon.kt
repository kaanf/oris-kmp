package com.kaanf.core.designsystem.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import oris.core.designsystem.generated.resources.Res

@Composable
fun OrisSuccessfulCheckDotLottieIcon(
    contentDescription: String? = null,
) {
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