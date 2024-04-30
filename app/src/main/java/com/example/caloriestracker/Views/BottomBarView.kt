package com.example.caloriestracker.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.caloriestracker.R
import com.example.caloriestracker.Screen


data class BottomItem(
    val label:String,
    val iconId: Int,
    val route: String
)

@Composable
fun BottomBarView(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val itemList: List<BottomItem> = listOf(
        BottomItem(
            "Щоденник",
            R.drawable.fork_and_knife_grey,
            Screen.DiaryScreen.route
        ),
        /*BottomItem(
            "Статистика",
            R.drawable.statistics_grey,
            Screen.StatisticsScreen.route
        ),*/
        BottomItem(
            "Інформація",
            R.drawable.idea_grey,
            Screen.ArticleScreen.route
        ),
        BottomItem(
            "Налаштування",
            R.drawable.baseline_settings_grey,
            Screen.SettingsScreen.route
        )
    )

    if (itemList.map { it.route }.contains(currentRoute)){
        BottomNavigation(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            backgroundColor =  colorResource(id = R.color.appDarkGrey)
        )
        {
            itemList.forEach {
                    item ->
                NavBottomItem(navController, item)
            }

        }
    }

}

@Composable
fun NavBottomItem(
    navController: NavController,
    item: BottomItem
){
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route;

    var color = colorResource(id = R.color.appLightGrey)
    if (currentRoute == item.route){
        color = colorResource(id = R.color.appGreen)
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(8.dp)
        .background(colorResource(id = R.color.appDarkGrey))
        .clickable { navController.navigate(item.route) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = item.iconId), contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = color)
        Text(text = item.label, fontSize = 14.sp, color = color)
    }
}

@Preview
@Composable
fun PreviewBottomBarView() {
    // BottomBarView()
}