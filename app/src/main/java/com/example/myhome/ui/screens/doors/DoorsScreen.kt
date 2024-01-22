package com.example.myhome.ui.screens.doors

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myhome.ui.uikit.ListScreen

@Composable
fun DoorsScreen() {
    val viewModel = hiltViewModel<DoorsViewModel>()

    ListScreen(viewModel = viewModel)
}