package com.example.deliveryfood.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.deliveryfood.R
import com.example.deliveryfood.models.db.CategoryEntity
import com.example.deliveryfood.models.db.MealEntity
import com.example.deliveryfood.utils.Constants
import com.example.deliveryfood.utils.MyUtils
import com.example.deliveryfood.viewmodels.MenuViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import coil.compose.AsyncImage

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {

    val categoryList by viewModel.categories.observeAsState()
    val mealList by viewModel.mealAllInfo.observeAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        CitiesDropDownMenu()
        categoryList?.let { listCategory ->
            mealList?.let { listMeal ->
                SetScreen(listCategory, listMeal, viewModel)
            }
        }
    }
}

@Composable
fun CitiesDropDownMenu() {
    val citiesList = listOf("London", "Paris", "Berlin")
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(citiesList[0]) }
    Row(modifier = Modifier
        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
        .clickable {
            expanded.value = !expanded.value
        }) {
        Text(
            text = currentValue.value,
            fontSize = 24.sp,
            color = Black,
            fontWeight = FontWeight.Bold
        )
        DropdownMenu(modifier = Modifier
            .background(White),
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }) {
            citiesList.forEach { city ->
                DropdownMenuItem(onClick = {
                    currentValue.value = city
                    expanded.value = false
                }) {
                    Text(
                        text = city,
                        color = Black,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Banners() {
    val bannerList = listOf(
        R.drawable.dodo,
        R.drawable.burger,
        R.drawable.mfood,
        R.drawable.kfc
    )

    val customShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp,
        bottomStart = 16.dp
    )

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 8.dp)
    ) {
        items(bannerList) { banner ->
            Card(
                modifier = Modifier
                    .padding(all = 15.dp)
                    .height(140.dp)
                    .fillParentMaxWidth(0.92f),
                shape = customShape,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(all = 0.dp)
                        .fillMaxSize(),
                    model = banner,
                    contentDescription = "Banner Image",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}
@Composable
fun Categories(categoryList: List<CategoryEntity>, viewModel: MenuViewModel) {
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier
            .background(White)
    ) {
        items(categoryList) { item ->
            Card(
                modifier = Modifier
                    .padding(15.dp)
                    .background(White),
                shape = MaterialTheme.shapes.small,
                elevation = 4.dp
            ) {
                Text(
                    text = item.title,
                    color = Black,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .background(White)
                        .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 10.dp)
                        .clickable {
                            if (MyUtils.isInternetAvailable(context)) {
                                viewModel.getMealByCategory(item.title)
                            }
                        }
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.Gray)
    ) {

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetScreen(
    categoryList: List<CategoryEntity>,
    mealList: List<MealEntity>,
    viewModel: MenuViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        item {
            Banners()
        }

        stickyHeader {
            Categories(categoryList, viewModel)
        }

        items(mealList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(White),
                shape = MaterialTheme.shapes.small,
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .background(White),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        imageModel = item.image,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                        ),
                        modifier = Modifier
                            .weight(1F),
                    )
                    Column(
                        modifier = Modifier
                            .weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.title,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            color = Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 18.sp
                        )
                        Text(
                            text = item.description,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            color = Black,
                            fontSize = 14.sp,
                            maxLines = 10,
                            overflow = TextOverflow.Ellipsis
                        )
                        OutlinedButton(
                            onClick = { viewModel.addClicked(item.title, 1) },
                        ) {
                            Text(
                                text = Constants.ADD_TO_CART,
                                color = Black,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}