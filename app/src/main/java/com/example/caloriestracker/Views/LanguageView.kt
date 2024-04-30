package com.example.caloriestracker.Views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.R

data class Language(var lang1: String,
                    val lang2: String,
                    var isSelected: Boolean)
@Composable
fun LanguageView(navigateToSetting: () -> Unit) {
    val lanList  = remember { mutableStateListOf(
        Language("Українська", "Українська", true),
        Language("English", "Англійська", false),
        Language("Français", "Французька", false),
        Language("Deutsch", "Німецька", false),
        Language("Polski", "Польська", false)
    ) }

    Scaffold(
        topBar = { TopBarView(
            title = "Мова застосунку",
            additionalText = "Вибрана мова: Українська",
            onBackNavClicked = navigateToSetting
        )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.Black)
        ) {
            Text(
                text = "Доступні мови",
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp)
            )
            line(1.dp, 0f, 0f)
            for(item in lanList) {
                LanguageItem(
                    item,
                    {
                        lanList.replaceAll { it.copy(isSelected = it.lang1 == item.lang1) }
                    }
                )
                line(1.dp, 0f, 0f)
            }

        }
    }
}

@Composable
fun LanguageItem(language: Language,
                 onButtonClicked:() -> Unit){

    val checkMark: (@Composable () -> Unit)? =
        if(language.isSelected) {
            {
                Icon(imageVector = Icons.Default.Check, contentDescription = null,
                    tint = colorResource(id = R.color.appGreen)
                )
            }
        } else {
            null
        }
    Button(onClick = onButtonClicked,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.appDarkGrey)
        ),
        shape = RoundedCornerShape(0)) {
        Row( modifier = Modifier
            .fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Column {
                Text(text = language.lang1, style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp))
                Text(text = language.lang2, style = TextStyle(color = colorResource(id = R.color.appLightGrey),
                    fontSize = 15.sp))
            }
            if(checkMark!=null) {
                checkMark()
            }
        }

    }
}

@Composable
fun line(width: Dp, start: Float, end: Float) {
    Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
        drawLine(
            start = Offset(start, 0f),
            end = Offset(size.width - end, 0f),
            color = Color.White,
            strokeWidth = width.toPx()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageView() {
    //LanguageView()
}