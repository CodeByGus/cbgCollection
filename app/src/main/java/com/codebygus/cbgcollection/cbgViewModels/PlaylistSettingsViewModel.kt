package com.codebygus.cbgcollection.cbgViewModels

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codebygus.cbgcollection.data.proto.playlistSettingsParamsDataStore
import com.codebygus.cbgcollection.proto.PlaylistSettingsParams
import com.codebygus.cbgcollection.proto.copy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistSettingsViewModel(application: Application) : AndroidViewModel(application) {
    data class ViewState(
        val playlistArtistExact: Boolean = true,
        val showAttachmentDialog: Boolean = true,
        val shareOnExternalFileCreate: Boolean = true,
        val playlistItemAdditionMethod: Int = 0,
        val artistSelectListFiltering: Int = 0,
        val genreSelectListFilteringExact: Boolean = true,
        val labelSelectListFilteringExact: Boolean = true,
        val styleSelectListFilteringExact: Boolean = true,
        val defaultPlaylistSortingMethod: Int = 0,
        val typeSelectListSortingMethod: Boolean = true,
        val externalPlaylistFormatMethod: Int = 0,
        val atTypePlaylistEndAction: Int = 0,
        val atTrackPlaylistEndAction: Int = 0,
        val atPlaylistStartAction: Int = 0,
        val playlistConfigAction: Int = 0
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var appStartupParamsCollectorJob: Job? = null

    private val playlistSettingsParamsDataStore: DataStore<PlaylistSettingsParams>
        get() = getApplication<Application>().applicationContext.playlistSettingsParamsDataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playlistSettingsParamsDataStore.data.collectLatest { settingsParams: PlaylistSettingsParams ->
                _viewState.update {currentState ->
                    currentState.copy(
                        playlistArtistExact = settingsParams.playlistArtistExact,
                        showAttachmentDialog = settingsParams.showAttachmentDialog,
                        shareOnExternalFileCreate = settingsParams.shareOnExternalFileCreate,
                        playlistItemAdditionMethod = settingsParams.playlistItemAdditionMethod,
                        artistSelectListFiltering = settingsParams.artistSelectListFiltering,
                        genreSelectListFilteringExact = settingsParams.genreSelectListFilteringExact,
                        labelSelectListFilteringExact = settingsParams.labelSelectListFilteringExact,
                        styleSelectListFilteringExact = settingsParams.styleSelectListFilteringExact,
                        defaultPlaylistSortingMethod = settingsParams.defaultPlaylistSortingMethod,
                        typeSelectListSortingMethod = settingsParams.typeSelectListSortingMethod,
                        externalPlaylistFormatMethod = settingsParams.externalPlaylistFormatMethod,
                        atTypePlaylistEndAction = settingsParams.atTypePlaylistEndAction,
                        atTrackPlaylistEndAction = settingsParams.atTrackPlaylistEndAction,
                        atPlaylistStartAction = settingsParams.atPlaylistStartAction,
                        playlistConfigAction = settingsParams.playlistConfigAction
                    )
                }
            }
        }
    }
    fun updateSetup(inputArtistExact: Boolean, inputShowAttachment: Boolean, inputShareOnCreate: Boolean, inputItemAddition: Int) {
        viewModelScope.launch {
            playlistSettingsParamsDataStore.updateData { params: PlaylistSettingsParams ->
                params.copy {
                    playlistArtistExact = inputArtistExact
                    showAttachmentDialog = inputShowAttachment
                    shareOnExternalFileCreate = inputShareOnCreate
                    playlistItemAdditionMethod = inputItemAddition
                }
            }
        }
    }
    fun updateOrder(inputDefaultOrder: Int, inputSelectionOrder: Boolean, inputExternalFormat: Int) {
        viewModelScope.launch {
            playlistSettingsParamsDataStore.updateData { params: PlaylistSettingsParams ->
                params.copy {
                    defaultPlaylistSortingMethod = inputDefaultOrder
                    typeSelectListSortingMethod = inputSelectionOrder
                    externalPlaylistFormatMethod = inputExternalFormat
                }
            }
        }
    }
    fun updateFilter(inputArtistFilter: Int, inputGenreExact: Boolean, inputLabelExact: Boolean, inputStyleExact: Boolean) {
        viewModelScope.launch {
            playlistSettingsParamsDataStore.updateData { params: PlaylistSettingsParams ->
                params.copy {
                    artistSelectListFiltering = inputArtistFilter
                    genreSelectListFilteringExact = inputGenreExact
                    labelSelectListFilteringExact = inputLabelExact
                    styleSelectListFilteringExact = inputStyleExact
                }
            }
        }
    }
    fun updatePlayers(inputAtTypeEnd: Int, inputAtTrackEnd: Int, inputStartAction: Int, inputConfigAction: Int) {
        viewModelScope.launch {
            playlistSettingsParamsDataStore.updateData { params: PlaylistSettingsParams ->
                params.copy {
                    atTypePlaylistEndAction = inputAtTypeEnd
                    atTrackPlaylistEndAction = inputAtTrackEnd
                    atPlaylistStartAction = inputStartAction
                    playlistConfigAction = inputConfigAction
                }
            }
        }
    }
}