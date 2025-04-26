package com.binissa.paleblueassignment.presentation.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val images = viewModel.imagesFlow.collectAsLazyPagingItems()

    val actions = rememberMainActions(viewModel)

    MainScreen(
        modifier = modifier,
        state = state,
        images = images,
        actions = actions,
    )
}

@Composable
fun rememberMainActions(
    viewModel: MainViewModel
): MainAction {
    return remember(viewModel) {
        MainAction(
            onSearch = viewModel::onSearch,
            onQueryChange = viewModel::onQueryChange,
            onSelectImage = viewModel::onSelectImage,
            onClearSelectedImage = viewModel::onClearSelectedImage
        )
    }
}