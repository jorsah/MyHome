package com.example.myhome.ui.screens.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myhome.ui.screens.cameras.CameraScreen
import com.example.myhome.ui.uikit.TabItem
import com.example.myhome.ui.screens.doors.DoorsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val tabs = listOf(
        TabItem(
            title = "Cameras",
            screen = { CameraScreen() }
        ),
        TabItem(
            title = "Doors",
            screen = { DoorsScreen() }
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }

        val pagerState = rememberPagerState { tabs.size }
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                text = "My House"
            )

            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = Color.Black,
            ) {

                tabs.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = (index == selectedTabIndex),
                        onClick = {
                            selectedTabIndex = index

                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedTabIndex)
                            }
                        },
                        text = {
                            Text(text = tabItem.title)
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { index ->
                tabs[index].screen()
            }
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }
    }
}
