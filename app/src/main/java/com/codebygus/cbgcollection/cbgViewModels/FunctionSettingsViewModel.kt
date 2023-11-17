package com.codebygus.cbgcollection.cbgViewModels

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettingsCompanion.Companion.atTypePlaylistEndAction
import com.codebygus.cbgcollection.data.proto.functionSettingsParamsDataStore
import com.codebygus.cbgcollection.proto.FunctionSettingsParams
import com.codebygus.cbgcollection.proto.copy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FunctionSettingsViewModel(application: Application) : AndroidViewModel(application) {
    data class ViewState(
        val albumListOrder: Int = 0,
        val albumListSavePosition: Boolean = true,
        val tracksByTitle: Boolean = true,
        val searchAtStart: Boolean = true,
        val atAlbumEndAction: Int = 0,
        val audioEffectsAvailable: Int = 0,
        val albumScrollMethod: Int = 0,
        val typeScrollMethod: Int = 0,
        val trackScrollMethod: Int = 0,
        val entryScrollMethod: Int = 0
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var appStartupParamsCollectorJob: Job? = null

    private val functionSettingsParamsDataStore: DataStore<FunctionSettingsParams>
        get() = getApplication<Application>().applicationContext.functionSettingsParamsDataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            functionSettingsParamsDataStore.data.collectLatest { settingsParams: FunctionSettingsParams ->
                _viewState.update {currentState ->
                    currentState.copy(
                        albumListOrder = settingsParams.albumListOrder,
                        albumListSavePosition = settingsParams.albumListSavePosition,
                        tracksByTitle = settingsParams.tracksByTitle,
                        searchAtStart = settingsParams.searchAtStart,
                        atAlbumEndAction = settingsParams.atAlbumEndAction,
                        audioEffectsAvailable = settingsParams.audioEffectsAvailable,
                        albumScrollMethod = settingsParams.albumScrollMethod,
                        typeScrollMethod = settingsParams.typeScrollMethod,
                        trackScrollMethod = settingsParams.trackScrollMethod,
                        entryScrollMethod = settingsParams.entryScrollMethod
                    )
                }
            }
        }
    }
    fun updateMain(inputAlbumOrder: Int, inputSaveListPos: Boolean, inputTracksByTitle: Boolean, inputSearchAtStart: Boolean) {
        viewModelScope.launch {
            functionSettingsParamsDataStore.updateData { params: FunctionSettingsParams ->
                params.copy {
                    albumListOrder = inputAlbumOrder
                    albumListSavePosition = inputSaveListPos
                    tracksByTitle = inputTracksByTitle
                    searchAtStart = inputSearchAtStart
                }
            }
        }
    }
    fun updatePlayer(inputAtAlbumEnd: Int, inputAudioEffects: Int) {
        viewModelScope.launch {
            functionSettingsParamsDataStore.updateData { params: FunctionSettingsParams ->
                params.copy {
                    atTypePlaylistEndAction = inputAtAlbumEnd
                    audioEffectsAvailable = inputAudioEffects
                }
            }
        }
    }
    fun updateScrolling(inputAlbumScroll: Int, inputTypeScroll: Int, inputTrackScroll: Int, inputEntryScroll: Int) {
        viewModelScope.launch {
            functionSettingsParamsDataStore.updateData { params: FunctionSettingsParams ->
                params.copy {
                    albumScrollMethod = inputAlbumScroll
                    typeScrollMethod = inputTypeScroll
                    trackScrollMethod = inputTrackScroll
                    entryScrollMethod = inputEntryScroll
                }
            }
        }
    }
}