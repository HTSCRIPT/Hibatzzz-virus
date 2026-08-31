package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SleekBlueLight,
    onPrimary = Color.White,
    primaryContainer = SleekBlueContainerDark,
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = ShieldGreen,
    onSecondary = Color.Black,
    secondaryContainer = ShieldGreenDarkBg,
    onSecondaryContainer = ShieldGreen,
    tertiary = RiskAmber,
    onTertiary = Color.Black,
    error = DangerRed,
    onError = Color.White,
    errorContainer = Color(0xFF450A0A),
    onErrorContainer = DangerRed,
    background = SleekBgDark,
    onBackground = TextPrimaryDark,
    surface = SleekSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SleekSurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = SleekBorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = SleekBluePrimary,
    onPrimary = Color.White,
    primaryContainer = SleekBlueContainerLight,
    onPrimaryContainer = SleekBlueText,
    secondary = ShieldGreen,
    onSecondary = Color.White,
    secondaryContainer = ShieldGreenContainer,
    onSecondaryContainer = ShieldGreenDark,
    tertiary = RiskAmber,
    onTertiary = Color.White,
    tertiaryContainer = RiskAmberContainer,
    onTertiaryContainer = RiskAmberText,
    error = DangerRed,
    onError = Color.White,
    errorContainer = DangerRedContainer,
    onErrorContainer = DangerRedText,
    background = SleekBgLight,
    onBackground = TextPrimaryLight,
    surface = SleekSurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SleekSurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = SleekBorderLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
