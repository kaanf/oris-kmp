package com.kaanf.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import org.jetbrains.compose.resources.Font
import oris.core.designsystem.generated.resources.Res
import oris.core.designsystem.generated.resources.inter_variable

val Inter @Composable get() = FontFamily(
    Font(
        resource = Res.font.inter_variable,
        weight = FontWeight.Light
    ),
    Font(
        resource = Res.font.inter_variable,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.inter_variable,
        weight = FontWeight.Medium
    ),
    Font(
        resource = Res.font.inter_variable,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.inter_variable,
        weight = FontWeight.Bold
    ),
)

val Typography.labelXSmall: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp
    )

val Typography.titleXSmall: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

val Typography.bodyMediumMedium: TextStyle
    @Composable
    get() = bodyMedium.copy(
        fontWeight = FontWeight.Medium
    )

val Typography.bodyMediumSemiBold: TextStyle
    @Composable
    get() = bodyMedium.copy(
        fontWeight = FontWeight.SemiBold
    )

val Typography.bodyLargeMedium: TextStyle
    @Composable
    get() = bodyLarge.copy(
        fontWeight = FontWeight.Medium
    )

val Typography.bodyLargeSemiBold: TextStyle
    @Composable
    get() = bodyLarge.copy(
        fontWeight = FontWeight.SemiBold
    )

val Typography.title16SemiBold: TextStyle
    @Composable
    get() = headlineMedium.copy(
        fontWeight = FontWeight.SemiBold
    )

val Typography.title14SemiBold: TextStyle
    @Composable
    get() = headlineSmall.copy(
        fontWeight = FontWeight.SemiBold
    )

val Typography.buttonSmall: TextStyle
    @Composable
    get() = labelMedium

val Typography.buttonLarge: TextStyle
    @Composable
    get() = labelLarge

val Typography @Composable get() = Typography(
    displayLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp
    )
)