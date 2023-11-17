package com.codebygus.cbgcollection.cbgRoot

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codebygus.cbgcollection.cbgReusables.CbgGradientButton
import com.codebygus.cbgcollection.cbgViewModels.MusicListViewModel
import com.codebygus.cbgcollection.ui.theme.Black
import com.codebygus.cbgcollection.ui.theme.appFont
import com.codebygus.cbgcollection.ui.theme.colorPrimaryDarkWhite
import com.codebygus.cbgcollection.ui.theme.colorPrimaryWhite
import java.io.File

@Composable
fun SelectRootScreen(
    navController: NavController,
    name: String?,
    viewModel: MusicListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val applicationContext = LocalContext.current
    var validFolder = false
    val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data!!
/*
            applicationContext.contentResolver
                .takePersistableUriPermission(
                    intent.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
*/
            val uriTree: Uri = intent.data!!
            val folderUri = uriTree.lastPathSegment
            val split = folderUri!!.split(":".toRegex()).toTypedArray()
            val type = split[0]
            val folderPath = type + "/" + split[1]
            Log.d("TAG", "SelectRootScreen(folderPath): $folderPath")
            validFolder = checkRootFolder(applicationContext, folderPath)
        }
    }
    val musicCards = viewModel.musicCards
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.initializeListIfNeeded(context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        CbgGradientButton(
            text = "Select Root Folder",
            fontFamily = appFont,
            buttonWidth = .8f,
            colorGradient = listOf(colorPrimaryDarkWhite, colorPrimaryWhite, colorPrimaryDarkWhite),
            fontSize = 14,
            fontColor = Black,
            onClick = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                folderPickerLauncher.launch(intent)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }

}
fun checkRootFolder(applicationContext: Context, folderPath: String) : Boolean {
    val rootFolder = "/storage/$folderPath"
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val pathSep = File.separatorChar
    val posParent = rootFolder.lastIndexOf(pathSep)
    var parentPath = rootFolder.substring(posParent + 1)
    parentPath = parentPath.replace("'", "''")
    val selectArg1 = "'%$parentPath%'"
    val selectArg2 = MediaStore.Audio.Media.IS_MUSIC + "!=0"
    val selectionArg = " LIKE $selectArg1 AND $selectArg2"
    val selection: String = MediaStore.Audio.Media.RELATIVE_PATH + selectionArg
    val rs = applicationContext.contentResolver.query(
        uri,
        null,
        selection,
        null,
        null
    )
    if (rs != null && rs.count > 0) {
        return true
    }
    rs?.close()
    return false
}