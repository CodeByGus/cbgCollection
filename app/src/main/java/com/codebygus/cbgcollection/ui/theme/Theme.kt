package com.codebygus.cbgcollection.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val DarkColorSchemeBlueWhite = darkColorScheme(
    primary = PrimaryBlueDark,
    onPrimary = OnPrimaryBlueDark,
    secondary = SecondaryBlueDark,
    onSecondary = OnSecondaryBlueDark,
    tertiary = TertiaryBlueDark,
    onTertiary = OnTertiaryBlueDark
)
private val LightColorSchemeBlueWhite = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = OnPrimaryBlue,
    secondary = SecondaryBlue,
    onSecondary = OnSecondaryBlue,
    tertiary = TertiaryBlue,
    onTertiary = OnTertiaryBlue
)
private val DarkColorSchemeDenimDarkWash = darkColorScheme(
    primary = PrimaryDenimDarkWashDark,
    onPrimary = OnPrimaryDenimDarkWashDark,
    secondary = SecondaryDenimDarkWashDark,
    onSecondary = OnSecondaryDenimDarkWashDark,
    tertiary = TertiaryDenimDarkWashDark,
    onTertiary = OnTertiaryDenimDarkWashDark
)
private val LightColorSchemeDenimDarkWash = lightColorScheme(
    primary = PrimaryDenimDarkWash,
    onPrimary = OnPrimaryDenimDarkWash,
    secondary = SecondaryDenimDarkWash,
    onSecondary = OnSecondaryDenimDarkWash,
    tertiary = TertiaryDenimDarkWash,
    onTertiary = OnTertiaryDenimDarkWash
)
private val DarkColorSchemeGreenWhite = darkColorScheme(
    primary = PrimaryGreenDark,
    onPrimary = OnPrimaryGreenDark,
    secondary = SecondaryGreenDark,
    onSecondary = OnSecondaryGreenDark,
    tertiary = TertiaryGreenDark,
    onTertiary = OnTertiaryGreenDark
)

private val LightColorSchemeGreenWhite = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryGreen,
    secondary = SecondaryGreen,
    onSecondary = OnSecondaryGreen,
    tertiary = TertiaryGreen,
    onTertiary = OnTertiaryGreen
)
private val DarkColorSchemeOrangeWhite = darkColorScheme(
    primary = PrimaryOrangeDark,
    onPrimary = OnPrimaryOrangeDark,
    secondary = SecondaryOrangeDark,
    onSecondary = OnSecondaryOrangeDark,
    tertiary = TertiaryOrangeDark,
    onTertiary = OnTertiaryOrangeDark
)

private val LightColorSchemeOrangeWhite = lightColorScheme(
    primary = PrimaryOrange,
    onPrimary = OnPrimaryOrange,
    secondary = SecondaryOrange,
    onSecondary = OnSecondaryOrange,
    tertiary = TertiaryOrange,
    onTertiary = OnTertiaryOrange
)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean,
    customTheme: String,
    content: @Composable () -> Unit
) {
    Log.d("debugTAG", "AppTheme(dynamicColor): $dynamicColor")
    Log.d("debugTAG", "AppTheme(customTheme): $customTheme")
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme ->
            when(customTheme) {
                "BlueWhite" -> DarkColorSchemeBlueWhite
                "DenimDarkWash" -> DarkColorSchemeDenimDarkWash
                "GreenWhite" -> DarkColorSchemeGreenWhite
                "OrangeWhite" -> DarkColorSchemeOrangeWhite
                else -> DarkColorScheme
            }
        else ->
            when(customTheme) {
            "BlueWhite" -> LightColorSchemeBlueWhite
            "DenimDarkWash" -> LightColorSchemeDenimDarkWash
            "GreenWhite" -> LightColorSchemeGreenWhite
            "OrangeWhite" -> LightColorSchemeOrangeWhite
            else -> LightColorScheme
        }

    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}