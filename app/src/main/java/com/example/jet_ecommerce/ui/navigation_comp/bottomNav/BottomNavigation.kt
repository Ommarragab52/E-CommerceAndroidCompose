package com.example.jet_ecommerce.ui.navigation_comp.bottomNav

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jet_ecommerce.R
import com.example.jet_ecommerce.ui.components.SelectedNavIcon

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Categories,
        BottomNavItem.WishList,
        BottomNavItem.Profile,
    )

    BottomNavigation(
        modifier = Modifier.clip(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            )
        ),
        backgroundColor = colorResource(id = R.color.main_color),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.screen_route
            BottomNavigationItem(
                icon = {
                    if (selected) {
                        SelectedNavIcon(item.icon, item.title)
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            tint = Color.White,
                            contentDescription = item.title
                        )
                    }
                },
                selectedContentColor = Color.White,
                unselectedContentColor = colorResource(id = R.color.main_color),
                alwaysShowLabel = true,
                selected = selected,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.navigate(item.screen_route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
