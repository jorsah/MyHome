package com.example.myhome.ui.base.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.ui.base.utills.launchWithCatch
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<UIState : BaseUIState>(
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    @Suppress("LeakingThis")
    private val _uiState = mutableStateOf(getStartUIState())
    val uiState: State<UIState>
        get() = _uiState

    abstract fun getStartUIState(): UIState

    open fun onUIEvent(uiEvent: BaseUIEvent) {
    }

    protected fun onUIState(newUIState: UIState) {
        _uiState.value = newUIState
    }

    protected fun launchWithCatch(
        catch: suspend (Throwable) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launchWithCatch(
            block = block,
            catch = {
                catch(it)
            }
        )
    }
}

interface BaseUIState
object NoUIState : BaseUIState

interface BaseUIEvent
