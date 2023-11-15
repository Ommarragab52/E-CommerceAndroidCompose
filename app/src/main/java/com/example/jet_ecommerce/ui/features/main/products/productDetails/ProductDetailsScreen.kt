package com.example.jet_ecommerce.ui.features.main.products.productDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.features.products.model.Product
import com.example.jet_ecommerce.R
import com.example.jet_ecommerce.ui.components.CustomAlertDialog
import com.example.jet_ecommerce.ui.components.CustomLoadingWidget
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay


@Composable
fun ProductDetailsScreen(vm: ProductDetailsViewModel, navController: NavHostController) {
    RenderViewState(vm, navController)
}

@Composable
fun RenderViewState(vm: ProductDetailsViewModel, navController: NavHostController) {
    val states by vm.states.collectAsState()
    val events by vm.events.collectAsState()

    when (states) {
        is ProductDetailsContract.States.Loading -> {
            CustomLoadingWidget()
        }

        is ProductDetailsContract.States.Error -> {
            var showDialog by remember { mutableStateOf(true) }
            CustomAlertDialog(showDialog = showDialog,
                dialogDescription = states.toString(),
                onDismiss = { showDialog = false },
                onConfirm = { showDialog = false })
        }

        is ProductDetailsContract.States.Success -> {
            val product = (states as ProductDetailsContract.States.Success).product
            ProductDetailsContent(
                navController = navController,
                product = product,
                onAddToCartClick = {
                    vm.invokeAction(ProductDetailsContract.Action.AddProductToCart(product))
                },
                onAddToWishListClick = {
                    vm.invokeAction(ProductDetailsContract.Action.AddProductToWishList(product))
                })
        }
    }

    when (events) {
        is ProductDetailsContract.Events.Idle -> {}
        is ProductDetailsContract.Events.NavigateToCart -> {
            //Navigate to CartScreen
        }
    }
}

@Composable
fun ProductDetailsContent(
    navController: NavHostController,
    product: Product,
    onAddToWishListClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(navController)
        ProductDetailsItem(
            product = product,
            onAddToWishListClick = { onAddToWishListClick() },
            onAddToCartClick = { onAddToCartClick() }
        )
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailsItem(
    product: Product,
    onAddToWishListClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(shape = RoundedCornerShape(16.dp)) {
            AutoSlidingCarousel(
                itemsCount = product.images?.size ?: 0,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.images?.get(index))
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0x4D004182),
                                shape = RoundedCornerShape(size = 15.dp)
                            )
                            .width(398.dp)
                            .height(300.dp)
                    )
                }
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = product.title ?: "",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF06004F),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "EGP ${product.price}",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF06004F),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x4D004182),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(0.5.dp)
                    .width(102.dp)
                    .height(34.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "${product.sold} Sold",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF06004F),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Star icon"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${product.ratingsAverage} (${product.ratingsQuantity})",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF06004F),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Row(
                Modifier
                    .width(122.dp)
                    .height(42.dp)
                    .background(color = Color(0xFF004182), shape = RoundedCornerShape(size = 20.dp))
                    .padding(start = 16.dp, top = 11.dp, end = 16.dp, bottom = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.__icon__subtract_circle_minus_remove_),
                    contentDescription = "Subtract icon",
                    tint = Color.White
                )
                Text(
                    text = "${product.quantity}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.__icon__plus_circle_),
                    contentDescription = "plus icon",
                    tint = Color.White
                )

            }

        }
        Text(
            text = "Description",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF06004F),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = "${product.description}",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight(400),
                color = Color(0x9906004F),
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total price",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0x9906004F),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    text = "EGP 3,500",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF06004F),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            IconButton(modifier = Modifier
                .width(270.dp)
                .height(48.dp)
                .background(color = Color(0xFF004182), shape = RoundedCornerShape(size = 20.dp))
                .padding(start = 32.dp, top = 12.dp, end = 79.dp, bottom = 12.dp),
                onClick = { 
                    //handle add to cart button
                }) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart__2__),
                        contentDescription = "cart icon",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Add to cart",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000L,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        // you can remove the surface in case you don't want
        // the transparent background
        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }

}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color(R.color.main_color) /* Color.Yellow */,
    unSelectedColor: Color = Color.Gray /* Color.Gray */,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController) {
    TopAppBar(title = {
        Text(
            "Product Details",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, navigationIcon = {
        val lastRoute = navController.previousBackStackEntry?.destination?.route
        IconButton(onClick = {
            if (lastRoute != null) {
                navController.navigate(lastRoute)
            }
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
    }, actions = {
        IconButton(onClick = { /* Handle search icon click */ }) {
            Icon(painterResource(id = R.drawable.__icon__search_), contentDescription = "Search")
        }
        IconButton(onClick = { /* Handle shopping cart icon click */ }) {
            Icon(
                painterResource(id = R.drawable.__icon__shopping_cart_),
                contentDescription = "Cart"
            )
        }
    })


}
