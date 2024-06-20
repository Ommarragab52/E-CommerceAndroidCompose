package com.example.jet_ecommerce.ui.features.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerGridItem() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .shimmer(shimmerInstance)
    ) {
        // Image placeholder
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text placeholders
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun ShimmerGrid() {
    Column {
        repeat(3) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                repeat(2) {
                    ShimmerGridItem()
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
