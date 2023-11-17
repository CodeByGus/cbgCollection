@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.codebygus.cbgcollection.cbgSettings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.artistSelectListFiltering
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.atPlaylistStartAction
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.atTrackPlaylistEndAction
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.atTypePlaylistEndAction
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.defaultPlaylistSortingMethod
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.externalPlaylistFormatMethod
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.genreSelectListFilteringExact
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.labelSelectListFilteringExact
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.loadingCountFilters
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.loadingCountOrders
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.loadingCountPlayers
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.loadingCountSetup
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.navController
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.playlistArtistExact
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.playlistConfigAction
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.playlistItemAdditionMethod
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.shareOnExternalFileCreate
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.showAttachmentDialog
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.styleSelectListFilteringExact
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.typeSelectListSortingMethod
import com.codebygus.cbgcollection.cbgViewModels.AppThemeViewModel
import com.codebygus.cbgcollection.cbgViewModels.PlaylistSettingsViewModel
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
fun PlaylistSettings(
    inputNavController: NavController,
    appBarTitle: String,
    appThemeViewModel: AppThemeViewModel = viewModel()
) {
    navController = inputNavController
    val tabItems = listOf(
        TabItem(
            title = stringResource(id = R.string.playlist_tab_header_setup),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_playlist_setup)
        ),
        TabItem(
            title = stringResource(id = R.string.playlist_tab_header_order),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_playlist_order)
        ),
        TabItem(
            title = stringResource(id = R.string.playlist_tab_header_filter),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_playlist_filter)
        ),
        TabItem(
            title = stringResource(id = R.string.playlist_tab_header_players),
            tabIcon = ImageBitmap.imageResource(R.drawable.ic_tab_playlist_player)
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
                                loadingCountSetup = 0
                                loadingCountFilters = 0
                                loadingCountOrders = 0
                                loadingCountPlayers = 0
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
                        0 -> ShowSetUp()
                        1 -> ShowOrder()
                        2 -> ShowFilter()
                        3 -> ShowPlayers()
                    }
                }
            }
            BackHandler {
                loadingCountSetup = 0
                loadingCountFilters = 0
                loadingCountOrders = 0
                loadingCountPlayers = 0
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun ShowSetUp(playlistSettingsViewModel: PlaylistSettingsViewModel = viewModel()) {
    val setupUiState : PlaylistSettingsViewModel.ViewState by playlistSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountSetup <=1) {
        loadingCountSetup ++
        playlistArtistExact = setupUiState.playlistArtistExact
        showAttachmentDialog = setupUiState.showAttachmentDialog
        shareOnExternalFileCreate = setupUiState.shareOnExternalFileCreate
        playlistItemAdditionMethod = setupUiState.playlistItemAdditionMethod
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Playlist entry addition
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.playlist_setup_add_entry_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_setup_add_entry_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var addExpanded by remember { mutableStateOf(false) }
                val addActions = arrayOf(
                    stringResource(id = R.string.playlist_setup_action_confirm),
                    stringResource(id = R.string.playlist_setup_action_continue),
                    stringResource(id = R.string.playlist_setup_action_exit)
                )
                var expandedAddMenu by remember { mutableStateOf(false) }
                var selectedAddAction = addActions[playlistItemAdditionMethod]
                ExposedDropdownMenuBox(
                    expanded = addExpanded,
                    onExpandedChange = {
                        expandedAddMenu = !expandedAddMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedAddAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor  = MaterialTheme.colorScheme.onPrimary,
                        onClick = { addExpanded = !addExpanded },
                        icon = if (expandedAddMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAddMenu,
                        onDismissRequest = { expandedAddMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        addActions.forEach {  item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedAddAction = item
                                    playlistItemAdditionMethod = addActions.indexOf(item)
                                    expandedAddMenu = false
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
        // Playlist external file creation
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.playlist_setup_external_share_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_setup_external_share_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var externalExpanded by remember { mutableStateOf(false) }
                val externalActions = arrayOf(
                    stringResource(id = R.string.playlist_setup_external_share_on_create),
                    stringResource(id = R.string.playlist_setup_external_share_from_list)
                )
                var expandedExternalMenu by remember { mutableStateOf(false) }
                var selectedExternalAction = if(shareOnExternalFileCreate) {
                    externalActions[0]
                }  else {
                    externalActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = externalExpanded,
                    onExpandedChange = {
                        expandedExternalMenu = !expandedExternalMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedExternalAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { externalExpanded = !externalExpanded },
                        icon = if (expandedExternalMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedExternalMenu,
                        onDismissRequest = { expandedExternalMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        externalActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedExternalAction = item
                                    shareOnExternalFileCreate = externalActions[0] == item
                                    expandedExternalMenu = false
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
        // Playlist email attachment size warning
        Box(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.playlist_setup_email_share_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_setup_email_share_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var emailExpanded by remember { mutableStateOf(false) }
                val emailActions = arrayOf(
                    stringResource(id = R.string.playlist_setup_email_share_action_on),
                    stringResource(id = R.string.playlist_setup_email_share_action_off)
                )
                var expandedEmailMenu by remember { mutableStateOf(false) }
                var selectedEmailAction = if (showAttachmentDialog) {
                    emailActions[0]
                } else {
                    emailActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = emailExpanded,
                    onExpandedChange = {
                        expandedEmailMenu = !expandedEmailMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedEmailAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor  = MaterialTheme.colorScheme.onPrimary,
                        onClick = { emailExpanded = !emailExpanded },
                        icon = if (expandedEmailMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEmailMenu,
                        onDismissRequest = { expandedEmailMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        emailActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedEmailAction = item
                                    showAttachmentDialog = emailActions[0] == item
                                    expandedEmailMenu = false
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
        // Update settings in datastore
        Spacer(modifier = Modifier.height(10.dp))
        CbgGradientButton(
            text = stringResource(id = R.string.playlist_setup_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                playlistSettingsViewModel.updateSetup(
                    playlistArtistExact,
                    showAttachmentDialog,
                    shareOnExternalFileCreate,
                    playlistItemAdditionMethod
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Setup Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun ShowFilter(playlistSettingsViewModel: PlaylistSettingsViewModel = viewModel()) {
    val filterUiState : PlaylistSettingsViewModel.ViewState by playlistSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountFilters <= 1) {
        loadingCountFilters ++
        artistSelectListFiltering = filterUiState.artistSelectListFiltering
        genreSelectListFilteringExact = filterUiState.genreSelectListFilteringExact
        labelSelectListFilteringExact = filterUiState.labelSelectListFilteringExact
        styleSelectListFilteringExact = filterUiState.styleSelectListFilteringExact
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
                    text = stringResource(id = R.string.playlist_filter_artist_list_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_filter_artist_list_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var artistExpanded by remember { mutableStateOf(false) }
                val artistActions = arrayOf(
                    stringResource(id = R.string.playlist_filter_artist_exact_only),
                    stringResource(id = R.string.playlist_filter_artist_included),
                    stringResource(id = R.string.playlist_filter_artist_associated)
                )
                var expandedArtistMenu by remember { mutableStateOf(false) }
                var selectedArtistAction = artistActions[artistSelectListFiltering]
                ExposedDropdownMenuBox(
                    expanded = artistExpanded,
                    onExpandedChange = {
                        expandedArtistMenu = !expandedArtistMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedArtistAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { artistExpanded = !artistExpanded },
                        icon = if (expandedArtistMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedArtistMenu,
                        onDismissRequest = { expandedArtistMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        artistActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedArtistAction = item
                                    artistSelectListFiltering = artistActions.indexOf(item)
                                    expandedArtistMenu = false
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
                    text = stringResource(id = R.string.playlist_filter_genre_list_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_filter_genre_list_sub_title),
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var genreExpanded by remember { mutableStateOf(false) }
                val genreActions = arrayOf(
                    stringResource(id = R.string.playlist_filter_genre_exact_only),
                    stringResource(id = R.string.playlist_filter_genre_included)
                )
                var expandedGenreMenu by remember { mutableStateOf(false) }
                var selectedGenreAction = if (genreSelectListFilteringExact) {
                    genreActions[0]
                } else {
                    genreActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = genreExpanded,
                    onExpandedChange = {
                        expandedGenreMenu = !expandedGenreMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedGenreAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { genreExpanded = !genreExpanded },
                        icon = if (expandedGenreMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGenreMenu,
                        onDismissRequest = { expandedGenreMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        genreActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedGenreAction = item
                                    genreSelectListFilteringExact = genreActions.indexOf(item) == 0
                                    expandedGenreMenu = false
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
                    text = stringResource(id = R.string.playlist_filter_label_list_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_filter_label_list_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var labelExpanded by remember { mutableStateOf(false) }
                val labelActions = arrayOf(
                    stringResource(id = R.string.playlist_filter_label_exact_only),
                    stringResource(id = R.string.playlist_filter_label_included)
                )
                var expandedLabelMenu by remember { mutableStateOf(false) }
                var selectedLabelAction = if (labelSelectListFilteringExact) {
                    labelActions[0]
                } else {
                    labelActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = labelExpanded,
                    onExpandedChange = {
                        expandedLabelMenu = !expandedLabelMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedLabelAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { labelExpanded = !labelExpanded },
                        icon = if (expandedLabelMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLabelMenu,
                        onDismissRequest = { expandedLabelMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        labelActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedLabelAction = item
                                    labelSelectListFilteringExact = labelActions.indexOf(item) == 0
                                    expandedLabelMenu = false
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
                    text = stringResource(id = R.string.playlist_filter_style_list_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_filter_style_list_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var styleExpanded by remember { mutableStateOf(false) }
                val styleActions = arrayOf(
                    stringResource(id = R.string.playlist_filter_style_exact_only),
                    stringResource(id = R.string.playlist_filter_style_included)
                )
                var expandedStyleMenu by remember { mutableStateOf(false) }
                var selectedStyleAction = if (styleSelectListFilteringExact) {
                    styleActions[0]
                } else {
                    styleActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = styleExpanded,
                    onExpandedChange = {
                        expandedStyleMenu = !expandedStyleMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedStyleAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { styleExpanded = !styleExpanded },
                        icon = if (expandedStyleMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStyleMenu,
                        onDismissRequest = { expandedStyleMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        styleActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedStyleAction = item
                                    styleSelectListFilteringExact = styleActions.indexOf(item) == 0
                                    expandedStyleMenu = false
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
            text = stringResource(id = R.string.playlist_filter_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                playlistSettingsViewModel.updateFilter(
                    artistSelectListFiltering,
                    genreSelectListFilteringExact,
                    labelSelectListFilteringExact,
                    styleSelectListFilteringExact
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Filter Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun ShowOrder(playlistSettingsViewModel: PlaylistSettingsViewModel = viewModel()) {
    val orderUiState : PlaylistSettingsViewModel.ViewState by playlistSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountOrders <= 1) {
        loadingCountOrders ++
        defaultPlaylistSortingMethod = orderUiState.defaultPlaylistSortingMethod
        typeSelectListSortingMethod = orderUiState.typeSelectListSortingMethod
        externalPlaylistFormatMethod = orderUiState.externalPlaylistFormatMethod
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
                    text = stringResource(id = R.string.playlist_order_default_order_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_order_default_order_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var defaultExpanded by remember { mutableStateOf(false) }
                val defaultActions = arrayOf(
                    stringResource(id = R.string.playlist_order_album_track_title),
                    stringResource(id = R.string.playlist_order_album_track_number),
                    stringResource(id = R.string.playlist_order_album_added_track_title),
                    stringResource(id = R.string.playlist_order_album_added_track_number)
                )
                var expandedDefaultMenu by remember { mutableStateOf(false) }
                var selectedDefaultAction = defaultActions[defaultPlaylistSortingMethod]
                ExposedDropdownMenuBox(
                    expanded = defaultExpanded,
                    onExpandedChange = {
                        expandedDefaultMenu = !expandedDefaultMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedDefaultAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { defaultExpanded = !defaultExpanded },
                        icon = if (expandedDefaultMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDefaultMenu,
                        onDismissRequest = { expandedDefaultMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        defaultActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedDefaultAction = item
                                    defaultPlaylistSortingMethod = defaultActions.indexOf(item)
                                    expandedDefaultMenu = false
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
                    text = stringResource(id = R.string.playlist_order_type_list_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_order_type_list_sub_title),
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var typeExpanded by remember { mutableStateOf(false) }
                val typeActions = arrayOf(
                    stringResource(id = R.string.playlist_order_type_excluding_article),
                    stringResource(id = R.string.playlist_order_type_including_article)
                )
                var expandedTypeMenu by remember { mutableStateOf(false) }
                var selectedTypeAction = if (typeSelectListSortingMethod) {
                    typeActions[0]
                } else {
                    typeActions[1]
                }
                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = {
                        expandedTypeMenu = !expandedTypeMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTypeAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { typeExpanded = !typeExpanded },
                        icon = if (expandedTypeMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTypeMenu,
                        onDismissRequest = { expandedTypeMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        typeActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTypeAction = item
                                    typeSelectListSortingMethod = typeActions.indexOf(item) == 0
                                    expandedTypeMenu = false
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
                    text = stringResource(id = R.string.playlist_order_external_format_title),
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.playlist_order_external_format_sub_title),
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var createExpanded by remember { mutableStateOf(false) }
                val createActions = arrayOf(
                    stringResource(id = R.string.playlist_order_create_sort),
                    stringResource(id = R.string.playlist_order_create_m3u),
                    stringResource(id = R.string.playlist_order_create_wpl),
                    stringResource(id = R.string.playlist_order_create_zpl)
                )
                var expandedCreateMenu by remember { mutableStateOf(false) }
                var selectedCreateAction = createActions[externalPlaylistFormatMethod]
                ExposedDropdownMenuBox(
                    expanded = createExpanded,
                    onExpandedChange = {
                        expandedCreateMenu = !expandedCreateMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedCreateAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { createExpanded = !createExpanded },
                        icon = if (expandedCreateMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCreateMenu,
                        onDismissRequest = { expandedCreateMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        createActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedCreateAction = item
                                    externalPlaylistFormatMethod = createActions.indexOf(item)
                                    expandedCreateMenu = false
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
            text = stringResource(id = R.string.playlist_order_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                playlistSettingsViewModel.updateOrder(
                    defaultPlaylistSortingMethod,
                    typeSelectListSortingMethod,
                    externalPlaylistFormatMethod
                )
                Toast.makeText(
                    GlobalApplication.appContext,
                    "Order Settings Updated"
                    , Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun ShowPlayers(playlistSettingsViewModel: PlaylistSettingsViewModel = viewModel()) {
    val playersUiState : PlaylistSettingsViewModel.ViewState by playlistSettingsViewModel.viewState.collectAsStateWithLifecycle()
    if (loadingCountPlayers <= 1) {
        loadingCountPlayers ++
        atTypePlaylistEndAction = playersUiState.atTypePlaylistEndAction
        atTrackPlaylistEndAction = playersUiState.atTrackPlaylistEndAction
        atPlaylistStartAction = playersUiState.atPlaylistStartAction
        playlistConfigAction = playersUiState.playlistConfigAction
    }
    Column(
        modifier = Modifier.fillMaxSize(),
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
                    text = "Type Playlist Player",
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "At the end of an type playlist",
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var typeExpanded by remember { mutableStateOf(false) }
                val typeActions = arrayOf(
                    "Loop back to the first Type",
                    "Exit the player"
                )
                var expandedTypeMenu by remember { mutableStateOf(false) }
                var selectedTypeAction = typeActions[atTypePlaylistEndAction]
                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = {
                        expandedTypeMenu = !expandedTypeMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTypeAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { typeExpanded = !typeExpanded },
                        icon = if (expandedTypeMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTypeMenu,
                        onDismissRequest = { expandedTypeMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        typeActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTypeAction = item
                                    atTypePlaylistEndAction = typeActions.indexOf(item)
                                    expandedTypeMenu = false
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
                    text = "Track Playlist Player",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "At the end of a track playlist",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var trackExpanded by remember { mutableStateOf(false) }
                val trackActions = arrayOf(
                    "Loop back to the first Track",
                    "Exit the player"
                )
                var expandedTrackMenu by remember { mutableStateOf(false) }
                var selectedTrackAction = trackActions[atTrackPlaylistEndAction]
                ExposedDropdownMenuBox(
                    expanded = trackExpanded,
                    onExpandedChange = {
                        expandedTrackMenu = !expandedTrackMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedTrackAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { trackExpanded = !trackExpanded },
                        icon = if (expandedTrackMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTrackMenu,
                        onDismissRequest = { expandedTrackMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        trackActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedTrackAction = item
                                    atTrackPlaylistEndAction = trackActions.indexOf(item)
                                    expandedTrackMenu = false
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
                    text = "Playlist Player",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "When a playlist starts play from",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var playerExpanded by remember { mutableStateOf(false) }
                val playerActions = arrayOf(
                    "The beginning of the playlist",
                    "The start of the track on exit",
                    "The track position on exit"
                )
                var expandedPlayerMenu by remember { mutableStateOf(false) }
                var selectedPlayerAction = playerActions[atPlaylistStartAction]
                ExposedDropdownMenuBox(
                    expanded = playerExpanded,
                    onExpandedChange = {
                        expandedPlayerMenu = !expandedPlayerMenu
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedPlayerAction,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { playerExpanded = !playerExpanded },
                        icon = if (expandedPlayerMenu)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedPlayerMenu,
                        onDismissRequest = { expandedPlayerMenu = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        playerActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedPlayerAction = item
                                    atPlaylistStartAction = playerActions.indexOf(item)
                                    expandedPlayerMenu = false
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
                    text = "Playlist Configuration File",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "When playing a playlist",
                    fontFamily = appFont,
                    modifier = Modifier,
                    color = colorPrimaryBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                var mExpanded by remember { mutableStateOf(false) }
                val configActions = arrayOf(
                    "Always create a new file",
                    "Create when playlist changed"
                )
                var expanded by remember { mutableStateOf(false) }
                var selectedText = configActions[playlistConfigAction]
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    cbgDropDownButton(
                        text = selectedText,
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        ),
                        fontSize = 14,
                        fontColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { mExpanded = !mExpanded },
                        icon = if (expanded)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        Modifier.background(MaterialTheme.colorScheme.primary)
                    ) {
                        configActions.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontFamily = appFont,
                                        fontSize = 14.sp
                                    )
                                },
                                onClick = {
                                    selectedText = item
                                    playlistConfigAction = configActions.indexOf(item)
                                    expanded = false
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
            text = stringResource(id = R.string.playlist_players_settings_apply),
            fontFamily = appFont,
            buttonWidth = 1f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Green,
            onClick = {
                playlistSettingsViewModel.updatePlayers(
                    atTypePlaylistEndAction,
                    atTrackPlaylistEndAction,
                    atPlaylistStartAction,
                    playlistConfigAction
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

class PlaylistSettingsCompanion {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        // SetUp
        var loadingCountSetup = 0
        var playlistItemAdditionMethod = 0
        var playlistArtistExact = true
        var showAttachmentDialog = false
        var shareOnExternalFileCreate = false
        // Filter
        var loadingCountFilters = 0
        var artistSelectListFiltering = 0
        var genreSelectListFilteringExact = false
        var labelSelectListFilteringExact = false
        var styleSelectListFilteringExact = false
        // Order
        var loadingCountOrders = 0
        var defaultPlaylistSortingMethod = 0
        var typeSelectListSortingMethod = false
        var externalPlaylistFormatMethod = 0
        // Player
        var loadingCountPlayers = 0
        var atTypePlaylistEndAction = 0
        var atTrackPlaylistEndAction = 0
        var atPlaylistStartAction = 0
        var playlistConfigAction = 0
    }
}