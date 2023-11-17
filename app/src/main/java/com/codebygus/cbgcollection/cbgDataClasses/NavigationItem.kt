package com.codebygus.cbgcollection.cbgDataClasses

import androidx.compose.ui.graphics.ImageBitmap

data class NavigationItem(
    val title: String,
    val icon: ImageBitmap,
    val badgeCount: Int? = null,
    val usesDivider: Boolean
)