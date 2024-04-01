@file:OptIn(ExperimentalMaterial3Api::class)

package com.yourfinances.gbank.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal

@Composable
fun BackgroundWithTop(
    navigator: DestinationsNavigator? = null,
    onBackPressed: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(start = 12.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
    ) {
        BackButton(onClick = {
            if (navigator != null) navigator.navigateUp() else onBackPressed()
        })
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 36.dp)
        ) {
            Text(
                text = stringResource(R.string.banking_app),
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onMenuClick)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun BackgroundWithTopWithoutPadding(
    navigator: DestinationsNavigator,
    onMenuClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(top = 12.dp, bottom = 24.dp),
    ) {
        BackButton(Modifier.padding(start = 12.dp), onClick = navigator::navigateUp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 36.dp, end = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.banking_app),
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onMenuClick)
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        modifier = modifier
            .size(136.dp, 40.dp)
            .background(CardContainer, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.back),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun Card(cardInfo: CardModel, onClick: (CardModel) -> Unit = {}) {
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(260.dp, 140.dp),
        colors = CardDefaults.cardColors(containerColor = Teal),
        onClick = {
            onClick(cardInfo)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(text = cardInfo.bankName, style = MaterialTheme.typography.labelSmall)
            Text(
                text = cardInfo.cardNumber,
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = (4.5).sp),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = cardInfo.holderName, style = MaterialTheme.typography.labelSmall)
                Image(
                    painter = painterResource(id = cardInfo.cardIcon),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp, 24.dp)
                )
            }
        }
    }
}

@Composable
fun DarkBlueCard(
    modifier: Modifier = Modifier,
    radius: Dp,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(radius),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardContainer),
        onClick = onClick
    ) {
        content()
    }
}