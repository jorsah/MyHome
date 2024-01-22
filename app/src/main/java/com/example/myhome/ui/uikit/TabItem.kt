package com.example.myhome.ui.uikit

import androidx.compose.runtime.Composable

data class TabItem(
     val title: String,
     val screen: @Composable () -> Unit
 )
