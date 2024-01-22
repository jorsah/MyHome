package com.example.myhome.ui.base.utills

import kotlinx.coroutines.Job

fun Job.track(
    state: (isWorking: Boolean) -> Unit
): Job {
    state(isActive)
    this.invokeOnCompletion {
        state(false)
    }
    return this
}
