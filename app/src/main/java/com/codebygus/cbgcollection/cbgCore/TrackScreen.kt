package com.codebygus.cbgcollection.cbgCore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codebygus.cbgcollection.cbgNavigation.Screen
import com.codebygus.cbgcollection.cbgReusables.CbgGradientButton
import com.codebygus.cbgcollection.ui.theme.Black
import com.codebygus.cbgcollection.ui.theme.appFont
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDarkWhite
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDenimDarkWash
import com.codebygus.cbgcollection.ui.theme.colorPrimaryGold
import com.codebygus.cbgcollection.ui.theme.colorPrimaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackScreen(navController: NavController, appBarTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            title = {
                Text(
                    appBarTitle
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = colorPrimaryDenimDarkWash,
                titleContentColor = colorPrimaryGold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        CbgGradientButton(
            text = "To SelectRootScreen",
            fontFamily = appFont,
            buttonWidth = .8f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Black,
            onClick = {
                navController.navigate(Screen.SelectRootScreen.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}