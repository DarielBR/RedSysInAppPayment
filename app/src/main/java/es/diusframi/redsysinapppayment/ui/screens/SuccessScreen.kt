package es.diusframi.redsystest.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import es.diusframi.fidelizacion.navigation.AppDestinations
import es.diusframi.redsysinapppayment.ui.theme.RedSysInAppPaymentTheme
import es.diusframi.redsysinapppayment.viewmodel.RedSysTestViewModel

@Preview
@Composable
fun PreviewSuccessScreen(){
    RedSysInAppPaymentTheme {
        SuccessScreen()
    }
}

@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    viewModel: RedSysTestViewModel? = null,
    navHostController: NavHostController? = null
){
    BackHandler {
        navHostController?.popBackStack()
    }
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(modifier = modifier.fillMaxSize()){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color(0xFF88CE40)),
                        contentScale = ContentScale.FillWidth,
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    val resultString = viewModel?.uiState?.resultResponse?.let {
                        with(it){
                            "$responseCode: $transactionType\n$authorisationCode\n$date\n$hour"
                        }
                    } ?: "Successful response"
                    Text(
                        text = viewModel?.uiState?.resultResponse?.responseCode
                            ?: "This is an example of an Generic Error on Screen",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    onClick = {
                        navHostController?.navigate(AppDestinations.MainScreen.route){
                            popUpTo(AppDestinations.ErrorScreen.route){inclusive = true}
                        }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF88CE40),
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Accept",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}