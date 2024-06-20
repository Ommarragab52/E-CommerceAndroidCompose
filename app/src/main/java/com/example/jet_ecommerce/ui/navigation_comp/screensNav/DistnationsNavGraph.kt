package com.example.jet_ecommerce.ui.navigation_comp.screensNav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.jet_ecommerce.ui.features.auth.login.LoginScreen
import com.example.jet_ecommerce.ui.features.main.carts.CartScreen
import com.example.jet_ecommerce.ui.features.main.products.ProductsListScreen
import com.example.jet_ecommerce.ui.features.main.products.ProductsViewModel
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsScreen
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsViewModel

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ECommerceScreens.ProductsScreen.name,
        route = Graph.DETAILS
    ) {
        composable(ECommerceScreens.LoginScreen.name) {
            //  val viewModel = LoginViewModel()
            LoginScreen(navController = navController)
        }

        composable(
            route = "${ECommerceScreens.ProductsScreen.name}/{category_id}",
            arguments = listOf(navArgument("category_id") {
                type = NavType.StringType
            })
        ) {
            val vm: ProductsViewModel = hiltViewModel()
            ProductsListScreen(vm = vm, navController = navController)
        }
        composable(
            route = "${ECommerceScreens.ProductDetailsScreen.name}/{product_id}",
            arguments = listOf(navArgument("product_id") {
                type = NavType.StringType
            }),

            ) {
            val vm: ProductDetailsViewModel = hiltViewModel()

            ProductDetailsScreen(vm = vm, navController = navController)
        }

        composable(ECommerceScreens.CartScreen.name) {
            CartScreen(navController)
        }

    }
}