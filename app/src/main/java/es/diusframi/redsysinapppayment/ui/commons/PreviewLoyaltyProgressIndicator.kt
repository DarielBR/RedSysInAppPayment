package es.diusframi.redsystest.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.diusframi.redsysinapppayment.ui.theme.RedSysInAppPaymentTheme

@Preview(name = "DarkMode", showBackground = false, uiMode = 32)
@Composable
fun PreviewLoyaltyProgressIndicator(){
    RedSysInAppPaymentTheme{ LoyaltyProgressIndicator() }
}

/**
 * A composable function that displays a custom loading indicator using bouncing circles.
 * The indicator is displayed on top of a semi-transparent surface, typically used to block user interaction during loading.
 */
@Composable
fun LoyaltyProgressIndicator(
    //modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        modifier =  Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ElementBouncer(
                listOfElements = listOf(
                    {modifier -> Circle(modifier = modifier, size = 15.dp) },
                    {modifier -> Circle(modifier = modifier, size = 15.dp) },
                    {modifier -> Circle(modifier = modifier, size = 15.dp) }
                ),
                delay = 150,
                yOffset = 30f
            )
        }
    }
}