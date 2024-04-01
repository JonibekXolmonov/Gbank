package com.yourfinances.gbank.ui.screens.menu

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.advancedShadow
import com.yourfinances.gbank.ui.screens.component.DarkBlueCard
import com.yourfinances.gbank.ui.screens.destinations.CardsScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.GeneralScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.GraphicScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.RatioScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.StatisticsScreenDestination
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme

@Composable
fun MenuScreen(
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 48.dp, top = 72.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )

            MenuContainer(modifier = Modifier.padding(end = 24.dp), radius = 16.dp) {
                Column(
                    Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MenuButton(modifier = Modifier.fillMaxWidth(), contentId = R.string.cards) {
                        navigator.navigate(CardsScreenDestination)
                    }

                    MenuButton(
                        modifier = Modifier.fillMaxWidth(),
                        contentId = R.string.statistics
                    ) {
                        navigator.navigate(StatisticsScreenDestination)
                    }

                    MenuButton(modifier = Modifier.fillMaxWidth(), contentId = R.string.graphic) {
                        navigator.navigate(GraphicScreenDestination)
                    }

                    MenuButton(
                        modifier = Modifier.fillMaxWidth(),
                        contentId = R.string.general_information
                    ) {
                        navigator.navigate(GeneralScreenDestination)
                    }

                    MenuButton(modifier = Modifier.fillMaxWidth(), contentId = R.string.ratio) {
                        navigator.navigate(RatioScreenDestination)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(
    modifier: Modifier,
    @StringRes contentId: Int,
    onClick: () -> Unit
) {
    DarkBlueCard(
        modifier
            .advancedShadow(offsetY = 6.dp, shadowBlurRadius = 4.dp, cornersRadius = 3.dp),
        radius = 8.dp,
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = contentId),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
    }
}

@Composable
fun MenuContainer(
    modifier: Modifier = Modifier,
    radius: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(radius),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardContainer.copy(.7f)),

        ) {
        content()
    }
}