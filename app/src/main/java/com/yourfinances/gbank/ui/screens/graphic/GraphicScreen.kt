package com.yourfinances.gbank.ui.screens.graphic

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.ui.screens.card.ActionButton
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTopWithoutPadding
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.screens.ratio.BalanceWithoutProgress
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun GraphicScreen(
    navigator: DestinationsNavigator
) {
    var isMenu by remember {
        mutableStateOf(false)
    }


    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTopWithoutPadding(
            navigator,
            onMenuClick = { isMenu = true }) {
            GraphicContent()
        }
    }
}

@Composable
fun GraphicContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        BalanceWithoutProgress(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp), balance = 8200.85f
        )

        ActionButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 48.dp),
            textId = R.string.confirm
        ) {

        }

        Graph(
            Modifier
                .fillMaxWidth()
        )

        Switches(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
    }
}

@Composable
fun Graph(
    modifier: Modifier,
    ) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "982.95",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 48.dp)
        )

        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.graphic), contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(bottom = 80.dp),
                contentScale = ContentScale.FillWidth
            )

            Pointer(
                modifier = Modifier
                    .padding(end = 54.dp)
                    .height(180.dp)
            )
        }
    }
}

@Composable
fun Pointer(modifier: Modifier) {
    Row(
        modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "102040506070",
            style = MaterialTheme.typography.bodyMedium.copy(Color.White),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp, 10.dp)
                    .background(Teal, RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
            )
            Divider(
                Modifier
                    .width(2.dp)
                    .weight(1f),
                color = Teal
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Teal, CircleShape)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun Switches(
    value1: Boolean = true,
    value2: Boolean = false,
    modifier: Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppSwitch(
            value = value1,
            onCheckedChange = {}
        )
        AppSwitch(
            value = value2,
            onCheckedChange = {}
        )
    }
}

@Composable
fun AppSwitch(value: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = value,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedTrackColor = CardContainer,
            uncheckedTrackColor = Teal,
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Background,
            checkedBorderColor = Color.Transparent,
            uncheckedBorderColor = Color.Transparent
        )
    )
}