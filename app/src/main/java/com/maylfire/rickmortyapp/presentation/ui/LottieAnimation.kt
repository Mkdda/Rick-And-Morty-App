package com.maylfire.rickmortyapp.presentation.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.airbnb.lottie.compose.*

private const val defaultSpeed = 1.0f
const val ID_LOTTIE_WRAPPER = "lottie_wrapper"
const val ID_LOTTIE = "lottie"

@Composable
fun RandMortyLottieAnimation(
    modifier: Modifier = Modifier,
    @RawRes lottieResource: Int
) {

    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieResource)
    )

    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = defaultSpeed
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(ID_LOTTIE_WRAPPER),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            modifier = Modifier.testTag(ID_LOTTIE),
            composition = compositionResult.value,
            progress = progress
        )
    }
}