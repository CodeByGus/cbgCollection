package com.codebygus.cbgcollection.cbgReusables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codebygus.cbgcollection.ui.theme.appFont

@Composable
fun cbgDropDownButton(
    text: String,
    fontFamily: FontFamily,
    buttonWidth: Float,
    colorGradient: List<Color>,
    fontSize: Int,
    fontColor: Color,
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(buttonWidth)
            .height(40.dp)
            .clickable(onClick = onClick)
            .background(
                Brush.verticalGradient(
                    colors = colorGradient
                )
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = text,
                fontFamily = appFont,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold,
                color = fontColor,
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = fontColor
            )
        }
    }
}