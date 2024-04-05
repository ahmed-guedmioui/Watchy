package com.ahmed_apps.watchy.favorites.presentation.favorites_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmed_apps.watchy.R
import com.ahmed_apps.watchy.favorites.presentation.FavoritesState
import com.ahmed_apps.watchy.favorites.presentation.FavoritesUiEvents
import com.ahmed_apps.watchy.ui.theme.BigRadius
import com.ahmed_apps.watchy.ui.theme.HugeRadius
import com.ahmed_apps.watchy.ui.ui_components.MediaItemImageAndTitle
import com.ahmed_apps.watchy.ui.ui_components.NonFocusedTopBar
import com.ahmed_apps.watchy.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @author Ahmed Guedmioui
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesListScreen(
    route: String,
    mainNavController: NavController,
    favoritesNavController: NavController,
    favoritesState: FavoritesState,
    onEvent: (FavoritesUiEvents) -> Unit
) {

    val toolbarHeightPx = with(LocalDensity.current) {
        HugeRadius.roundToPx().toFloat()
    }

    val toolbarOffsetHeightPx = remember {
        mutableFloatStateOf(0f)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val mediaList = when (route) {
        Screen.LikedList.route -> favoritesState.likedList
        else -> favoritesState.bookmarksList
    }

    val title = when (route) {
        Screen.LikedList.route -> stringResource(R.string.favorites)
        else -> stringResource(R.string.bookmarks)
    }

    val scope = rememberCoroutineScope()
    var refreshing by remember {
        mutableStateOf(false)
    }

    fun onRefresh() = scope.launch {
        refreshing = true
        delay(1500)
        onEvent(FavoritesUiEvents.Refresh)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = ::onRefresh
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .pullRefresh(refreshState)
    ) {
        val lazyState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Adaptive(190.dp),
            state = lazyState,
            contentPadding = PaddingValues(top = HugeRadius)
        ) {

            items(mediaList.size) { index ->
                MediaItemImageAndTitle(
                    media = mediaList[index],
                    mainNavController = mainNavController
                )
            }

        }

        NonFocusedTopBar(
            mainNavController = mainNavController,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            title = title
        )

        PullRefreshIndicator(
            refreshing = refreshing,
            state = refreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = (BigRadius - 8.dp))
        )

    }
}





















