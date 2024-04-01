package com.yourfinances.gbank.ui.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.History
import com.yourfinances.gbank.data.formatFloat
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.DarkBlueCard
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun HistoryScreen(
    navigator: DestinationsNavigator,
    viewModel: HistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = HistoryViewModelFactory(
            LocalContext.current
        )
    )
) {
    var isMenu by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.uiState.collectAsState()

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(Modifier.padding(top = 64.dp)) {
                AccountAmount(uiState.totalAmount)

                LazyColumn(
                    Modifier.padding(start = 12.dp, end = 12.dp, top = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(uiState.history) {
                        History(history = it)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountAmount(totalAmount: Float) {
    DarkBlueCard(modifier = Modifier.fillMaxWidth(), radius = 0.dp) {
        Column(Modifier.padding(36.dp, 24.dp, 36.dp, 36.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                )
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Teal)
            }

            Text(
                text = stringResource(R.string.account_amount),
                style = MaterialTheme.typography.titleSmall.copy(color = Teal),
                modifier = Modifier.padding(top = 36.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ ${totalAmount.formatFloat()}",
                    style = MaterialTheme.typography.displaySmall.copy(Color.White)
                )
                Column {
                    Text(
                        text = stringResource(R.string.reserve),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
                    )
                    Divider(
                        Modifier
                            .padding(top = 12.dp)
                            .height(1.dp)
                            .width(50.dp),
                        color = Background
                    )
                }
            }
        }
    }
}

@Composable
fun History(modifier: Modifier = Modifier, history: History) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = history.name,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
                )
                Text(
                    text = if (history.isPayment) stringResource(R.string.successful_debit) else stringResource(R.string.successful_payment),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
                )
            }
            Text(
                text = if(history.isPayment) "-$ ${history.amount}" else "+$ ${history.amount}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (history.isPayment) Color.White else Teal
                )
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(
                    if (history.isPayment) 1.dp else 2.dp
                ),
            color = CardContainer
        )
    }
}