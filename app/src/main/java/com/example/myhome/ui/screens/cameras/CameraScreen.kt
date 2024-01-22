package com.example.myhome.ui.screens.cameras

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myhome.ui.uikit.ListScreen


@Composable
fun CameraScreen() {
    val viewModel = hiltViewModel<CamerasViewModel>()

    ListScreen(viewModel = viewModel)
}