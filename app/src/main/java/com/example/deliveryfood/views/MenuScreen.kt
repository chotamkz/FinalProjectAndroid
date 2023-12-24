package com.example.deliveryfood.views

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
        //CitiesDropDownMenu()
        categoryList?.let { listCategory ->
            mealList?.let { listMeal ->
                SetScreen(listCategory, listMeal, viewModel)
            }
        }
    }
}

/*@Composable
fun CitiesDropDownMenu() {
    val citiesList = listOf("", "", "")
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
}*/

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
            .background(MaterialTheme.colors.background)
            .padding(vertical = 8.dp)
    ) {
        items(categoryList) { item ->
            CategoryCard(item, viewModel, context)
        }
    }

    Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
}

@Composable
fun CategoryCard(item: CategoryEntity, viewModel: MenuViewModel, context: Context) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                if (MyUtils.isInternetAvailable(context)) {
                    viewModel.getMealByCategory(item.title)
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,//depth
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Text(
            text = item.title,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(all = 12.dp)
        )
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
            .background(MaterialTheme.colors.background)
    ) {

        item {
            Banners()
        }

        stickyHeader {
            Categories(categoryList, viewModel)
        }

        items(mealList) { item ->
            MealCard(item, viewModel)
        }
    }
}

@Composable
fun MealCard(meal: MealEntity, viewModel: MenuViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MealImage(meal.image)
            MealInfo(meal.title, meal.description, viewModel)
        }
    }
}

@Composable
fun MealImage(image: String) {
    GlideImage(
        imageModel = image,
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
        ),
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun MealInfo(title: String, description: String, viewModel: MenuViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6,
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
            maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Expand text when clicked
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        AddToCartButton(title, viewModel)
    }
}


@Composable
fun AddToCartButton(title: String, viewModel: MenuViewModel) {
    Button(
        onClick = { viewModel.addClicked(title, 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.error,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = Constants.ADD_TO_CART,
            style = MaterialTheme.typography.button
        )
    }
}



