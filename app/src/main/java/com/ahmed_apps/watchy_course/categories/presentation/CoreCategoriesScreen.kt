package com.ahmed_apps.watchy_course.categories.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahmed_apps.watchy_course.categories.presentation.categoies_list.CategoriesListScreen
import com.ahmed_apps.watchy_course.categories.presentation.category.CategoriesScreen
import com.ahmed_apps.watchy_course.util.Screen

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun CoreCategoriesScreen(
    mainNavController: NavController
) {

    val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
    val categoriesState by categoriesViewModel.categoriesState.collectAsState()

    val categoriesNavController = rememberNavController()

    NavHost(
        navController = categoriesNavController,
        startDestination = Screen.Categories.route
    ) {
        composable(Screen.Categories.route) {
            CategoriesScreen(
                categoriesNavController = categoriesNavController,
                categoriesState = categoriesState
            )
        }

        composable(
            "${Screen.CategoriesList.route}?category={category}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) {

            val category = it.arguments?.getString("category")

            category?.let { categoryType ->
                CategoriesListScreen(
                    category = category,
                    mainNavController = mainNavController,
                    categoriesState = categoriesState
                )
            }

        }
    }

}




















