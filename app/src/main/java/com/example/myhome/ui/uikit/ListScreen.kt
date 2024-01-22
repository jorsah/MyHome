package com.example.myhome.ui.uikit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myhome.ui.base.viewModel.ListScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(viewModel: ListScreenViewModel) {

    val popupControl = remember { mutableStateOf(false) }
    val popupCameraId = remember {
        mutableIntStateOf(0)
    }
    val popupText = rememberSaveable { mutableStateOf("") }
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = viewModel.uiState.value.isRefreshing,
            onRefresh = { viewModel.onUIEvent(ListScreenViewModel.UIEvent.OnRefresh) }
        )

    if (popupControl.value) {
        Dialog(popupControl, popupText, popupCameraId) { id, name ->
            viewModel.onUIEvent(ListScreenViewModel.UIEvent.OnEditItem(id, name))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {

        when {
            viewModel.uiState.value.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 64.dp)
                        .statusBarsPadding(),
                    color = Color.Black
                )
            }

            viewModel.uiState.value.error.orEmpty().isNotEmpty() -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = viewModel.uiState.value.error.orEmpty()
                )
            }

            else -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                    items(viewModel.uiState.value.itemsList) {
                        DraggableCard(
                            name = it.name,
                            isFavorite = it.favorites,
                            url = it.snapshot,
                            isRevealed = viewModel.uiState.value.revealedItem == it,
                            cardOffset = 200f,
                            onExpand = {
                                viewModel.onUIEvent(ListScreenViewModel.UIEvent.OnExpandItem(it))
                            },
                            onCollapse = {
                                viewModel.onUIEvent(ListScreenViewModel.UIEvent.OnCollapseItem(it))
                            },
                            onEditClicked = {
                                popupCameraId.intValue = it.id
                                popupText.value = it.name
                                popupControl.value = true
                            }
                        )
                    }
                }

            }
        }


        PullRefreshIndicator(
            refreshing = viewModel.uiState.value.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun Dialog(
    popupControl: MutableState<Boolean>,
    popupText: MutableState<String>,
    popupItemId: MutableIntState,
    onEditItem: (id: Int, newName: String) -> Unit
) {
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = { popupControl.value = false },
        text = {
            TextField(
                value = popupText.value,
                onValueChange = { popupText.value = it },
                label = { Text("Change Name") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                popupControl.value = false
                onEditItem(popupItemId.intValue, popupText.value)
            }) {
                Text("Save")
            }
        },
    )
}