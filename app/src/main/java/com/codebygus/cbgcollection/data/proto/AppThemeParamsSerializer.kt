package com.codebygus.cbgcollection.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.codebygus.cbgcollection.proto.AppThemeParams
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object AppThemeParamsSerializer : Serializer<AppThemeParams> {
    override val defaultValue: AppThemeParams = AppThemeParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppThemeParams = withContext(Dispatchers.IO) {
        try {
            return@withContext AppThemeParams.parseFrom(input)
        } catch(exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read appTheme proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppThemeParams, output: OutputStream) = withContext(Dispatchers.IO) {
        t.writeTo(output)
    }
}

val Context.appThemeParamsDataStore: DataStore<AppThemeParams> by dataStore(
    fileName = "app_theme_params.pb",
    serializer = AppThemeParamsSerializer
)