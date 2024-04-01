@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.yourfinances.gbank.ui.screens.card

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.Card
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal
import androidx.compose.ui.platform.LocalContext
import bek.yourfinances.gbank.ui.screens.card.CardsViewModel
import bek.yourfinances.gbank.ui.screens.card.CardsViewModelFactory
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.data.formatFloat
import com.yourfinances.gbank.data.getActivity
import com.yourfinances.gbank.ui.screens.destinations.AddCardScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.DetailsScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.HistoryScreenDestination
import com.yourfinances.gbank.ui.screens.menu.MenuScreen

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CardsScreen(
    context: Context = LocalContext.current,
    viewModel: CardsViewModel = viewModel(
        factory = CardsViewModelFactory(
            context
        )
    ),
    navigator: DestinationsNavigator
) {

    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        uiState.cards.size
    })

    BackHandler {
        context.getActivity()?.finish()
    }

    var isMenu by remember {
        mutableStateOf(false)
    }

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(null, onBackPressed = { context.getActivity()?.finish() }, onMenuClick = {
            isMenu = true
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CardsPager(
                    pagerState,
                    uiState.cards,
                    onClick = { cardModel ->
                        navigator.navigate(DetailsScreenDestination(cardModel))
                    },
                    onSwipe = viewModel::onCardSwipe

                )

                Indicator(pagerState)

                CardBalance(balance = uiState.totalBalance, onClick = {
                    navigator.navigate(HistoryScreenDestination)
                })

                ActionButton(textId = R.string.add_card) {
                    navigator.navigate(AddCardScreenDestination)
                }
            }
        }
    }
}

@Composable
fun CardsPager(
    pagerState: PagerState,
    cards: List<CardModel>,
    onClick: (CardModel) -> Unit,
    onSwipe: (CardModel) -> Unit
) {
    HorizontalPager(state = pagerState) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Card(cardInfo = cards[pagerState.currentPage], onClick = onClick)
            Spacer(modifier = Modifier.weight(1f))
        }
        onSwipe(cards[pagerState.currentPage])
    }
}

@Composable
fun Indicator(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.White else Color(0xFF253D7B)
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

@Composable
fun CardBalance(balance: Float, onClick: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardContainer),
        onClick = onClick
    ) {
        Column(
            Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.cards),
                    style = MaterialTheme.typography.labelMedium
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = stringResource(R.string.total_card_balance),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "$ ${balance.formatFloat()}",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    textId: Int,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Teal),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(textId),
            style = textStyle
        )
    }
}