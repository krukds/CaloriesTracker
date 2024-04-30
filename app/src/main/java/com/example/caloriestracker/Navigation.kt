package com.example.caloriestracker

import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriestracker.Models.ArticleModel
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import com.example.caloriestracker.ViewModels.SearchProductViewModel
import com.example.caloriestracker.ViewModels.ArticleViewModel
import com.example.caloriestracker.ViewModels.DiaryViewModel
import com.example.caloriestracker.Views.SearchProductView
import com.example.caloriestracker.Views.DetailArticleView
import com.example.caloriestracker.Views.ArticleView
import com.example.caloriestracker.Views.BottomBarView
import com.example.caloriestracker.Views.DetailProductView
import com.example.caloriestracker.Views.DiaryView
import com.example.caloriestracker.Views.LanguageView
import com.example.caloriestracker.Views.SettingsView
import com.example.caloriestracker.Views.StatisticsView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    val articleViewModel: ArticleViewModel = viewModel()
    val diaryViewModel: DiaryViewModel = viewModel()
    val searchProductsViewModel: SearchProductViewModel = viewModel()

    val prefs = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    val editor: SharedPreferences.Editor = prefs.edit()
    editor.putInt("RDNS", 1800)
    editor.apply()

    Scaffold(
        bottomBar = { BottomBarView(navController) }
    ){
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = Screen.DiaryScreen.route,
        ) {
            composable(route = Screen.SettingsScreen.route) {
                SettingsView(navigateToLanguage = {
                    navController.navigate(Screen.LanguageScreen.route)
                })
            }

            composable(route = Screen.LanguageScreen.route) {
                LanguageView(navigateToSetting = {
                    navController.navigate(Screen.SettingsScreen.route)
                })
            }

            composable(route = Screen.ArticleScreen.route) {
                ArticleView(
                    articleViewState = articleViewModel.articlesState.value,
                    navigateToDetailInfo = {
                        if(navController.currentBackStackEntry?.savedStateHandle != null) {
                            navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
                        }
                        navController.navigate(Screen.DetailArticleScreen.route)
                    })
            }

            composable(route = Screen.DetailArticleScreen.route) {
                val article = navController.previousBackStackEntry?.savedStateHandle?.get<ArticleModel>("article")
                    ?: ArticleModel("","", emptyList())
                DetailArticleView(article = article, navigateToInfo = {
                    navController.navigate(Screen.ArticleScreen.route)
                })
            }

            composable(route = Screen.DiaryScreen.route) {
                DiaryView(
                    diaryViewModel,
                    navigateToSearchProduct = { title, partDayType ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("title", title)
                        navController.currentBackStackEntry?.savedStateHandle?.set("partDayType", partDayType)
                        navController.navigate(Screen.SearchProductScreen.route)
                    },
                    navigateToDetailProduct = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("productModel", it)
                        navController.navigate(Screen.DetailProductScreen.route)
                        navController.currentBackStackEntry?.savedStateHandle?.set("isEditOrDelete", true)
                    }
                )
            }

            composable(route = Screen.StatisticsScreen.route) {
                StatisticsView()
            }

            composable(route = Screen.SearchProductScreen.route) {
                val title = navController.previousBackStackEntry?.savedStateHandle?.get<String>("title") ?: "Сніданок"
                navController.currentBackStackEntry?.savedStateHandle?.set("title", title)
                val partDayType = navController.previousBackStackEntry?.savedStateHandle?.get<PartDayTypeEnum>("partDayType") ?: PartDayTypeEnum.BREAKFAST
                SearchProductView(
                    searchProductsViewModel,
                    title = title,
                    additionalText = diaryViewModel.dateState.value.toString(),
                    partDayType = partDayType,
                    navigateToDiary = {
                        searchProductsViewModel.clearSearchProducts()
                        navController.navigate(Screen.DiaryScreen.route)
                    },
                    navigateToDetailProduct = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("productModel", it)
                        navController.navigate(Screen.DetailProductScreen.route)
                    }
                )
            }

            composable(route = Screen.DetailProductScreen.route) {
                val productModel = navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("productModel")
                    ?: ProductModel(
                        1,
                        "",
                        0,
                        PartDayTypeEnum.BREAKFAST,
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        0.0,
                        0.0,
                        0.0 ,
                        0.0,
                        0.0
                    )
                DetailProductView(
                    product = productModel,
                    onBackNavClicked = {
                        if (productModel.id != null) {
                            navController.navigate(Screen.DiaryScreen.route)
                        } else {
                            navController.navigate(Screen.SearchProductScreen.route)
                        }
                    },
                    onSaveProductClicked = {
                        diaryViewModel.saveProduct(it)
                        searchProductsViewModel.clearSearchProducts()
                        navController.navigate(Screen.DiaryScreen.route)
                    },
                    onDeleteProductClicked = {
                        diaryViewModel.deleteProduct(it)
                        navController.navigate(Screen.DiaryScreen.route)
                    }
                )
            }
        }
    }
}