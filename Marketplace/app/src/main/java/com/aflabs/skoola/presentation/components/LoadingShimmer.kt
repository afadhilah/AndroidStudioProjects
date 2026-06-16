package com.aflabs.skoola.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.15f),
            Color.LightGray.copy(alpha = 0.5f),
        )

        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer"
        )

        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .background(
                brush = shimmerBrush(),
                shape = RoundedCornerShape(cornerRadius)
            )
    )
}

@Composable
fun ProductCardShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(160.dp)
            .padding(8.dp)
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            cornerRadius = 12.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(16.dp),
            cornerRadius = 4.dp
        )
        Spacer(modifier = Modifier.height(6.dp))
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(14.dp),
            cornerRadius = 4.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            ShimmerBox(
                modifier = Modifier
                    .width(60.dp)
                    .height(18.dp),
                cornerRadius = 4.dp
            )
            ShimmerBox(
                modifier = Modifier
                    .size(24.dp),
                cornerRadius = 12.dp
            )
        }
    }
}
