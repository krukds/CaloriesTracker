package com.example.caloriestracker.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.R
import com.example.caloriestracker.Utils

@Composable
fun TopBarView(title: String,
               showBackNav: Boolean = true,
               forwardButtonText: String? = null,
               additionalText: String? = null,
               onBackNavClicked: () -> Unit = {},
               onForwardNavClicked: () -> Unit = {},
               additionalContent: @Composable () -> Unit = {}
) {
    val utils = Utils()
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = utils.trimTextToMaxLength(title),
                        color = colorResource(id = R.color.white),
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    additionalContent()
                }

                if (additionalText != null) {
                    additionalText(additionalText)
                }
            }
        },
        modifier = Modifier.height(60.dp),
        backgroundColor = colorResource(id = R.color.appBlue),
        navigationIcon = { backButton(showBackNav, onBackNavClicked) },
        actions = {
            forwardButton(forwardButtonText, onForwardNavClicked)
        }
    )

}

@Composable
fun backButton(showBackNav: Boolean, onBackNavClicked: () -> Unit) {
    if(showBackNav)
    {
        IconButton(onClick = { onBackNavClicked() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = colorResource(id = R.color.appGreen),
                contentDescription = null
            )
        }

    } else {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            onClick = {},
            elevation = ButtonDefaults.elevation(0.dp)
        ) {}

    }
}

@Composable
fun forwardButton(forwardButtonText: String?, onForwardNavClicked: () -> Unit) {
    if(forwardButtonText != null)
    {
        Button(
            onClick = { onForwardNavClicked() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                colorResource(id = R.color.appGreen)
            ),
            elevation = ButtonDefaults.elevation(0.dp) // Видалення тіні
        ) {
            Text(
                text = forwardButtonText,
                style = TextStyle(fontSize = 15.sp)
            )

        }
    } else {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            onClick = {},
            elevation = ButtonDefaults.elevation(0.dp)
        ) {}

    }
}

@Composable
fun additionalText(additionalText: String) {
    if(additionalText != null)
    {
        Text(additionalText, fontSize = 13.sp,
            textAlign = TextAlign.Center, color = Color.White
        )

    } else {
        null
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBarView() {
    TopBarView("Які",
        showBackNav = true,
        forwardButtonText = "dawdwad",
        additionalText = "fcdvx",
        additionalContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White)
            }
        }
    )
}