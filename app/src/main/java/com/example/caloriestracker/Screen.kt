package com.example.caloriestracker

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash-screen")
    object SettingsScreen: Screen("settings")
    object LanguageScreen: Screen("language/settings")
    object ArticleScreen: Screen("article")
    object DetailArticleScreen: Screen("detail/article")
    object DiaryScreen: Screen("diary")
    object StatisticsScreen: Screen("statistics")
    object SearchProductScreen: Screen("search-product")
    object DetailProductScreen: Screen("detail/search-product")
}