package com.example.engdictionaryapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engdictionaryapp.R

private val SCREEN_BACKGROUND = Color(0xFFF5F3F7)
private val TEXT_COLOR = Color(0xFF24DF68)
private const val TITLE_TEXT_SIZE = 20
private const val BUTTON_TEXT_SIZE = 17
private val BUTTON_SIZE = 155.5.dp to 58.dp
private val PADDING_BOTTOM_TEXT = 16.dp
private val PADDING_BOTTOM_CAPTION = 32.dp
private val PADDING_BOTTOM_BUTTON = 50.dp

private val NUNITO_BOLT =  FontFamily(Font(R.font.nunito_bold, FontWeight.Bold))

@Composable
fun CloseScreen(
    onContinueClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SCREEN_BACKGROUND)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = PADDING_BOTTOM_BUTTON),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TextContent()

            Spacer(modifier = Modifier.weight(1f))

            ContinueButton(
                onClick = onContinueClicked,
                modifier = Modifier.padding(top = PADDING_BOTTOM_CAPTION)
            )
        }
    }
}

@Composable
private fun TextContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.don_t_panic),
            color = TEXT_COLOR,
            fontSize = TITLE_TEXT_SIZE.sp,
            fontFamily = NUNITO_BOLT,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = PADDING_BOTTOM_TEXT)
        )
        Text(
            text = stringResource(R.string.development_by_gusandr).uppercase(),
            fontFamily = NUNITO_BOLT,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(BUTTON_SIZE.first)
            .height(BUTTON_SIZE.second),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.wrongAnswerColor)
        )
    ) {
        Text(
            text = stringResource(R.string.button_continue),
            fontSize = BUTTON_TEXT_SIZE.sp,
            fontFamily = NUNITO_BOLT,
            fontWeight = FontWeight.Bold,
        )
    }
}

//@Preview
//@Composable
//fun CloseScreenPreview() {
//    CloseScreen(onContinueClicked = {})
//}