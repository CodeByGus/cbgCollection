package com.codebygus.cbgcollection.cbgViewModels

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codebygus.cbgcollection.data.proto.appThemeParamsDataStore
import com.codebygus.cbgcollection.proto.AppThemeParams
import com.codebygus.cbgcollection.proto.copy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppThemeViewModel(application: Application) : AndroidViewModel(application) {
    data class ViewState(
        val dynamicColors: Boolean = true,
        val customTheme: String = ""
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var appThemeParamsCollectorJob: Job? = null

    private val appThemeParamsDataStore: DataStore<AppThemeParams>
        get() = getApplication<Application>().applicationContext.appThemeParamsDataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            appThemeParamsDataStore.data.collectLatest { themeParams: AppThemeParams ->
                _viewState.update { currentState ->
                    currentState.copy(
                        dynamicColors = themeParams.dynamicColors,
                        customTheme = themeParams.customTheme
                    )
                }
            }
        }
    }

    fun updateTheme(inputDynamicColors: Boolean, inputCustomTheme: String) {
        viewModelScope.launch {
            appThemeParamsDataStore.updateData { params: AppThemeParams ->
                params.copy {
                    dynamicColors = inputDynamicColors
                    customTheme = inputCustomTheme
                }
            }
        }
    }
}