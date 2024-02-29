package com.ahmed_apps.watchy_course.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmed_apps.watchy_course.R
import com.ahmed_apps.watchy_course.main.presentation.ui_components.MediaHomeScreenSection
import com.ahmed_apps.watchy_course.ui.theme.BigRadius
import com.ahmed_apps.watchy_course.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Ahmed Guedmioui
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mainNavController: NavController,
    mainState: MainState,
    onEvent: (MainUiEvents) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                mainNavController.navigate()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Category,
                    contentDescription = stringResource(R.string.categories)
                )
            }
        }
    ) { paddingValues ->
        val padding = paddingValues

        val scope = rememberCoroutineScope()
        var refreshing by remember {
            mutableStateOf(false)
        }

        fun onRefresh() = scope.launch {
            refreshing = true
            delay(1500)
            onEvent(MainUiEvents.Refresh(Screen.Main.route))
            refreshing = false
        }

        val refreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = ::onRefresh
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = BigRadius)
            ) {
                MediaHomeScreenSection(
                    route = Screen.Trending.route,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(16.dp))

                MediaHomeScreenSection(
                    route = Screen.Tv.route,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(16.dp))

                MediaHomeScreenSection(
                    route = Screen.Movies.route,
                    mainState = mainState,
                    mainNavController = mainNavController
                )

                Spacer(modifier = Modifier.height(90.dp))
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = (BigRadius - 8.dp))
            )
        }

    }

}





















