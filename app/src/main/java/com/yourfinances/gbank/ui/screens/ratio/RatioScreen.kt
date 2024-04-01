package com.yourfinances.gbank.ui.screens.ratio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.DarkBlueCard
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Purple
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun RatioScreen(
    navigator: DestinationsNavigator
) {
    var isMenu by remember {
        mutableStateOf(false)
    }


    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(
                verticalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                BalanceWithoutProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    balance = 2700.86f
                )

                Ratio(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp), 1200f, 570f
                )

                TimeInterval(Modifier.fillMaxWidth())

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "- $ 570",
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "+ $ 1200",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Teal
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TimeInterval(modifier: Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.dayweekmonth),
            style = MaterialTheme.typography.bodyMedium.copy(Color.White)
        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            trackColor = CardContainer,
            color = Teal,
            progress = .5f
        )
    }
}

@Composable
fun Ratio(modifier: Modifier = Modifier, income: Float, expense: Float) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(CardContainer, CircleShape)
                .clip(CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(115.dp)
                    .background(Background, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(11.dp)
            ) {
                drawArc(
                    color = Teal,
                    startAngle = -90f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Round)
                )
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(11.dp)
            ) {
                drawArc(
                    color = Purple,
                    startAngle = -140f,
                    sweepAngle = -90f,
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Round)
                )
            }
        }

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Teal, CircleShape)
                        .clip(CircleShape)
                        .size(14.dp)
                )

                Text(
                    text = "+ $ $income",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Teal
                    )
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Purple, CircleShape)
                        .clip(CircleShape)
                        .size(14.dp)
                )

                Text(
                    text = "+ $ $expense",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun BalanceWithoutProgress(modifier: Modifier, balance: Float) {
    DarkBlueCard(modifier = modifier, radius = 16.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp, 12.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.balance),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = stringResource(id = R.string.visa),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Teal,
                    ),
                    modifier = Modifier.padding(end = 48.dp, bottom = 24.dp)
                )


                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Teal)
            }

            Text(
                text = "$ $balance",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}