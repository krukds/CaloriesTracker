package com.example.caloriestracker.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import com.example.caloriestracker.Models.ProductResponseModel
import com.example.caloriestracker.R
import com.example.caloriestracker.Utils
import com.example.caloriestracker.ViewModels.SearchProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchProductView(viewModel: SearchProductViewModel,
                      title: String,
                      additionalText: String,
                      partDayType: PartDayTypeEnum,
                      navigateToDiary:() -> Unit,
                      navigateToDetailProduct:(ProductModel) -> Unit) {
    val searchViewState by viewModel.searchProductsState
    val utils = Utils()
    val openDialog = remember { mutableStateOf(false) }
    val wifiConnected = remember { mutableStateOf(false) }

    fun showDialog() {
        openDialog.value = true
    }

    fun dismissDialog() {
        openDialog.value = false
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val connected = withContext(Dispatchers.IO) {
            utils.isWifiConnected(context)
        }
        wifiConnected.value = connected
        if (!connected) {
            showDialog()
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { dismissDialog() }, // Функція, яка викликається при закритті вікна
            title = { Text(text = "Відсутнє підключення до Wi-Fi") },
            text = { Text(text = "Для пошуку та додавання продуктів вам необхідно бути підключеними до Wi-Fi") },
            confirmButton = {
                Button(onClick = {
                    dismissDialog()
                    wifiConnected.value = utils.isWifiConnected(context)
                    if(!wifiConnected.value) navigateToDiary()
                }) {
                    Text("OK")
                }
            }
        )
    }


    Scaffold(topBar = {
        TopBarView(
            title = title,
            showBackNav = true,
            additionalText = additionalText,
            onBackNavClicked = navigateToDiary
        )
    }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            SearchTextField(viewModel)
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                println("loading")
                println(searchViewState.loading)
                if(searchViewState.loading) {
                    CircularProgressIndicator(color = Color.White)
                } else if(searchViewState.error != null){
                    Text("ERROR OCCURED")
                } else{
                searchViewState.list.forEach{
                    SearchProductItem(
                        it,
                        partDayType,
                        LocalDate.parse(additionalText, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        navigateToDetailProduct
                    )
                    line(width = 0.dp, 40f, 40f)
                }
            }

            }
        }
    }

}


@Composable
fun SearchTextField(viewModel: SearchProductViewModel) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var showClear = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(id = R.color.appLightGrey),
                    RoundedCornerShape(25.dp)
                )
                .height(50.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.loading()
                    viewModel.fetchSearchProducts(it.text)

                },
                cursorBrush = SolidColor(Color.White),
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Search // Кнопка "Пошук" на клавіатурі
//                )
            )
            if(text.text.isEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.appLightGrey),
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "Пошук їжі",
                        color = colorResource(id = R.color.appLightGrey),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                    if(showClear.value) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = colorResource(id = R.color.appLightGrey),
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchProductItem(product: ProductResponseModel,
                      partDayType: PartDayTypeEnum,
                      date: LocalDate,
                      navigateToDetailProduct:(ProductModel) -> Unit) {
    Button(
        onClick = {
            navigateToDetailProduct(
                ProductModel(
                    null,
                    product.title,
                    100,
                    partDayType,
                    date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    product.fat.toDouble(),
                    product.carbohydrate.toDouble(),
                    product.protein.toDouble(),
                    product.energy.toDouble(),
                    product.fiber?.toDouble() ?: 0.0
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(product.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row() {
                    Text("100 г", color = colorResource(id = R.color.appGreen), fontSize = 12.sp)
                    Text(" - " + product.energy + " ккал",
                        color = colorResource(id = R.color.appLightGrey), fontSize = 12.sp)
                }
            }
            //var checked by remember { mutableStateOf(false) }
//            RoundedCornerCheckbox(
//                label = "",
//                isChecked = checked,
//                onValueChange = { checked = it },
//                modifier = Modifier.padding(0.dp)
//            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = false)
@Composable
fun PreviewAddProductView() {
//    ProductItem(
//        ProductModel(
//            "1",
//            "Apple",
//            100.0,
//            PartDayTypeEnum.BREAKFAST,
//            LocalDate.now(),
//            10.1,
//            20.2,
//            30.3,
//            50.5
//        )
//    )
}

@Composable
fun RoundedCornerCheckbox(
    label: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Float = 24f,
    checkedColor: Color = colorResource(id = R.color.appGreen),
    uncheckedColor: Color = Color(0xFF4D4D4A),
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor)
    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(48.dp) // height of 48dp to comply with minimum touch target size
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = RoundedCornerShape(20.dp))
                .border(
                    width = 1.5.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
        )
    }
}