package com.codebygus.cbgcollection.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.codebygus.cbgcollection.proto.PlaylistSettingsParams
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object PlaylistSettingsParamsSerializer : Serializer<PlaylistSettingsParams> {
    override val defaultValue: PlaylistSettingsParams = PlaylistSettingsParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PlaylistSettingsParams = withContext(Dispatchers.IO) {
        try {
            return@withContext PlaylistSettingsParams.parseFrom(input)
        } catch(exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read playlist settings proto.", exception)
        }
    }

    override suspend fun writeTo(t: PlaylistSettingsParams, output: OutputStream) = withContext(Dispatchers.IO) {
        t.writeTo(output)
    }
}

val Context.playlistSettingsParamsDataStore: DataStore<PlaylistSettingsParams> by dataStore(
    fileName = "playlist_settings_params.pb",
    serializer = PlaylistSettingsParamsSerializer
)