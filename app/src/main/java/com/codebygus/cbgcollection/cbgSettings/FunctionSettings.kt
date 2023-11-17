@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.codebygus.cbgcollection.cbgSettings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codebygus.cbgcollection.GlobalApplication
import com.codebygus.cbgcollection.R
import com.codebygus.cbgcollection.cbgDataClasses.TabItem
import com.codebygus.cbgcollection.cbgReusables.CbgGradientButton
import com.codebygus.cbgcollection.cbgReusables.cbgDropDownButton
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.albumListOrder
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.albumListSavePosition
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.albumScrollMethod
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.atAlbumEndAction
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.audioEffectsAvailable
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.entryScrollMethod
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.loadingCountMain
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.loadingCountPlayer
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.loadingCountScroll
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.searchAtStart
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.trackScrollMethod
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.tracksByTitle
import com.codebygus.cbgcollection.cbgSettings.FunctionSettingsCompanion.Companion.typeScrollMethod
import com.codebygus.cbgcollection.cbgViewModels.AppThemeViewModel
import com.codebygus.cbgcollection.cbgViewModels.FunctionSettingsViewModel
import com.codebygus.cbgcollection.ui.theme.AppTheme
import com.codebygus.cbgcollection.ui.theme.Green
import com.codebygus.cbgcollection.ui.theme.appFont
import com.codebygus.cbgcollection.ui.theme.colorPrimaryBlack
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDarkWhite
import com.codebygus.cbgcollection.ui.theme.colorPrimaryWhite
import com.codebygus.cbgcollection.ui.theme.menuOff
import com.codebygus.cbgcollection.ui.theme.menuOn

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FunctionSettings(
    navController: NavController,
    appBarTitle: String,
    appThemeViewModel: AppThemeViewModel = viewModel()
) {
    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.function_tab_header_main),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_function_main)
        ),
        TabItem(
            title = stringResource(id = R.string.function_tab_header_scroll),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_function_scroll)
        ),
        TabItem(
            title = stringResource(id = R.string.function_tab_header_player),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_function_player)
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
                    contentColor = MaterialTheme.colorScheme.onPrimary,
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
                    when (index) {
                        0 -> ShowMain()
                        1 -> ShowScrolling()
                        2 -> ShowPlayer()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowMain(functionSettingsViewModel: FunctionSettingsViewModel = viewModel()) {
    val mainUiState : FunctionSettingsViewModel.ViewState by functionSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountMain <=1) {
        loadingCountMain++
        albumListOrder = mainUiState.albumListOrder
        albumListSavePosition = mainUiState.albumListSavePosition
        tracksByTitle = mainUiState.tracksByTitle
        searchAtStart = mainUiState.searchAtStart
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_main_album_order_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_main_album_order_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var albumOrderExpanded by remember { mutableStateOf(false) }
                val albumOrderActions = arrayOf(
                    stringResource(id = R.string.function_main_album_order_title_excluding),
                    stringResource(id = R.string.function_main_album_order_title_including),
                    stringResource(id = R.string.function_main_album_order_artist_excluding),
                    stringResource(id = R.string.function_main_album_order_artist_including)
                )
                var expandedAlbumOrderMenu by remember { mutableStateOf(false) }
                var selectedAlbumOrderAction = albumOrderActions[albumListOrder]
                ExposedDropdownMenuBox(
                    expanded = albumOrderExpanded,
                    onExpandedChange = {
                        expandedAlbumOrderMenu = !expandedAlbumOrderMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAlbumOrderAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor  = MaterialTheme.colorScheme.onPrimary,
                        onClick = { albumOrderExpanded = !albumOrderExpanded },
                        icon = if (expandedAlbumOrderMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAlbumOrderMenu,
                        onDismissRequest = { expandedAlbumOrderMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        albumOrderActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAlbumOrderAction = item
                                    albumListOrder = albumOrderActions.indexOf(item)
                                    expandedAlbumOrderMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_main_album_list_position_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_main_album_list_position_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var albumListExpanded by remember { mutableStateOf(false) }
                val albumListActions = arrayOf(
                    stringResource(id = R.string.function_main_album_list_position_forget_pos),
                    stringResource(id = R.string.function_main_album_list_position_retain_pos)
                )
                var expandedAlbumListMenu by remember { mutableStateOf(false) }
                var selectedAlbumListAction = if (albumListSavePosition) {
                    albumListActions[1]
                } else {
                    albumListActions[0]
                }
                ExposedDropdownMenuBox(
                    expanded = albumListExpanded,
                    onExpandedChange = {
                        expandedAlbumListMenu = !expandedAlbumListMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAlbumListAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor  = MaterialTheme.colorScheme.onPrimary,
                        onClick = { albumListExpanded = !albumListExpanded },
                        icon = if (expandedAlbumListMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAlbumListMenu,
                        onDismissRequest = { expandedAlbumListMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        albumListActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAlbumListAction = item
                                    albumListSavePosition = albumListActions.indexOf(item) == 1
                                    expandedAlbumListMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_main_track_order_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_main_track_order_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var trackOrderExpanded by remember { mutableStateOf(false) }
                val trackOrderActions = arrayOf(
                    stringResource(id = R.string.function_main_track_order_alphabetical),
                    stringResource(id = R.string.function_main_track_order_numerical)
                )
                var expandedTrackOrderMenu by remember { mutableStateOf(false) }
                var selectedTrackOrderAction = if (tracksByTitle) {
                    trackOrderActions[0]
                } else {
                    trackOrderActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = trackOrderExpanded,
                    onExpandedChange = {
                        expandedTrackOrderMenu = !expandedTrackOrderMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTrackOrderAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { trackOrderExpanded = !trackOrderExpanded },
                        icon = if (expandedTrackOrderMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTrackOrderMenu,
                        onDismissRequest = { expandedTrackOrderMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        trackOrderActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTrackOrderAction = item
                                    tracksByTitle = trackOrderActions.indexOf(item) == 0
                                    expandedTrackOrderMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_main_search_type_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_main_search_type_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var searchOrderExpanded by remember { mutableStateOf(false) }
                val searchOrderActions = arrayOf(
                    stringResource(id = R.string.function_main_search_type_beginning),
                    stringResource(id = R.string.function_main_search_type_containing)
                )
                var expandedSearchOrderMenu by remember { mutableStateOf(false) }
                var selectedSearchOrderAction = if (searchAtStart) {
                    searchOrderActions[0]
                } else {
                    searchOrderActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = searchOrderExpanded,
                    onExpandedChange = {
                        expandedSearchOrderMenu = !expandedSearchOrderMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedSearchOrderAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { searchOrderExpanded = !searchOrderExpanded },
                        icon = if (expandedSearchOrderMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedSearchOrderMenu,
                        onDismissRequest = { expandedSearchOrderMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        searchOrderActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedSearchOrderAction = item
                                    searchAtStart = searchOrderActions.indexOf(item) == 0
                                    expandedSearchOrderMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.function_main_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                functionSettingsViewModel.updateMain(
                    albumListOrder,
                    albumListSavePosition,
                    tracksByTitle,
                    searchAtStart
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Main Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShowPlayer(functionSettingsViewModel: FunctionSettingsViewModel = viewModel()) {
    val playerUiState : FunctionSettingsViewModel.ViewState by functionSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountPlayer <=1) {
        loadingCountPlayer++
        atAlbumEndAction = playerUiState.atAlbumEndAction
        audioEffectsAvailable = playerUiState.audioEffectsAvailable
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_player_album_player_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_player_album_player_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var albumPlayerExpanded by remember { mutableStateOf(false) }
                val albumPlayerActions = arrayOf(
                    stringResource(id = R.string.function_player_album_player_loop),
                    stringResource(id = R.string.function_player_album_player_next),
                    stringResource(id = R.string.function_player_album_player_return)
                )
                var expandedAlbumPlayerMenu by remember { mutableStateOf(false) }
                var selectedAlbumPlayerAction = albumPlayerActions[atAlbumEndAction]
                ExposedDropdownMenuBox(
                    expanded = albumPlayerExpanded,
                    onExpandedChange = {
                        expandedAlbumPlayerMenu = !expandedAlbumPlayerMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAlbumPlayerAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { albumPlayerExpanded = !albumPlayerExpanded },
                        icon = if (expandedAlbumPlayerMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAlbumPlayerMenu,
                        onDismissRequest = { expandedAlbumPlayerMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        albumPlayerActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAlbumPlayerAction = item
                                    atAlbumEndAction = albumPlayerActions.indexOf(item)
                                    expandedAlbumPlayerMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_player_audio_effects_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_player_audio_effects_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var audioEffectsExpanded by remember { mutableStateOf(false) }
                val audioEffectsActions = arrayOf(
                    stringResource(id = R.string.function_player_audio_effects_none),
                    stringResource(id = R.string.function_player_audio_effects_equalizer),
                    stringResource(id = R.string.function_player_audio_effects_dynamics)
                )
                var expandedAudioEffectsMenu by remember { mutableStateOf(false) }
                var selectedAudioEffectsAction = audioEffectsActions[audioEffectsAvailable]
                ExposedDropdownMenuBox(
                    expanded = audioEffectsExpanded,
                    onExpandedChange = {
                        expandedAudioEffectsMenu = !expandedAudioEffectsMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAudioEffectsAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { audioEffectsExpanded = !audioEffectsExpanded },
                        icon = if (expandedAudioEffectsMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAudioEffectsMenu,
                        onDismissRequest = { expandedAudioEffectsMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        audioEffectsActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAudioEffectsAction = item
                                    audioEffectsAvailable = audioEffectsActions.indexOf(item)
                                    expandedAudioEffectsMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.function_player_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                functionSettingsViewModel.updatePlayer(
                    atAlbumEndAction,
                    audioEffectsAvailable
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Player Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShowScrolling(functionSettingsViewModel: FunctionSettingsViewModel = viewModel()) {
    val scrollUiState : FunctionSettingsViewModel.ViewState by functionSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountScroll <=1) {
        loadingCountScroll++
        albumScrollMethod = scrollUiState.albumScrollMethod
        typeScrollMethod = scrollUiState.typeScrollMethod
        trackScrollMethod = scrollUiState.trackScrollMethod
        entryScrollMethod = scrollUiState.entryScrollMethod
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_scroll_album_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_scroll_album_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var albumScrollExpanded by remember { mutableStateOf(false) }
                val albumScrollActions = arrayOf(
                    stringResource(id = R.string.function_scroll_standard),
                    stringResource(id = R.string.function_scroll_fast),
                    stringResource(id = R.string.function_scroll_indexed_no_anim)
                )
                var expandedAlbumScrollMenu by remember { mutableStateOf(false) }
                var selectedAlbumScrollAction = albumScrollActions[albumScrollMethod]
                ExposedDropdownMenuBox(
                    expanded = albumScrollExpanded,
                    onExpandedChange = {
                        expandedAlbumScrollMenu = !expandedAlbumScrollMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAlbumScrollAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { albumScrollExpanded = !albumScrollExpanded },
                        icon = if (expandedAlbumScrollMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAlbumScrollMenu,
                        onDismissRequest = { expandedAlbumScrollMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        albumScrollActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAlbumScrollAction = item
                                    albumScrollMethod = albumScrollActions.indexOf(item)
                                    expandedAlbumScrollMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_scroll_track_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_scroll_track_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var trackScrollExpanded by remember { mutableStateOf(false) }
                val trackScrollActions = arrayOf(
                    stringResource(id = R.string.function_scroll_standard),
                    stringResource(id = R.string.function_scroll_fast)
                )
                var expandedTrackScrollMenu by remember { mutableStateOf(false) }
                var selectedTrackScrollAction = trackScrollActions[trackScrollMethod]
                ExposedDropdownMenuBox(
                    expanded = trackScrollExpanded,
                    onExpandedChange = {
                        expandedTrackScrollMenu = !expandedTrackScrollMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTrackScrollAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { trackScrollExpanded = !trackScrollExpanded },
                        icon = if (expandedTrackScrollMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTrackScrollMenu,
                        onDismissRequest = { expandedTrackScrollMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        trackScrollActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTrackScrollAction = item
                                    trackScrollMethod = trackScrollActions.indexOf(item)
                                    expandedTrackScrollMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_scroll_type_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_scroll_type_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var typeScrollExpanded by remember { mutableStateOf(false) }
                val typeScrollActions = arrayOf(
                    stringResource(id = R.string.function_scroll_standard),
                    stringResource(id = R.string.function_scroll_fast),
                    stringResource(id = R.string.function_scroll_indexed_anim),
                    stringResource(id = R.string.function_scroll_indexed_no_anim)
                )
                var expandedTypeScrollMenu by remember { mutableStateOf(false) }
                var selectedTypeScrollAction = typeScrollActions[typeScrollMethod]
                ExposedDropdownMenuBox(
                    expanded = typeScrollExpanded,
                    onExpandedChange = {
                        expandedTypeScrollMenu = !expandedTypeScrollMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTypeScrollAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { typeScrollExpanded = !typeScrollExpanded },
                        icon = if (expandedTypeScrollMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTypeScrollMenu,
                        onDismissRequest = { expandedTypeScrollMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        typeScrollActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTypeScrollAction = item
                                    typeScrollMethod = typeScrollActions.indexOf(item)
                                    expandedTypeScrollMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.function_scroll_playlist_entry_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.function_scroll_playlist_entry_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var entryScrollExpanded by remember { mutableStateOf(false) }
                val entryScrollActions = arrayOf(
                    stringResource(id = R.string.function_scroll_standard),
                    stringResource(id = R.string.function_scroll_fast),
                    stringResource(id = R.string.function_scroll_indexed_anim),
                    stringResource(id = R.string.function_scroll_indexed_no_anim),
                    stringResource(id = R.string.function_scroll_list_size_based)
                )
                var expandedEntryScrollMenu by remember { mutableStateOf(false) }
                var selectedEntryScrollAction = entryScrollActions[entryScrollMethod]
                ExposedDropdownMenuBox(
                    expanded = entryScrollExpanded,
                    onExpandedChange = {
                        expandedEntryScrollMenu = !expandedEntryScrollMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedEntryScrollAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { entryScrollExpanded = !entryScrollExpanded },
                        icon = if (expandedEntryScrollMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEntryScrollMenu,
                        onDismissRequest = { expandedEntryScrollMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        entryScrollActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedEntryScrollAction = item
                                    entryScrollMethod = entryScrollActions.indexOf(item)
                                    expandedEntryScrollMenu = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.function_scroll_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                functionSettingsViewModel.updateScrolling(
                    albumScrollMethod,
                    typeScrollMethod,
                    trackScrollMethod,
                    entryScrollMethod
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Scrolling Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

class FunctionSettingsCompanion {
    companion object {
        @SuppressLint("StaticFieldLeak")
        // Main
        var loadingCountMain = 0
        var albumListOrder = 0
        var albumListSavePosition = false
        var tracksByTitle = true
        var searchAtStart = true
        // Player
        var loadingCountPlayer = 0
        var atAlbumEndAction = 0
        var audioEffectsAvailable = 0
        // Scrolling
        var loadingCountScroll = 0
        var albumScrollMethod = 0
        var typeScrollMethod = 0
        var trackScrollMethod = 0
        var entryScrollMethod = 0
    }
}