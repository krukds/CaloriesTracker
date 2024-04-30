package com.example.caloriestracker.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import com.example.caloriestracker.R
import com.example.caloriestracker.Utils
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

@Composable
fun DetailProductView(
    product: ProductModel,
    onBackNavClicked: () -> Unit,
    onSaveProductClicked: (ProductModel) -> Unit,
    onDeleteProductClicked: (ProductModel) -> Unit,
) {
    val utils: Utils = Utils()
    var grams by remember { mutableStateOf(TextFieldValue(product.getGrams().toString())) }
    Scaffold(
        topBar = {
            TopBarView(
                title = product.getName(),
                showBackNav = true,
                onBackNavClicked = onBackNavClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = grams,
                        onValueChange = {
                            grams = if (it.text.length <= 6) it else grams
                            if(grams.text.isNotEmpty()) {
                                product.setGrams(grams.text.toInt())
                            } else {
                                product.setGrams(0)
                            }
                        },
                        cursorBrush = SolidColor(Color.White),
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                        ),
                        modifier = Modifier
                            .border(
                                1.dp,
                                colorResource(id = R.color.appDarkGrey),
                                RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .background(colorResource(id = R.color.appDarkGrey))
                            .height(50.dp) // Додано висоту для визначення висоти текстового поля
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .padding(start = 8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                    )

                    Text(
                        "г",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                if(product.id != null) {
                    Button(
                        onClick = { onDeleteProductClicked(product) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFFF6666)
                        ),
                        modifier = Modifier.width(90.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Видалити", color = Color.Black)
                    }
                }
                Button(
                    onClick = {
                        if(product.id != null) {
                            product.setGrams(grams.text.toInt())
                        }
                        onSaveProductClicked(product)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.appGreen)
                    ),
                    modifier = Modifier.width(90.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Зберегти", color = Color.Black)
                }
            }
            Text("Поживна цінність",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Розмір порції",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = product.getGrams().toString() + " г", color = Color.White)
            }
            line(width = 1.dp, start = 0f, end = 0f)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Енергія",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = round(product.getEnergy()).toInt().toString() + " кДж", color = Color.White)
                    Text(text = round(product.getCalories()).toInt().toString() + " ккал", color = Color.White)
                }
            }
            line(width = 1.dp, start = 0f, end = 0f)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Жири",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = utils.formatDouble(product.getFats()) + " г", color = Color.White)
            }
            line(width = 1.dp, start = 0f, end = 0f)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Вуглеводи",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = utils.formatDouble(product.getCarbohydrates()) + " г", color = Color.White)
            }
            line(width = 1.dp, start = 0f, end = 0f)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Білки",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = utils.formatDouble(product.getProteins()) + " г", color = Color.White)
            }
            line(width = 1.dp, start = 0f, end = 0f)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Волокна",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = utils.formatDouble(product.getFiber()) + " г", color = Color.White)
            }
            line(width = 1.dp, start = 0f, end = 0f)

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewDetailProductView() {
    DetailProductView(
        ProductModel(
            1,
            "JERfd RJEKFE erjdsfhIreerf erjhbfr rfbhfede3wdewdwwer erjdbfbrf brhferhb",
            100,
            PartDayTypeEnum.BREAKFAST,
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
            10.1,
            20.2,
            30.3,
            50.5,
            10.0
        ),
        {},
        {},
        {},
    )
}