package com.example.jet_ecommerce.ui.features.main.carts

import ErrorToast
import SuccessToast
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.domain.features.cart.model.ShoppingAddingRequest
import com.example.domain.features.cart.model.check_out.Session
import com.example.domain.features.cart.model.getLoggedUse.CartQuantity
import com.example.jet_ecommerce.R
import com.example.jet_ecommerce.ui.components.CustomAlertDialog
import com.example.jet_ecommerce.ui.components.CustomLoadingWidget
import com.example.jet_ecommerce.ui.components.cart_component.CartItem
import com.example.jet_ecommerce.ui.components.toasts.InfoToast
import com.example.jet_ecommerce.ui.features.auth.TokenViewModel
import com.example.jet_ecommerce.ui.features.main.check_out.CheckOutViewModel
import com.example.jet_ecommerce.ui.features.main.home.RenderCustomTopBar
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@Composable
fun RenderCartStates(
    viewModel: CartViewModel = hiltViewModel(),
    checkOutViewModel: CheckOutViewModel = hiltViewModel()
) {
    val states = produceState<CartContract.State>(initialValue = CartContract.State.Idle) {
        viewModel.states.collect {
            value = it
        }
    }.value
    val uriHandler = LocalUriHandler.current

    var sessionData: Session? = null

    when (states) {
        is CartContract.State.Error -> {}
        CartContract.State.Idle -> {}
        CartContract.State.Loading -> CustomLoadingWidget()
        is CartContract.State.Success -> {
            val products = states.data
            sessionData = states.checkOutData
            ListOfCartProducts(
                products,
                sessionData = states.checkOutData,
                checkOutViewModel = checkOutViewModel
            )
        }
    }

    val events =
        produceState<CartContract.Event>(initialValue = CartContract.Event.Idle) {
            viewModel.eventsFlow.collect {
                value = it
            }
        }.value

    when (events) {
        is CartContract.Event.Idle -> {}
        is CartContract.Event.ShowError -> {
            ErrorToast(message = "Something Went Wrong! Could Not Remove it.")
        }

        is CartContract.Event.ShowSuccess -> {
            SuccessToast(message = "Product Removed Successfully")

        }

        is CartContract.Event.NavigateToStripe -> {
            uriHandler.openUri(sessionData?.url ?: "")
        }
    }
}


@Composable
fun ListOfCartProducts(
    products: CartQuantity,
    viewModel: CartViewModel = hiltViewModel(), sessionData: Session,
    checkOutViewModel: CheckOutViewModel = hiltViewModel()
) {

    var productCount by remember { mutableIntStateOf(1) }
    var productTotalPrice by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val paymentSheet = rememberPaymentSheet {
        onPaymentSheetResult(it, viewModel = viewModel, products = products)
    }
    var customerConfig by remember {
        mutableStateOf<PaymentSheet.CustomerConfiguration?>(
            null
        )
    }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products.products?.size ?: 0, key = { index -> index }) { index ->
                productCount = products.products?.get(index)?.count ?: 0
                productTotalPrice = products.totalCartPrice ?: 0
                val productId = products.products?.get(index)?.product?.id ?: ""
                CartItem(
                    imgUrl = products.products?.get(index)?.product?.imageCover ?: "",
                    productName = products.products?.get(index)?.product?.title ?: "",
                    productPrice = productTotalPrice,
                    quantityValue = productCount,
                    onDeleteClick = {
                        viewModel.invokeAction(
                            CartContract.Action.DeleteSpecificCartItem(
                                productId = productId,
                                product = products.products?.get(index)
                            )
                        )
                    },
                    onMinusClick = {
                        if (productCount > 1)
                            productCount--
                        viewModel.invokeAction(
                            CartContract.Action.UpdateCartProductQuantity(
                                count = productCount,
                                productId
                            )
                        )
                        productTotalPrice = products.totalCartPrice ?: 0
                    }) {
                    productCount++
                    viewModel.invokeAction(
                        CartContract.Action.UpdateCartProductQuantity(
                            count = productCount,
                            productId
                        )
                    )
                    productTotalPrice = products.totalCartPrice ?: 0
                }
            }
        }

        if ((products.products?.size ?: 0) >= 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(24.dp).padding(bottom = 60.dp),
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
                        text = "$productTotalPrice EGP",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF06004F),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier
                    .width(270.dp)
                    .height(48.dp)
                    .clickable {
                        viewModel.invokeAction(
                            CartContract.Action.CreateCastOrder(
                                products.id ?: "",
                                ShoppingAddingRequest(
                                    details = "details",
                                    phone = "01271561961",
                                    city = "Cairo"
                                )
                            )
                        )

                        viewModel.invokeAction(
                            CartContract.Action.CheckOut(
                                products.id ?: ""
                            )
                        )

                    }) {
                    IconButton(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xFF004182),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                        .padding(start = 32.dp, top = 12.dp, end = 79.dp, bottom = 12.dp),
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                checkOutViewModel.makePayment { clientSecret, customerId, ephemeralKey ->
                                    paymentIntentClientSecret = clientSecret
                                    customerConfig = PaymentSheet.CustomerConfiguration(
                                        customerId ?: "",
                                        ephemeralKey ?: ""
                                    )
                                }
                                presentPaymentSheet(
                                    paymentSheet,
                                    paymentIntentClientSecret!!,
                                    customerConfig!!
                                )
                            }

                        }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Check Out",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 18.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center,
                                )
                            )
                            Spacer(modifier = Modifier.width(18.dp))
                            Icon(
                                modifier = Modifier.height(40.dp),
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = "cart icon",
                                tint = Color.White,
                            )
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    checkOutViewModel: CartViewModel = hiltViewModel()
) {
    viewModel.invokeAction(CartContract.Action.GetUserProducts)

    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {

        RenderCustomTopBar(
            nonDetailsTopBarTitle = "Carts",
            cartViewModel = cartViewModel,
            tokenViewModel = tokenViewModel,
            navController = navController,
            detailsTopBar = false,
            isMainScreen = false, onBackClick = { navController.popBackStack() },
            onLongCartClick = {
                showDialog = true
            }
        )
        if (showDialog) {
            CustomAlertDialog(
                dialogTitle = "Are you Sure To Clear Your Cart?",
                showDialog = showDialog,
                dialogDescription = "clear cart",
                onDismiss = {
                    showDialog = false
                }
            ) {

                viewModel.invokeAction(CartContract.Action.ClearCart)
                showDialog = false
                navController.popBackStack()
            }
        }
        RenderCartStates(viewModel)


    }


}

fun presentPaymentSheet(
    paymentSheet: PaymentSheet,
    paymentIntentClientSecret: String,
    customerConfig: PaymentSheet.CustomerConfiguration
) {
    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "crow",
            customer = customerConfig
        )
    )
}

fun onPaymentSheetResult(
    paymentSheetResult: PaymentSheetResult,
    viewModel: CartViewModel,
    products: CartQuantity
) {

    when (paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            println("Canceled")
        }

        is PaymentSheetResult.Failed -> {
            println("Error: ${paymentSheetResult.error}")
        }

        is PaymentSheetResult.Completed -> {
            println("Completed")
            viewModel.invokeAction(
                CartContract.Action.CreateCastOrder(
                    products.id ?: "",
                    ShoppingAddingRequest(
                        details = "details",
                        phone = "01271561961",
                        city = "Cairo"
                    )
                )
            )
        }
    }
}