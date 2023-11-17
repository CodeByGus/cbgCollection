package com.codebygus.cbgcollection.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.codebygus.cbgcollection.proto.FunctionSettingsParams
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object FunctionSettingsParamsSerializer : Serializer<FunctionSettingsParams> {
    override val defaultValue: FunctionSettingsParams = FunctionSettingsParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FunctionSettingsParams = withContext(Dispatchers.IO) {
        try {
            return@withContext FunctionSettingsParams.parseFrom(input)
        } catch(exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read function settings proto.", exception)
        }
    }

    override suspend fun writeTo(t: FunctionSettingsParams, output: OutputStream) = withContext(Dispatchers.IO) {
        t.writeTo(output)
    }
}

val Context.functionSettingsParamsDataStore: DataStore<FunctionSettingsParams> by dataStore(
    fileName = "function_settings_params.pb",
    serializer = FunctionSettingsParamsSerializer
)