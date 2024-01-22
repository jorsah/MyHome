package com.example.myhome.ui.base.viewModel

import com.example.myhome.ui.base.model.ListItemUi

abstract class ListScreenViewModel
    : BaseViewModel<ListScreenViewModel.UIState>() {

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnExpandItem -> reduce(uiEvent)
            is UIEvent.OnCollapseItem -> reduce(uiEvent)
            is UIEvent.OnEditItem -> reduce(uiEvent)
            is UIEvent.OnRefresh -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    abstract fun reduce(uiEvent: UIEvent.OnRefresh)

    abstract fun reduce(uiEvent: UIEvent.OnEditItem)

    private fun reduce(uiEvent: UIEvent.OnExpandItem) {
        if (uiState.value.revealedItem != uiEvent.camera) {
            onUIState(
                uiState.value.copy(
                    revealedItem = uiEvent.camera
                )
            )
        }
    }

    private fun reduce(uiEvent: UIEvent.OnCollapseItem) {
        onUIState(
            uiState.value.copy(
                revealedItem = ListItemUi.Empty
            )
        )
    }

    data class UIState(
        val isLoading: Boolean = true,
        val itemsList: List<ListItemUi> = emptyList(),
        val revealedItem: ListItemUi = ListItemUi.Empty,
        val error: String? = null,
        val isRefreshing: Boolean = false
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data object OnRefresh : UIEvent()
        data class OnExpandItem(val camera: ListItemUi) : UIEvent()
        data class OnCollapseItem(val camera: ListItemUi) : UIEvent()
        data class OnEditItem(val id: Int, val newName: String) : UIEvent()
    }

}