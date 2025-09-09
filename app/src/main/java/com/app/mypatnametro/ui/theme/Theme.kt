package com.app.mypatnametro.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val PatnaMetroDarkColorScheme = darkColorScheme(
    primary = PatnaMetroPrimary,
    secondary = PatnaMetroSecondary,
    tertiary = PatnaMetroAccent,
    background = BgColorDark,
    surface = CardColorDark,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnPrimary,
    onTertiary = TextOnPrimary,
    onBackground = TextOnPrimary,
    onSurface = TextOnPrimary
)

private val PatnaMetroLightColorScheme = lightColorScheme(
    primary = PatnaMetroPrimary,
    secondary = PatnaMetroSecondary,
    tertiary = PatnaMetroAccent,
    background = BgColorLight,
    surface = CardColorLight,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnPrimary,
    onTertiary = TextOnPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun PatnaMetroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to maintain brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> PatnaMetroDarkColorScheme
        else -> PatnaMetroLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Legacy theme name for compatibility
@Composable
fun MypatnametroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    PatnaMetroTheme(darkTheme, dynamicColor, content)
}