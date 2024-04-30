package com.example.caloriestracker.Views

import CustomDatePicker
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.Models.PartDayModel
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import com.example.caloriestracker.R
import com.example.caloriestracker.Utils
import com.example.caloriestracker.ViewModels.DiaryViewModel
import java.security.AccessController.getContext
import kotlin.math.round


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryView(diaryViewModel: DiaryViewModel,
              navigateToSearchProduct: (String, PartDayTypeEnum) -> Unit,
              navigateToDetailProduct: (ProductModel) -> Unit) {

    val products = diaryViewModel.getProducts.value.collectAsState(initial = listOf())
    diaryViewModel.partDayListState.forEach { partDay ->
        partDay.productList = products.value.filter { it.getPartDayType().equals(partDay.partDayType) }.toMutableList()
    }

    Scaffold(
        topBar = { TopBarView(title = "", showBackNav = false,
            additionalContent = {
                CustomDatePicker(
                    value = diaryViewModel.dateState.value,
                    onValueChange = {
                        diaryViewModel.setDate(it)
                    }
                )
            })},
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.Black)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                var fats = 0.0
                var carbohyydrates = 0.0
                var proteins = 0.0
                var rdns = 0
                var calories = 0
                diaryViewModel.partDayListState.forEach{
                    fats += it.getTotalFats()
                    carbohyydrates += it.getTotalCarbohydrates()
                    proteins += it.getTotalProteins()
                    rdns += it.getTotalRDNS(1800)
                    calories += it.getTotalCalories()
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Жири",
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .padding(start = 38.dp)
                                .width(40.dp),
                            textAlign = TextAlign.Left
                        )
                        Text(
                            "Вугл",
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .width(40.dp),
                            textAlign = TextAlign.Left)
                        Text(
                            "Білки",
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .width(40.dp),
                            textAlign = TextAlign.Left)
                        Text(
                            "РДНС",
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .width(40.dp),
                            textAlign = TextAlign.Left)
                        Text(
                            "Калорії",
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .width(60.dp)
                                .padding(end = 5.dp),
                            textAlign = TextAlign.Left)
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        GeneralInfoItem(
                            fats,
                            carbohyydrates,
                            proteins,
                            rdns,
                            Color.White,
                            0.dp
                        )
                        Text(
                            calories.toString(),
                            color = colorResource(id = R.color.appVeryLightGrey),
                            modifier = Modifier
                                .width(60.dp)
                                .padding(end = 5.dp),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold)
                    }
                }
                diaryViewModel.partDayListState.forEach { partDay ->
                    PartDayItem(item = partDay, navigateToSearchProduct, navigateToDetailProduct)
                }

                ResultInfo(diaryViewModel.partDayListState)

            }
        }
    }
}

@Composable
fun ResultInfo(partDayListState: MutableList<PartDayModel>) {
    val utils = Utils()
    var fats = 0.0
    var carbohyydrates = 0.0
    var proteins = 0.0
    var rdns = 0
    var calories = 0
    partDayListState.forEach{
        fats += it.getTotalFats()
        carbohyydrates += it.getTotalCarbohydrates()
        proteins += it.getTotalProteins()
        rdns += it.getTotalRDNS(1800)
        calories += it.getTotalCalories()
    }

    val prefs = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    val totalRdns = prefs.getInt("RDNS", 0)

    Text(
        text = "Підсумок",
        color = Color.White,
        modifier = Modifier.padding(top = 16.dp),
        fontSize = 20.sp
    )
    Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Залишилось калорій", color = Color.White)
        Text(text = (totalRdns-calories).toString(), color = Color.White)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Спожито калорій", color = Color.White)
        Text(text = (calories).toString(), color = Color.White)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "$rdns% від РДНС",
            color = colorResource(id = R.color.appLightGrey),
            fontStyle = FontStyle.Italic)
        Text(text = totalRdns.toString(),
            color = colorResource(id = R.color.appLightGrey),
            fontStyle = FontStyle.Italic)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Text(text = "Всього жирів: ", color = Color.White)
        Text(text = utils.formatDouble(fats) + "г", color = Color.White)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        Text(text = "Всього білків: ", color = Color.White)
        Text(text = utils.formatDouble(proteins) + "г", color = Color.White)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        Text(text = "Всього вуглеводів: ", color = Color.White)
        Text(text = utils.formatDouble(carbohyydrates) + "г", color = Color.White)
    }

}

@Composable
fun PartDayItem(item: PartDayModel,
                navigateToSearchProduct:(String, PartDayTypeEnum) -> Unit,
                navigateToDetailProduct: (ProductModel) -> Unit) {
    val isEmpty = item.productList.isEmpty()
    val isOpen = item.isOpen

    var shape: Shape = RoundedCornerShape(13.dp)
    if (!isEmpty) {
        shape = RoundedCornerShape(
            topStart = 13.dp,
            topEnd = 13.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    }

    Column(modifier = Modifier
        .padding(horizontal = 3.dp, vertical = 5.dp)
        .background(
            colorResource(
                id = R.color.appLightBlue
            ),
            shape = RoundedCornerShape(13.dp)
        )
    ) {
        Button(
            onClick = { navigateToSearchProduct(item.title, item.partDayType) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.appLightBlue)
            ),
            shape = shape,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(5.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = item.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(horizontal = 8.dp)) {
                        if(!isEmpty) {
                            Text(
                                text = item.getTotalCalories().toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Калорії", color = Color.White)
                        }
                    }
                    Image(painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }

            }
        }

        if(!isEmpty) {
            if(!isOpen.value) {
                Button(
                    onClick = {
                        item.isOpen.value = !item.isOpen.value
                    },
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 13.dp,
                        bottomEnd = 13.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.appBlue),
                    ),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    GeneralInfoRowButtonItem(item, isOpen)
                }
            } else {
                line(0.dp, 0f, 0f)
                Button(
                    onClick = {
                        item.isOpen.value = !item.isOpen.value
                    },
                    border = BorderStroke(0.dp, colorResource(id = R.color.appLightBlue)),
                    modifier = Modifier
                        .fillMaxSize(),
                    //.border(1.dp, Color.White)
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.appLightBlue),
                    ),
                    shape = RoundedCornerShape(0),
                    elevation = ButtonDefaults.elevation(0.dp),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    GeneralInfoRowButtonItem(item, isOpen)
                }
                item.productList.forEach{
                    ProductButtonItem(
                        it,
                        item.productList.indexOf(it) == item.productList.lastIndex,
                        navigateToDetailProduct
                    )
                }

            }
        }
    }
}



@Composable
fun GeneralInfoRowButtonItem(item: PartDayModel, isOpen: MutableState<Boolean>) {
    var imageVector = Icons.Default.KeyboardArrowDown
    if(isOpen.value)
        imageVector = Icons.Default.KeyboardArrowUp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GeneralInfoItem(
            item.getTotalFats(),
            item.getTotalCarbohydrates(),
            item.getTotalProteins(),
            item.getTotalRDNS(1800),
            Color.White,
            30.dp
        )
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = colorResource(id = R.color.appLightGrey),
        )
    }
}

@Composable
fun GeneralInfoItem(fats: Double, carb: Double, protein: Double, rdns: Int, color: Color, padding: Dp) {
    val utils = Utils()
    Text(
        utils.formatDouble(fats),
        modifier = Modifier
            .padding(start = 38.dp)
            .width(40.dp),
        textAlign = TextAlign.Left,
        color = color
    )
    Text(
        utils.formatDouble(carb),
        color = color,
        textAlign = TextAlign.Left,
        modifier = Modifier.width(40.dp)
    )
    Text(
        utils.formatDouble(protein),
        color = color,
        textAlign = TextAlign.Left,
        modifier = Modifier.width(40.dp)
    )
    Text(
        "$rdns%",
        textAlign = TextAlign.Left,
        modifier = Modifier
            .padding(end = padding)
            .width(40.dp),
        color = color
    )
}

@Composable
fun ProductButtonItem(product: ProductModel,
                      isLast:Boolean,
                      navigateToDetailProduct: (ProductModel) -> Unit) {
    var shape = RoundedCornerShape(0.dp)
    if(isLast) {
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 13.dp,
            bottomEnd = 13.dp
        )
    }
    line(0.dp, 0f, 0f)
    Button(
        onClick = { navigateToDetailProduct(product) },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.appLightBlue),
        ),
        contentPadding = PaddingValues(5.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier
                    .padding(start = 38.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
                    .fillMaxWidth()

                ) {
                    Text(
                        product.getName(),
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        product.getGrams().toString() + " г",
                        color = colorResource(id = R.color.appGreen),
                        fontSize = 12.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            product.getCalories().toString(),
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.appVeryLightGrey)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = colorResource(id = R.color.appLightGrey),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GeneralInfoItem(
                    product.getFats(),
                    product.getCarbohydrates(),
                    product.getProteins(),
                    product.getRDNS(1800),
                    colorResource(id = R.color.appVeryLightGrey),
                    30.dp
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = colorResource(id = R.color.appLightBlue),
                )
            }
        }
    }

}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewDiaryView() {
    DiaryView(DiaryViewModel(), navigateToSearchProduct = {title, partDayType -> }, {})

}