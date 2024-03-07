package com.ahmed_apps.watchy_course.categories.presentation.categoies_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmed_apps.watchy_course.categories.presentation.CategoriesState
import com.ahmed_apps.watchy_course.ui.theme.HugeRadius
import com.ahmed_apps.watchy_course.ui.ui_components.MediaItemImageAndTitle
import com.ahmed_apps.watchy_course.ui.ui_components.NonFocusedTopBar
import com.ahmed_apps.watchy_course.util.Constants
import kotlin.math.roundToInt

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun CategoriesListScreen(
    category: String,
    mainNavController: NavController,
    categoriesState: CategoriesState
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

    val mediaList = when (category) {
        Constants.actionAndAdventureList -> categoriesState.actionAndAdventureList
        Constants.dramaList -> categoriesState.dramaList
        Constants.comedyList -> categoriesState.comedyList
        Constants.sciFiAndFantasyList -> categoriesState.sciFiAndFantasyList
        else -> categoriesState.animationList
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
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
            title = category
        )

    }
}





















