package com.example.jet_ecommerce.ui.navigation_comp.bottomNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jet_ecommerce.ui.features.auth.login.LoginScreen
import com.example.jet_ecommerce.ui.features.main.carts.CartScreen
import com.example.jet_ecommerce.ui.features.main.carts.CartViewModel
import com.example.jet_ecommerce.ui.features.main.categories.CategoriesScreen
import com.example.jet_ecommerce.ui.features.main.categories.CategoriesViewModel
import com.example.jet_ecommerce.ui.features.main.home.HomeScreen
import com.example.jet_ecommerce.ui.features.main.home.HomeViewModel
import com.example.jet_ecommerce.ui.features.main.products.ProductsListScreen
import com.example.jet_ecommerce.ui.features.main.products.ProductsViewModel
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsScreen
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsViewModel
import com.example.jet_ecommerce.ui.features.main.profile.ProfileScreen
import com.example.jet_ecommerce.ui.features.main.wishlist.WishListScreen
import com.example.jet_ecommerce.ui.navigation_comp.screensNav.Graph
import com.example.jet_ecommerce.ui.navigation_comp.screensNav.detailsNavGraph

@Composable
fun MainGraph(navHostController: NavHostController) {
        NavHost(
            navController = navHostController,
            route = Graph.MAIN,
            startDestination = BottomNavItem.Home.screen_route
        ) {

            composable(BottomNavItem.Home.screen_route) {

                HomeScreen(navController = navHostController)
            }
            composable(BottomNavItem.Categories.screen_route) {
                val vm: CategoriesViewModel = hiltViewModel()
                CategoriesScreen(vm, navController = navHostController)
            }

            composable(BottomNavItem.WishList.screen_route) {
                val cartViewModel = hiltViewModel<CartViewModel>()
                val viewModel = it.sharedViewModel<HomeViewModel>(navController = navHostController)
                WishListScreen(navController = navHostController,cartViewModel, homeViewModel =  viewModel)
            }

            composable(BottomNavItem.Profile.screen_route) {

                ProfileScreen(navController = navHostController)
            }
            detailsNavGraph(navHostController)
        }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController) : T{
    val  navGraphRoute = destination.parent?.route ?: return  hiltViewModel()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return  hiltViewModel(parentEntry)
}