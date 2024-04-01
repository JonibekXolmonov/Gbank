package com.yourfinances.gbank.ui.screens.general

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Purple
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun GeneralScreen(
    navigator: DestinationsNavigator
) {
    var isMenu by remember {
        mutableStateOf(false)
    }

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IncomeGeneral()
                    ExpenseGeneral()
                }

                LinearProgressIndicator(
                    progress = .8f,
                    trackColor = CardContainer,
                    color = Teal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 84.dp)
                        .clip(RoundedCornerShape(50))
                        .height(9.dp),
                    strokeCap = StrokeCap.Round
                )

                LinearProgressIndicator(
                    progress = .6f,
                    trackColor = CardContainer,
                    color = Purple,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(50))
                        .height(10.dp),
                    strokeCap = StrokeCap.Round
                )

                Text(
                    text = stringResource(R.string.final_balance),
                    style = MaterialTheme.typography.titleMedium.copy(color = Teal),
                    modifier = Modifier.padding(top = 84.dp)
                )

                Text(
                    text = "$ 5290",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun ExpenseGeneral() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = .9f,
                modifier = Modifier.fillMaxSize(),
                trackColor = Background,
                color = Purple,
                strokeCap = StrokeCap.Round,
                strokeWidth = 8.dp
            )

            Box(
                Modifier
                    .size(105.dp)
                    .background(CardContainer, CircleShape)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .rotate(-90f)
                        .size(36.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Text(
            text = "- $ 570+",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun IncomeGeneral() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = .9f,
                modifier = Modifier.fillMaxSize(),
                trackColor = Background,
                color = Teal,
                strokeCap = StrokeCap.Round,
                strokeWidth = 8.dp
            )

            Box(
                Modifier
                    .size(105.dp)
                    .background(CardContainer, CircleShape)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .rotate(90f)
                        .size(36.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Text(
            text = "$ 1200",
            style = MaterialTheme.typography.labelLarge
        )
    }
}