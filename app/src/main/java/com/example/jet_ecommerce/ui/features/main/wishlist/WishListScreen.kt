package com.example.jet_ecommerce.ui.features.main.wishlist

import ErrorToast
import SuccessToast
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.data.api.AppSharedReferences
import com.example.domain.features.cart.model.getLoggedUse.CartQuantity
import com.example.domain.features.products.model.Product
import com.example.jet_ecommerce.ui.components.CustomLoadingWidget
import com.example.jet_ecommerce.ui.components.wish_list_component.WishListItem
import com.example.jet_ecommerce.ui.features.auth.TokenViewModel
import com.example.jet_ecommerce.ui.features.main.carts.CartContract
import com.example.jet_ecommerce.ui.features.main.carts.CartViewModel
import com.example.jet_ecommerce.ui.features.main.carts.ListOfCartProducts
import com.example.jet_ecommerce.ui.features.main.home.HomeViewModel
import com.example.jet_ecommerce.ui.features.main.home.ProductContract
import com.example.jet_ecommerce.ui.features.main.home.RenderCustomTopBar
import com.example.jet_ecommerce.ui.features.main.products.ProductsContract
import com.example.jet_ecommerce.ui.features.main.products.ProductsViewModel
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsContract
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsViewModel
import com.example.jet_ecommerce.ui.navigation_comp.screensNav.ECommerceScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


@Composable
fun WishListScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    wishListViewModel: WishListViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    cartViewModel.invokeAction(CartContract.Action.GetUserProducts)
    wishListViewModel.invokeAction(WishListContract.Action.GetWishListProducts)
    homeViewModel.invokeProductsAction(ProductContract.Action.LoadProducts)

    Column(modifier = Modifier.fillMaxSize()) {
        RenderCustomTopBar(
            cartViewModel = cartViewModel,
            tokenViewModel = tokenViewModel,
            navController = navController,
            isMainScreen = false
        )


        RenderWishListStates(viewModel = wishListViewModel)
    }


}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RenderWishListStates(
    viewModel: WishListViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),

    ) {
    val productDetailsEvents =
        produceState<ProductDetailsContract.ProductDetailsEvents>(initialValue = ProductDetailsContract.ProductDetailsEvents.Idle) {
            productDetailsViewModel.eventsFlow.collect {
                value = it
            }
        }.value

    val states = produceState<WishListContract.State>(initialValue = WishListContract.State.Idle) {
        viewModel.states.collect {
            value = it
        }
    }.value

    val cartStates = produceState<CartContract.State>(initialValue = CartContract.State.Idle) {
        cartViewModel.states.collect {
            value = it
        }
    }.value
    val products = mutableStateOf<CartQuantity>(CartQuantity())
    when (cartStates) {
        is CartContract.State.Error -> {}
        CartContract.State.Idle -> {}
        CartContract.State.Loading -> {}
        is CartContract.State.Success -> {
            products.value = cartStates.data
        }
    }
    val scope = rememberCoroutineScope()
    when (states) {
        is WishListContract.State.Error -> {
            ErrorToast(message = states.message)
        }

        is WishListContract.State.Idle -> {
        }

        is WishListContract.State.Loading -> CustomLoadingWidget()
        is WishListContract.State.Success -> {
            val wishListProduct = states.productData

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp)
            ) {
                items(wishListProduct.size) { index ->
                    WishListItem(isNewToWishList = states.productData.isNotEmpty(),
                        imgUrl = wishListProduct[index].imageCover ?: "",
                        productName = wishListProduct[index].title ?: "",
                        productPrice = wishListProduct[index].price ?: 0,
                        onRemoveFromWishLis = {
                                viewModel.invokeAction(
                                    WishListContract.Action.RemoveProductFomWishList(
                                        wishListProduct[index].id ?: ""
                                    )
                                )
                                 scope.launch {
                                     delay(3000)
                                     viewModel.invokeAction(WishListContract.Action.GetWishListProducts)
                                 }
                        }) {
                        productDetailsViewModel.invokeAction(
                            ProductDetailsContract.Action.AddProductToCart(
                                wishListProduct[index].id ?: "",
                                1
                            )
                        )
                    }
                }
            }
        }
    }

    when (productDetailsEvents) {
        is ProductDetailsContract.ProductDetailsEvents.Idle -> {}
        is ProductDetailsContract.ProductDetailsEvents.NavigateToCart -> {
        }

        is ProductDetailsContract.ProductDetailsEvents.ShowError -> {
            ErrorToast(message = "Something Went Wrong! Could Not Add it.")
        }

        is ProductDetailsContract.ProductDetailsEvents.ShowSuccess -> {
            SuccessToast(message = "Product Added Successfully To Cart!")
        }

        else -> {}
    }
}
