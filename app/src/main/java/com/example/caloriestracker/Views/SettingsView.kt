package com.example.caloriestracker.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.R


@Composable
fun SettingsView(navigateToLanguage:() -> Unit) {
    Scaffold(
        topBar = {
            TopBarView(title = "Налаштування", showBackNav = false)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.Black)
            .padding(16.dp)
        ) {
            Text(text = "Параметри",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )

            SettingsItem(
                painterResource(id = R.drawable.baseline_light_mode_24),
                "Темний режим",
                "За замовчуванням",
                {})

            SettingsItem(
                icon = painterResource(id = R.drawable.baseline_language_24),
                mainText = "Мова застосунку",
                text = "Українська",
                onNavClicked = navigateToLanguage)

            Text(text = "Персоналізація",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )

            SettingsItem(
                painterResource(id = R.drawable.baseline_monitor_weight_24),
                "Моя вага",
                "",
                {})

            SettingsItem(
                    painterResource(id = R.drawable.baseline_calculate_24),
            "РДНС",
            "1800 ккал",
            {})

            SettingsItem(
                painterResource(id = R.drawable.baseline_alarm_24),
                "Нагадування",
                "",
                {})

        }
    }
}

@Composable
fun SettingsItem(icon: Painter,
                 mainText: String,
                 text: String,
                 onNavClicked: () -> Unit)
{
    val addText: (@Composable () -> Unit)? =
        if(text != "") {
            {
                Text(
                    text = text, color = colorResource(id = R.color.appGreen),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        } else {
            null
        }


    Button(onClick = { onNavClicked() }, modifier = Modifier.padding(top = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.appDarkGrey)),
            shape = RoundedCornerShape(13.dp)
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = icon, contentDescription = null,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        tint = colorResource(id = R.color.appLightGrey))
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(text = mainText, color = Color.White,
                            style = TextStyle(fontSize = 20.sp,
                                fontWeight = FontWeight.Bold))
                        if(addText != null) {
                            addText()
                        }
                    }
                }
            }
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = colorResource(id = R.color.appLightGrey)
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsView() {
    //SettingsView()
}