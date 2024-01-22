package com.example.myhome.ui.screens.cameras

import com.example.myhome.data.models.ApiResult
import com.example.myhome.domain.models.Camera
import com.example.myhome.domain.repositories.MyHouseRepository
import com.example.myhome.ui.base.utills.track
import com.example.myhome.ui.base.viewModel.ListScreenViewModel
import com.example.myhome.ui.base.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CamerasViewModel @Inject constructor(
    private val repository: MyHouseRepository
) : ListScreenViewModel() {

    private var cameraList: List<Camera> = emptyList()

    init {
        launchWithCatch({
            //Show error message
        }) {
            when (val cameras = repository.getCameras()) {
                is ApiResult.Error -> onUIState(
                    uiState.value.copy(
                        error = cameras.error
                    )
                )

                is ApiResult.Success -> {
                    cameras.data?.let { list ->
                        cameraList = list
                        onUIState(
                            uiState.value.copy(
                                itemsList = list.map { it.toUi() }
                            )
                        )
                    }
                }

                else -> Unit
            }
        }.track {
            onUIState(
                uiState.value.copy(
                    isLoading = it
                )
            )
        }
    }

    override fun reduce(uiEvent: UIEvent.OnRefresh) {
        launchWithCatch({
            onUIState(
                uiState.value.copy(
                    error = it.message
                )
            )
        }) {
            repository.getCameras(true).data?.let { list ->
                onUIState(
                    uiState.value.copy(
                        itemsList = list.map { it.toUi() }
                    )
                )
            }
        }.track {
            onUIState(
                uiState.value.copy(
                    isRefreshing = it
                )
            )
        }
    }

    override fun reduce(uiEvent: UIEvent.OnEditItem) {
        launchWithCatch({
            onUIState(uiState.value.copy(
                error = it.message.orEmpty()
            ))
        }) {
            onUIState(uiState.value.copy(
                itemsList = uiState.value.itemsList.map {
                    if (it.id == uiEvent.id) it.copy(name = uiEvent.newName)
                    else it
                }
            ))
            repository.updateCamera(cameraList.first { it.id == uiEvent.id }
                .copy(name = uiEvent.newName))
        }
    }


    override fun getStartUIState(): UIState = UIState()
}