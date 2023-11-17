package com.codebygus.cbgcollection

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("unused")
class GlobalApplication : Application() {
    private val appCoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    override fun onCreate() {
        super.onCreate()
        // Set the application context
        appContext = this
        // AppTheme
/*
        appCoroutineScope.launch {
            this@GlobalApplication.appThemeParamsDataStore.updateData { params: AppThemeParams ->
                params.copy {
                   dynamicColors = true
                   customTheme = "Blue"
                }
            }
        }
*/
        // Playlist Settings

    }
/*
// No need to cancel this scope as it'll be torn down with the process
        applicationScope = CoroutineScope(SupervisorJob())
        // Create the foreground service channel
        createServiceNotificationChannel()
        if (!BuildConfig.DEBUG) return

        val oldHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t: Thread, e: Throwable ->
            try {
                val sw = StringWriter()
                e.printStackTrace(PrintWriter(sw))
                val intent = Intent(Intent.ACTION_SEND)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra(Intent.EXTRA_TEXT, sw.toString())
                intent.type = "text/plain"
                startActivity(intent)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                if (oldHandler != null) oldHandler.uncaughtException(t, e) else exitProcess(1)
            }
        }
        Thread.setDefaultUncaughtExceptionHandler { _, ex ->
            Log.e("unhandledException", "onCreate: ${ex.printStackTrace()}")
            Log.e("unhandledException", "onCreate: ${ex.message}")
            errorClass = ex.stackTrace[0].fileName
            errorFunction = ex.stackTrace[0].methodName
            errorMessage = ex.message.toString()
            errorReason = unhandledException
            logError()
        }
    }
    companion object {

        const val SERVICE_CHANNEL_ID = "foregroundServiceChannel"
        private const val SERVICE_CHANNEL_NAME = "Foreground Service Channel"
        private lateinit var foregroundServiceChannel: NotificationChannel
        private lateinit var foregroundNotificationManager: NotificationManager
        // Errors
        private var errorClass = ""
        private var errorFunction = ""
        private var errorObject = ""
        private var errorMessage = ""
        private var errorReason = ""
        private var errorFilePath = ""

        lateinit var appContext: Context
        lateinit var applicationScope: CoroutineScope
            private set

        fun enableStrictMode() {
            if (BuildConfig.DEBUG) StrictMode.enableDefaults()
        }

        fun logError() {
            val errorDate = getReadableDate(Date())
            ErrorLogger.createErrorRecord(
                ModelLogEntryError(
                    errorDate,
                    errorClass,
                    errorFunction,
                    errorMessage,
                    errorReason
                )
            )
        }

        fun createServiceNotificationChannel() {
            foregroundServiceChannel = NotificationChannel(
                SERVICE_CHANNEL_ID,
                SERVICE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            foregroundServiceChannel.setShowBadge(false)
            foregroundServiceChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            foregroundNotificationManager = getSystemService(appContext, NotificationManager::class.java)!!
            foregroundNotificationManager.createNotificationChannel(
                foregroundServiceChannel
            )

        }
    }
*/
    companion object {
    lateinit var appContext: Context
    }
}