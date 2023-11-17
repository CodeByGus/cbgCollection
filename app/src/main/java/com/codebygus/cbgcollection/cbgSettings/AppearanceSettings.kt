@file:OptIn(ExperimentalMaterial3Api::class)

package com.codebygus.cbgcollection.cbgSettings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codebygus.cbgcollection.R
import com.codebygus.cbgcollection.cbgDataClasses.TabItem
import com.codebygus.cbgcollection.cbgReusables.CbgGradientButton
import com.codebygus.cbgcollection.cbgViewModels.AppThemeViewModel
import com.codebygus.cbgcollection.ui.theme.AppTheme
import com.codebygus.cbgcollection.ui.theme.Green
import com.codebygus.cbgcollection.ui.theme.appFont
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDarkWhite
import com.codebygus.cbgcollection.ui.theme.colorPrimaryWhite
import com.codebygus.cbgcollection.ui.theme.menuOff
import com.codebygus.cbgcollection.ui.theme.menuOn

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppearanceSettings(
    navController: NavController,
    appBarTitle: String,
    appThemeViewModel: AppThemeViewModel = viewModel()
) {
    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.appearance_tab_header_colours),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_appearance_theme)
        ),
        TabItem(
            title = stringResource(id = R.string.appearance_tab_header_background),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_appearance_background)
        ),
        TabItem(
            title = stringResource(id = R.string.appearance_tab_header_status_bar),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_appearance_status_bar)
        )
    )
    val themeUiState : AppThemeViewModel.ViewState by appThemeViewModel.viewState.collectAsStateWithLifecycle()
    AppTheme(
        dynamicColor = themeUiState.dynamicColors,
        customTheme = themeUiState.customTheme
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = appBarTitle)
                    },
                    navigationIcon = {
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                navController.popBackStack()
                            })
                        {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.menu_go_back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { values ->
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }
            val pagerState = rememberPagerState(
                pageCount = {
                    tabItems.size
                }
            )
            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(
                                    text = tabItem.title,
                                    fontFamily = appFont,
                                )
                            },
                            icon = {
                                Icon(
                                    bitmap = tabItem.tabIcon,
                                    contentDescription = tabItem.title,
                                    modifier = Modifier.size(
                                        if (index == selectedTabIndex) {
                                            menuOn
                                        } else {
                                            menuOff
                                        }
                                    )
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { index ->
                    when(index) {
                        0 -> ShowColorSchemes()
                        1 -> ShowScreen()
                        2 -> ShowStatusBar()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowColorSchemes() {
    loadAppearanceColourSettings()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.appearance_colours_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                updateAppearanceColourSettings()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun ShowScreen() {
    loadAppearanceScreenSettings()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.appearance_screen_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                updateAppearanceScreenSettings()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShowStatusBar() {
    loadAppearanceStatusBarSettings()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.appearance_status_bar_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                updateAppearanceStatusBarSettings()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

fun loadAppearanceColourSettings() {

}
fun updateAppearanceColourSettings() {

}

fun loadAppearanceScreenSettings() {

}
fun updateAppearanceScreenSettings() {

}

fun loadAppearanceStatusBarSettings() {

}
fun updateAppearanceStatusBarSettings() {

}