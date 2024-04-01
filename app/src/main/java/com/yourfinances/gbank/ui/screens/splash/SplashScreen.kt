package com.yourfinances.gbank.ui.screens.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.ui.screens.destinations.AuthScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.CardsScreenDestination
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal
import com.yourfinances.gbank.ui.theme.Track

@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth(unbounded = true)
                .padding(bottom = 100.dp)
        ) {
            Text(
                text = stringResource(R.string.banking_app),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 120.dp)
            )
            Text(
                text = stringResource(R.string.g_bank),
                style = MaterialTheme.typography.displayLarge
            )
            MyIndicator(
                modifier = Modifier
                    .padding(top = 36.dp)
                    .clip(RoundedCornerShape(50))
                    .height(3.dp)
                    .width(196.dp),
                onFinish = {
                    navigator.navigate(AuthScreenDestination)
                })
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun MyIndicator(modifier: Modifier, onFinish: () -> Unit) {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    val currentPercentage by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(durationMillis = 5000, easing = LinearEasing),
        label = "progress",
        finishedListener = {
            onFinish()
        }
    )

    LaunchedEffect(key1 = Unit) {
        currentProgress = 1f
    }

    LinearProgressIndicator(
        modifier = modifier,
        progress = currentPercentage,
        color = Teal,
        trackColor = Track,
        strokeCap = StrokeCap.Round
    )
}