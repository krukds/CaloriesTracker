package com.example.caloriestracker.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.Models.ArticleModel
import com.example.caloriestracker.Models.Link
import com.example.caloriestracker.R



@Composable
fun DetailArticleView(article: ArticleModel,
                      navigateToInfo: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopBarView(
                title = article.title,
                onBackNavClicked = navigateToInfo
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = article.text, style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.padding(16.dp))
            /*Text("Детальна інформація:",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )*/
            Spacer(modifier = Modifier.height(8.dp))
            if(!article.links.isEmpty()) line(1.dp, 0f, 0f)
            article.links.forEach { link ->
                VideoItem(link, {
                    uriHandler.openUri(link.link)
                })

            }
        }
    }
}

@Composable
fun VideoItem(link: Link, onButtonClick:()->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.appDarkGrey)
            )
        ){
            Image(
                painter = painterResource(id = link.id),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .padding(end = 16.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(text = link.title,
                style = TextStyle(fontSize = 16.sp, color = Color.White)
            )
        }
    }

    line(1.dp, 0f, 0f)
}



@Preview
@Composable
fun PreviewDetailInfoView() {
//    DetailInfoView(
//        "Які основні принципи здорового харчування?",
//        "Здорове харчування - це ключовий аспект здорового способу життя, який сприяє оптимальному фізичному та психічному самопочуттю. Основні принципи здорового харчування включають збалансований раціон, різноманітність, модерацію, мінімізацію оброблених продуктів, гідрацію, модерацію цукрів і насичених жирів, фізичну активність та споживання відповідно до потреб.",

//    )
}