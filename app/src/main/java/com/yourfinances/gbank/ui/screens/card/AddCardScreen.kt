package com.yourfinances.gbank.ui.screens.card

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.Constant.cardIcons
import com.yourfinances.gbank.data.noRippleClickable
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.creditCardFilter
import com.yourfinances.gbank.data.formatCardNumber
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.Background

@Destination
@Composable
fun AddCardScreen(
    context: Context = LocalContext.current,
    viewModel: AddCardViewModel = viewModel(
        factory = AddCardViewModelFactory(
            context
        )
    ),
    navigator: DestinationsNavigator
) {

    val uiState by viewModel.uiState.collectAsState()
    val card = uiState.cardModel

    var isMenu by remember {
        mutableStateOf(false)
    }

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.add_card),
                    style = MaterialTheme.typography.labelLarge
                )

                CardIcons(card, onCardIconSelect = viewModel::onIconSelected)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    CardField(
                        value = card.bankName,
                        label = stringResource(id = R.string.bank_name),
                        onValueChanged = viewModel::setBankName
                    )
                    CardNumberField(
                        modifier = Modifier.fillMaxWidth(),
                        value = card.cardNumber,
                        label = stringResource(id = R.string.card_number).formatCardNumber(),
                        onValueChanged = viewModel::setCardNumber
                    )
                    CardField(
                        value = card.holderName,
                        label = stringResource(id = R.string.holder_name),
                        onValueChanged = viewModel::setHolderName
                    )
                }

                ActionButton(textId = R.string.add_card) {
                    viewModel.insertCard(card, onSuccess = navigator::navigateUp)
                }
            }
        }
    }
}

@Composable
fun CardIcons(cardModel: CardModel, onCardIconSelect: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardContainer),
        modifier = Modifier.padding(horizontal = 36.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardIcons) {
                Box(
                    modifier = Modifier
                        .size(54.dp, 36.dp)
                        .background(
                            if (it == cardModel.cardIcon) Background else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp, 30.dp)
                            .noRippleClickable {
                                onCardIconSelect(it)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun CardField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    radius: Dp = 4.dp,
    onValueChanged: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .background(CardContainer, RoundedCornerShape(radius))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            if (value.isEmpty())
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                )
            innerTextField.invoke()
        }
    )
}

@Composable
fun CardNumberField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    radius: Dp = 4.dp,
    onValueChanged: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.length <= 16)
                onValueChanged(it)
        },
        modifier = modifier
            .background(CardContainer, RoundedCornerShape(radius))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = (4.5).sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        visualTransformation = { it.creditCardFilter() },
        decorationBox = { innerTextField ->
            if (value.isEmpty())
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        letterSpacing = (4.5).sp
                    )
                )
            innerTextField.invoke()
        }
    )
}