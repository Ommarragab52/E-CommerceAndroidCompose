package com.example.jet_ecommerce.ui.navigation_comp.screensNav
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.jet_ecommerce.ui.features.auth.login.LoginScreen
import com.example.jet_ecommerce.ui.features.main.MainScreen
import com.example.jet_ecommerce.ui.features.auth.register.RegisterScreen
import com.example.jet_ecommerce.ui.features.main.carts.CartScreen
import com.example.jet_ecommerce.ui.features.main.home.HomeViewModel
import com.example.jet_ecommerce.ui.features.main.products.ProductsListScreen
import com.example.jet_ecommerce.ui.features.main.products.ProductsViewModel
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsScreen
import com.example.jet_ecommerce.ui.features.main.products.productDetails.ProductDetailsViewModel
import com.example.jet_ecommerce.ui.features.splash.SplashScreen
import com.example.jet_ecommerce.ui.navigation_comp.bottomNav.sharedViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
navigation(
     route= Graph.AUTH,
    startDestination = ECommerceScreens.SplashScreen.name
){
    composable(ECommerceScreens.SplashScreen.name) {

        SplashScreen(navController = navController)

    }

    composable(ECommerceScreens.LoginScreen.name) {
        //  val viewModel = LoginViewModel()
        LoginScreen(navController = navController)
    }

    composable(ECommerceScreens.RegisterScreen.name) {
        //val homeViewModel = hiltViewModel<HomeScreenViewModel>()
        RegisterScreen(navController = navController)
    }


}
}



object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
    const val BOTTOM_NAV = "bottom_nav_graph"
    const val DETAILS = "details"
}