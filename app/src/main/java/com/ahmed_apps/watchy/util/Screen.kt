package com.ahmed_apps.watchy.util

/**
 * @author Ahmed Guedmioui
 */
sealed class Screen(val route: String) {
    object Core: Screen("core")


    object Login: Screen("login")
    object Register: Screen("register")

    object Main: Screen("main")
    object Trending: Screen("trending")
    object Tv: Screen("tv")
    object Movies: Screen("movies")


    object Profile: Screen("profile")



    object CoreDetails: Screen("core_details")
    object Details: Screen("details")
    object WatchVideo: Screen("watch_video")
    object Similar: Screen("similar")
    object Search: Screen("search")


    object CoreFavorites: Screen("core_favorites")
    object Favorites: Screen("favorites")
    object LikedList: Screen("liked_list")
    object Bookmarked: Screen("bookmarked")

    object CoreCategories: Screen("core_categories")
    object Categories: Screen("categories")
    object CategoriesList: Screen("categories_list")

}






















