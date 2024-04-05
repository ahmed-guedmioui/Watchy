package com.ahmed_apps.watchy.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed_apps.watchy.favorites.presentation.favorites.FavoritesScreen
import com.ahmed_apps.watchy.favorites.presentation.favorites_list.FavoritesListScreen
import com.ahmed_apps.watchy.util.Screen

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun CoreFavoritesScreen(
    mainNavController: NavController
) {

    val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
    val favoritesState by favoritesViewModel.favoritesState.collectAsState()

    val favoritesNavController = rememberNavController()

    NavHost(
        navController = favoritesNavController,
        startDestination = Screen.Favorites.route
    ) {
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable(Screen.LikedList.route) {
            FavoritesListScreen(
                route = Screen.LikedList.route,
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }

        composable(Screen.Bookmarked.route) {
            FavoritesListScreen(
                route = Screen.Bookmarked.route,
                mainNavController = mainNavController,
                favoritesNavController = favoritesNavController,
                favoritesState = favoritesState,
                onEvent = favoritesViewModel::onEvent
            )
        }
    }

}





















