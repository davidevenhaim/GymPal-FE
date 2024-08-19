package com.example.gympal2.core.ui.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationView(animation: Int, modifier: Modifier) {
    // Load the Lottie animation composition
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))
//
//    // You can control the animation progress with this state
    val progress by animateLottieCompositionAsState(composition)
//
    LottieAnimation(
        composition,
        progress = { progress },
        modifier = modifier
    )
}