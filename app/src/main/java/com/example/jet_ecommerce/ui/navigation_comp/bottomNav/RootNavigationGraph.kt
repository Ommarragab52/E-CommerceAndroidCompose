package com.example.jet_ecommerce.ui.navigation_comp.bottomNav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jet_ecommerce.ui.features.main.MainScreen
import com.example.jet_ecommerce.ui.navigation_comp.screensNav.Graph
import com.example.jet_ecommerce.ui.navigation_comp.screensNav.authNavGraph

@Composable
fun RootNavigationGraph(navController: NavHostController) {

        NavHost(
            navController = navController,
            route = Graph.ROOT,
            startDestination = Graph.AUTH
        ) {
            authNavGraph(navController)
            composable(Graph.MAIN){
                MainScreen()
            }
        }
}