package com.binissa.paleblueassignment.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.binissa.paleblueassignment.domain.usecase.GetPagedImagesUseCase
import com.binissa.paleblueassignment.presentation.mapper.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPagedImagesUseCase: GetPagedImagesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val imagesFlow = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getPagedImagesUseCase(query)
                .map { pagingData -> pagingData.map {
                    it.toUI()
                } }
                .cachedIn(viewModelScope)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
        searchQuery.value = query
    }

    fun onSearch() {
        searchQuery.value = searchQuery.value.trim()
    }

    fun onSelectImage(image: ImageItem) {
        _state.update { it.copy(selectedImage = image) }
    }

    fun onClearSelectedImage() {
        _state.update { it.copy(selectedImage = null) }
    }
}