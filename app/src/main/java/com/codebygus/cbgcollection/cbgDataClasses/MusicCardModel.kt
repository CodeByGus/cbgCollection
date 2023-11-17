package com.codebygus.cbgcollection.cbgDataClasses

import android.graphics.Bitmap
import android.net.Uri

data class MusicCardModel(
    val contentUri: Uri,
    val songId: Long,
    val cover: Bitmap?,
    val songTitle: String,
    val artist: String,
    val duration: String
)