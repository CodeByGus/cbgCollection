package com.codebygus.cbgcollection.cbgCore

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codebygus.cbgcollection.R
import com.codebygus.cbgcollection.cbgDataClasses.NavigationItem
import com.codebygus.cbgcollection.cbgNavigation.Screen
import com.codebygus.cbgcollection.cbgReusables.CbgGradientButton
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgAppearanceSettings
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgChangeMusicFolder
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgExportData
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgFunctionSettings
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgGenerateData
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgImportData
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgPlaylistSettings
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.ArgSyncCollection
import com.codebygus.cbgcollection.cbgViewModels.AppThemeViewModel
import com.codebygus.cbgcollection.ui.theme.AppTheme
import com.codebygus.cbgcollection.ui.theme.Red
import com.codebygus.cbgcollection.ui.theme.appFont
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDarkWhite
import com.codebygus.cbgcollection.ui.theme.colorPrimaryWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, appBarTitle: String, appThemeViewModel: AppThemeViewModel = viewModel()) {

    // Items for the left navigation drawer
    val leftItems = listOf(
        NavigationItem(
            title = stringResource(id = R.string.nav_change_music_folder),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_change_folder),
            usesDivider = false
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_sync_collection_database),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_sync_collection),
            usesDivider = true
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_import_data),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_data_import),
            usesDivider = false
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_export_data),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_data_export),
            usesDivider = false
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_generate_data),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_data_generate),
            usesDivider = true
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_appearance_settings),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_appearance_settings),
            usesDivider = false
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_function_settings),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_function_settings),
            usesDivider = false
        ),
        NavigationItem(
            title = stringResource(id = R.string.nav_playlist_settings),
            icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_playlist_settings),
            usesDivider = false
        )
    )
    // Items for the right navigation drawer
    /*
        val rightItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.nav_create_artist_playlist),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_artist),
                usesDivider = false
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_create_genre_playlist),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_genre),
                badgeCount = 45,
                usesDivider = false
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_create_label_playlist),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_label),
                usesDivider = false
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_create_style_playlist),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_style),
                usesDivider = true
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_artist_associations),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_associated_artists),
                usesDivider = true
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_active_playlists),
                icon = ImageBitmap.imageResource(id = R.drawable.ic_nav_playlist_active),
                usesDivider = false
            )
        )
    */
    val themeUiState: AppThemeViewModel.ViewState by appThemeViewModel.viewState.collectAsStateWithLifecycle()
    var dynamicTheme by remember { mutableStateOf(true) }
    var colorString by remember { mutableStateOf("GreenWhite") }
    AppTheme(
        dynamicColor = themeUiState.dynamicColors,
        customTheme = themeUiState.customTheme
    ) {
        val drawerStateLeft = rememberDrawerState(initialValue = DrawerValue.Closed)
        val drawerScope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        /// Start double drawer....
        // Replace this with the double drawer layout?
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.requiredWidth(320.dp),
                    drawerContainerColor = MaterialTheme.colorScheme.primary,
                    drawerContentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Header",
                            fontFamily = appFont,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    leftItems.forEachIndexed { index, navigationItem ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = navigationItem.title,
                                    fontFamily = appFont,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                drawerScope.launch {
                                    drawerStateLeft.close()
                                }
                                navController.navigate(
                                    when (index) {
                                        0 -> Screen.PlaylistSettings.withArgs(ArgChangeMusicFolder)
                                        1 -> Screen.PlaylistSettings.withArgs(ArgSyncCollection)
                                        2 -> Screen.PlaylistSettings.withArgs(ArgImportData)
                                        3 -> Screen.PlaylistSettings.withArgs(ArgExportData)
                                        4 -> Screen.PlaylistSettings.withArgs(ArgGenerateData)
                                        5 -> Screen.AppearanceSettings.withArgs(ArgAppearanceSettings)
                                        6 -> Screen.FunctionSettings.withArgs(ArgFunctionSettings)
                                        7 -> Screen.PlaylistSettings.withArgs(ArgPlaylistSettings)
                                        else -> throw AssertionError()
                                    }
                                )
                            },
                            icon = {
                                Icon(
                                    bitmap = navigationItem.icon,
                                    contentDescription = navigationItem.title
                                )
                            },
                            badge = {
                                navigationItem.badgeCount?.let {
                                    Text(
                                        text = navigationItem.badgeCount.toString(),
                                        fontFamily = appFont,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            modifier = Modifier.padding(vertical = 2.dp),
                            shape = RectangleShape,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                unselectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedBadgeColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedBadgeColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        )
                        if (navigationItem.usesDivider) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            },
            drawerState = drawerStateLeft
        )
        // End double drawer....
        // Rets of the base screen content
        {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            var displayMenu by remember { mutableStateOf(false) }
            var isClicked by remember { mutableStateOf(false) }
            var isDrawerOn by remember { mutableStateOf(false) }
            val context = LocalContext.current
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                                    drawerScope.launch {
                                        drawerStateLeft.open()
                                    }
                                }
                            ) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.ic_menu_drawer),
                                    contentDescription = stringResource(id = R.string.navigation_drawer_desc)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                onClick = {
                                    displayMenu = !displayMenu
                                }
                            ) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.ic_menu_palette),
                                    contentDescription = "Palette"
                                )
                            }
                            DropdownMenu(expanded = displayMenu, onDismissRequest = { displayMenu = false }) {
                                DropdownMenuItem(onClick = {
                                    isClicked = true
                                    dynamicTheme = true
                                    colorString = "Dynamic"
                                    Toast.makeText(context, "Dynamic selected", Toast.LENGTH_LONG).show()
                                }) {
                                    Text(text = "Dynamic")
                                }
                                DropdownMenuItem(onClick = {
                                    isClicked = true
                                    dynamicTheme = false
                                    colorString = "BlueWhite"
                                    Toast.makeText(context, "BlueWhite selected", Toast.LENGTH_LONG).show()
                                }) {
                                    Text(text = "BlueWhite")
                                }
                                DropdownMenuItem(onClick = {
                                    isClicked = true
                                    dynamicTheme = false
                                    colorString = "DenimDarkWash"
                                    Toast.makeText(context, "Denim DarkWash selected", Toast.LENGTH_LONG).show()
                                }) {
                                    Text(text = "Denim DarkWash")
                                }
                                DropdownMenuItem(onClick = {
                                    isClicked = true
                                    dynamicTheme = false
                                    colorString = "GreenWhite"
                                    Toast.makeText(context, "GreenWhite selected", Toast.LENGTH_LONG).show()
                                }) {
                                    Text(text = "GreenWhite")
                                }
                                DropdownMenuItem(onClick = {
                                    isClicked = true
                                    dynamicTheme = false
                                    colorString = "OrangeWhite"
                                    Toast.makeText(context, "OrangeWhite selected", Toast.LENGTH_LONG).show()
                                }) {
                                    Text(text = "OrangeWhite")
                                }
                            }
                            if (isClicked) {
                                appThemeViewModel.updateTheme(dynamicTheme, colorString)
                                displayMenu = false
                                AppTheme(
                                    dynamicColor = dynamicTheme,
                                    customTheme = colorString,
                                ) {}
                            }
                            IconButton(
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                onClick = {
                                    isDrawerOn = true
                                }
                            ) {
                                Icon(
                                    bitmap = ImageBitmap.imageResource(R.drawable.ic_menu_drawer),
                                    contentDescription = "Right Drawer"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    if (isDrawerOn) {
                        isDrawerOn = false
                        DoubleDrawerLayout(

                        ){

                        }
/*
                        drawerScope.launch {
                            drawerStateLeft.open()
                        }
*/
                    }
                }
            ) { values ->
                val topPadding = values.calculateTopPadding()
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 50.dp, top = topPadding)
                    ) {
                        items(100) {
                            Text(
                                text = "Item #$it",
                                fontFamily = appFont,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    val activity = LocalContext.current as Activity
                    CbgGradientButton(
                        text = stringResource(id = R.string.app_close),
                        fontFamily = appFont,
                        buttonWidth = 1f,
                        colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
                        fontSize = 14,
                        fontColor = Red,
                        onClick = {
                            activity.finishAndRemoveTask()
                        },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

class DoubleDrawerState(){
    var scope = CoroutineScope(Dispatchers.Main)
    var maxWidth = 0f
    var leftOffsetX = Animatable(-5000f)/*initial value is to prevent the drawer to show during startup*/
    var leftDrawerWidthPx = 0f
    var leftThreshold = 0f
    var isLeftOpen = false
    var leftDrawerEnabled = true

    var rightDrawerWidthPx = 0f
    var rightOffsetX = Animatable(5000f)
    var rightThreshold = 0f
    var isRightOpen = false
    var rightDrawerEnabled = true

    var fiftyPercentL = 0f
    var fiftyPercentR = 0f

    private var isLeftDragging = false
    private var isRightDragging = false
    private var velocity = 0f
    private val velocityThreshold = 25f

    internal fun onDrag(delta: Float){
        if ((!leftOffsetX.isRunning) && (!rightOffsetX.isRunning)) {
            velocity = delta
            when {
                delta > 0 -> {/* left drawer */
                    when {
                        isRightDragging -> {
                            onDragRight(delta)
                        }
                        else -> {
                            if(!isRightOpen){ isLeftDragging = true }/*if right drawer is not open then capture dragging*/
                            if (isRightOpen) onDragRight(delta) else onDragLeft(delta)
                        }
                    }
                }
                delta < 0 -> {/* right drawer */
                    when {
                        isLeftDragging -> {
                            onDragLeft(delta)
                        }
                        else -> {
                            if(!isLeftOpen){ isRightDragging = true }/*if left drawer is not open then capture dragging*/
                            if (isLeftOpen) onDragLeft(delta) else onDragRight(delta)
                        }
                    }
                }
            }
        }
    }

    internal fun onDragEnd(){
        if(abs(velocity) > velocityThreshold){
            performFling(velocity)
            return
        } else{
            if (isRightOpen) {
                when {
                    rightOffsetX.value > (rightThreshold + fiftyPercentR) -> hideRight()
                    else -> showRight()
                }
            } else {
                when {
                    rightOffsetX.value > (maxWidth - fiftyPercentR) -> hideRight()
                    rightOffsetX.value < (maxWidth - fiftyPercentR) -> showRight()
                }
            }

            if (isLeftOpen) {
                /*hide if 20% swipe in*/
                when {
                    abs(leftOffsetX.value) > (abs(leftThreshold) + fiftyPercentL) -> hideLeft()
                    else -> showLeft()
                }
            } else {
                /*show if 20% swipe out*/
                when {
                    abs(leftOffsetX.value) < (maxWidth - fiftyPercentL) -> showLeft()
                    else -> hideLeft()
                }
            }
        }

        isLeftDragging = false
        isRightDragging = false
        velocity = 0f
    }

    private fun performFling(velocity: Float){
        if(velocity > velocityThreshold){
            if(isRightOpen) hideRight() else showLeft()
        }
        if(velocity < velocityThreshold){
            if(isLeftOpen) hideLeft() else showRight()
        }
    }

    private fun onDragLeft(delta: Float){
        if(!leftDrawerEnabled){ return }
        when {
            (leftOffsetX.value + delta) > leftThreshold -> {
                scope.launch { leftOffsetX.snapTo(leftThreshold) }
                isLeftOpen = true
            }
            (leftOffsetX.value + delta) < -maxWidth -> {
                scope.launch { leftOffsetX.snapTo(-maxWidth) }
                isLeftOpen = false
            }
            else -> {
                scope.launch { leftOffsetX.snapTo(leftOffsetX.value + delta) }
            }
        }
    }

    private fun onDragRight(delta: Float){
        if(!rightDrawerEnabled){ return }
        when {
            (rightOffsetX.value + delta) <= rightThreshold -> {
                scope.launch { rightOffsetX.snapTo(rightThreshold) }
                isRightOpen = true
            }
            (rightOffsetX.value + delta) > maxWidth -> {
                scope.launch { rightOffsetX.snapTo(maxWidth) }
                isRightOpen = false
            }
            else -> {
                scope.launch { rightOffsetX.snapTo(rightOffsetX.value + delta) }
            }
        }
    }

    fun showLeft(){
        if(!leftDrawerEnabled){ return }
        scope.launch {
            rightOffsetX.snapTo(maxWidth)/*hide right first*/
            leftOffsetX.animateTo(leftThreshold)/*then show left*/
        }
        if(!leftOffsetX.isRunning){ isLeftOpen = true }
    }
    fun hideLeft(){
        scope.launch {
            leftOffsetX.animateTo(-maxWidth)
        }
        if(!leftOffsetX.isRunning){ isLeftOpen = false }
    }
    fun showRight(){
        if(!rightDrawerEnabled){ return }
        scope.launch {
            leftOffsetX.snapTo(-maxWidth)/*hide left first*/
            rightOffsetX.animateTo(rightThreshold)/*then show right*/
        }
        if(!rightOffsetX.isRunning){ isRightOpen = true }
    }
    fun hideRight(){
        scope.launch {
            rightOffsetX.animateTo(maxWidth)
        }
        if(!rightOffsetX.isRunning){ isRightOpen = false }
    }

    internal fun onConfigurationChange(){
        Log.d("debugTAG", "onConfigurationChange leftThreshold: $leftThreshold")
        if(isLeftOpen){
            scope.launch {leftOffsetX.snapTo(leftThreshold)}
        } else{
            scope.launch {leftOffsetX.snapTo(-maxWidth)}
        }
        if(isRightOpen){
            scope.launch { rightOffsetX.snapTo(rightThreshold) }
        } else{
            scope.launch { rightOffsetX.snapTo(maxWidth) }
        }
    }

    companion object{
        val OffsetSaver = listSaver<Animatable<Float, AnimationVector1D>, Any>(
            save = { listOf(it.value)},
            restore = { Animatable(it[0] as Float, Float.VectorConverter, Spring.DefaultDisplacementThreshold) }
        )
    }
}

@Composable
fun DoubleDrawerLayout(
    state: DoubleDrawerState = remember{ DoubleDrawerState() },
    leftDrawerWidth: Dp = 300.dp,
    leftDrawerContent: @Composable BoxScope.() -> Unit = { LeftDrawerTestContent { state.hideLeft() } },
    leftDrawerEnabled: Boolean = true,
    rightDrawerWidth: Dp = 250.dp,
    rightDrawerContent: @Composable BoxScope.() -> Unit = { RightDrawerTestContent { state.hideRight() } },
    rightDrawerEnabled: Boolean = true,
    body: @Composable BoxScope.() -> Unit = { TestBody() }
) {
    val scope = rememberCoroutineScope()
    state.scope = scope

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val constraintScope = this
        val density = LocalDensity.current
        state.maxWidth = with(density){ constraintScope.maxWidth.toPx()}
        LaunchedEffect(Unit) {
            state.leftOffsetX.snapTo(-state.maxWidth)
            state.rightOffsetX.snapTo(state.maxWidth)
        }

        state.leftDrawerWidthPx = with(density){ (leftDrawerWidth).toPx()}
        state.leftThreshold = -(state.maxWidth - state.leftDrawerWidthPx)
        state.leftDrawerEnabled = leftDrawerEnabled

        state.rightDrawerWidthPx = with(density){ (rightDrawerWidth).toPx()}
        state.rightThreshold = (state.maxWidth - state.rightDrawerWidthPx)
        state.rightDrawerEnabled = rightDrawerEnabled

        state.fiftyPercentL = (state.leftDrawerWidthPx * 50) / 100
        state.fiftyPercentR = (state.rightDrawerWidthPx * 50) / 100

        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            change.consume()
                            state.onDrag(dragAmount.x)
                        },
                        onDragEnd = {
                            state.onDragEnd()
                        }
                    )
                }
        ){
           TestBody()
///            MemberListScreen()(vm = hiltViewModel(), {}, { photoId, personId ->  })
            //body()

            LeftDrawerBody(
                leftOffsetX = state.leftOffsetX.value,
                leftThreshold = state.leftThreshold,
                leftDrawerWidth = leftDrawerWidth,
                onHideRequest = {state.hideLeft()},
                content = leftDrawerContent
            )

            RightDrawerBody(
                rightOffsetX = state.rightOffsetX.value,
                rightThreshold = state.rightThreshold,
                rightDrawerWidth = rightDrawerWidth,
                onHideRequest = {state.hideRight()},
                content = rightDrawerContent
            )
        }
    }
}

@Composable
private fun LeftDrawerBody(
    leftOffsetX: Float,
    leftThreshold: Float,
    leftDrawerWidth: Dp,
    onHideRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Scrim(
        open = leftOffsetX >= leftThreshold,
        onClose = { onHideRequest() },
        opacity = {0f},
        color = Color.Transparent//DrawerDefaults.scrimColor
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(x = leftOffsetX.roundToInt(), y = 0) }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(leftDrawerWidth)
                    .border(width = 5.dp, color = Color.Red)
                    .align(Alignment.TopEnd)
            ){
                content()
            }
        }
    }
}

@Composable
private fun RightDrawerBody(
    rightOffsetX: Float,
    rightThreshold: Float,
    rightDrawerWidth: Dp,
    onHideRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Scrim(
        open = rightOffsetX == rightThreshold,
        onClose = { onHideRequest() },
        opacity = {0f},
        color = Color.Transparent//DrawerDefaults.scrimColor
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(x = rightOffsetX.roundToInt(), y = 0) }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(rightDrawerWidth)
                    .border(width = 5.dp, color = Color.Green)
                    .align(Alignment.TopStart)
            ){
                content()
            }
        }
    }
}

@Composable
private fun TestBody() {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        for(i in 1..50){
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedButton(
                        onClick = {},
                        content = { Text(text = "Double!") },
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "#$i. Double Drawer Layout...",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)

                    )
                    OutlinedButton(
                        onClick = {},
                        content = { Text(text = "Click Me!") },
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Divider()
            }
        }
    }
}

@Composable
private fun BoxScope.LeftDrawerTestContent(onHideRequest: () -> Unit) {
    Button(
        onClick = { onHideRequest() },
        content = { Text(text = "#Click to hide!") },
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
private fun BoxScope.RightDrawerTestContent(onHideRequest: () -> Unit) {
    Button(
        onClick = { onHideRequest() },
        content = { Text(text = "Click to hide!") },
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    opacity: () -> Float,
    color: Color
) {
    val closeDrawer = "getString(Strings.CloseDrawer)"
    val dismissDrawer = if (open) {
        Modifier
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            .semantics(mergeDescendants = true) {
                contentDescription = closeDrawer
                onClick { onClose(); true }
            }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissDrawer)
    ) {
        drawRect(color, alpha = opacity())
    }
}