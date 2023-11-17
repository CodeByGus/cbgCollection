package com.codebygus.cbgcollection.cbgDataClasses

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.codebygus.cbgcollection.ui.theme.Blue
import com.codebygus.cbgcollection.ui.theme.ColorTheme

data class ThemeUiState(
    var dynamicColorState: Boolean = false,
    var customColorState: ColorTheme = ColorTheme.BlueWhite,
    var primary: Color = Blue,
    var onPrimary: Color = White
)