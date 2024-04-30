package com.example.caloriestracker.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.Models.ArticleModel
import com.example.caloriestracker.R
import com.example.caloriestracker.ViewModels.ArticleViewModel

@Composable
fun ArticleView(articleViewState: ArticleViewModel.ArticlesState,
                navigateToDetailInfo: (ArticleModel) -> Unit) {

    var sItems by remember { mutableStateOf(listOf<ArticleModel>()) }
    sItems = articleViewState.articleList

    Scaffold(
        topBar = { TopBarView(title = "Корисна інформація", showBackNav = false)},
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Дієти",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            CarouselCard()
        }
        val index = 0
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 300.dp).background(Color.White)
        ) {
            itemsIndexed(sItems) { index, item ->
                if (index == 0) {
                    line(1.dp, 0f, 0f)
                }
                Article(item, navigateToDetailInfo)
                line(1.dp, 0f, 0f)
            }
        }
    }
}

@Composable
fun Article(item: ArticleModel,
            navigateToDetailInfo: (ArticleModel) -> Unit) {
    IconButton(onClick = { navigateToDetailInfo(item) },
        modifier = Modifier.background(Color.Black).fillMaxWidth().height(60.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.title, style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colorResource(id = R.color.appLightGrey),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoView() {
    //InfoView()
}
