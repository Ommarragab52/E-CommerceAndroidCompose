package com.example.jet_ecommerce.ui.features.main.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.jet_ecommerce.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold {
        Column(Modifier.fillMaxSize()) {
            Image(modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}