package com.yourfinances.gbank.ui.screens.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Purple
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun StatisticsScreen(
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Balance(Modifier.fillMaxWidth(), 8200.0)

                Income(Modifier.fillMaxWidth())

                Expense(Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun Expense(modifier: Modifier = Modifier) {
    DarkBlueCard(modifier = modifier, radius = 16.dp) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Teal,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Box(
                    Modifier
                        .size(60.dp)
                        .background(Purple, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .rotate(-90f)
                            .size(36.dp)
                    )
                }

                Column(
                    Modifier
                        .weight(1f)
                        .height(54.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.expense),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    LinearProgressIndicator(
                        progress = .8f,
                        trackColor = Background,
                        color = Purple,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(50))
                            .height(9.dp),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
fun Income(modifier: Modifier = Modifier) {
    DarkBlueCard(modifier = modifier, radius = 16.dp) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Teal,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Box(
                    Modifier
                        .size(60.dp)
                        .background(Teal, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .rotate(90f)
                            .size(36.dp)
                    )
                }

                Column(
                    Modifier
                        .weight(1f)
                        .height(54.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.income),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    LinearProgressIndicator(
                        progress = .5f,
                        trackColor = Background,
                        color = Teal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(50))
                            .height(9.dp),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
fun Balance(modifier: Modifier, balance: Double) {
    DarkBlueCard(modifier = modifier, radius = 16.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp, 12.dp, 48.dp),
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

            LinearProgressIndicator(
                progress = .5f,
                trackColor = Background,
                color = Teal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp, top = 24.dp)
                    .clip(RoundedCornerShape(50))
                    .height(10.dp),
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Preview
@Composable
fun StatisticsScreenPr() {
    GbankTheme {
//        StatisticsScreen()
    }
}