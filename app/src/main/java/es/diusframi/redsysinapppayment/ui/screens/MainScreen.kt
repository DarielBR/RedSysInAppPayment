package es.diusframi.redsystest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import es.diusframi.fidelizacion.navigation.AppDestinations
import es.diusframi.redsysinapppayment.R
import es.diusframi.redsysinapppayment.ui.commons.HideSystemBars
import es.diusframi.redsysinapppayment.ui.theme.RedSysInAppPaymentTheme
import es.diusframi.redsysinapppayment.viewmodel.RedSysTestViewModel

@Preview
@Composable
fun PreviewMainScreen(){
    RedSysInAppPaymentTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: RedSysTestViewModel? = null,
    navHostController: NavHostController? = null
) {
    /*region Hiding Android System Bars*/
    val context = LocalContext.current
    val view = LocalView.current
    HideSystemBars(
        context = context,
        view = view,
        desiredBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT,
        avoidDefaultOnNonPhysicalDevices = true
    )
    /*endregion*/

    /*region UX Navigation*/
    LaunchedEffect(
        key1 = viewModel?.uiState,
        key2 = viewModel?.errorState,
    ) {
        if (viewModel?.uiState?.isLoading == true){
            if (viewModel.errorState.onError()) {
                navHostController?.navigate(AppDestinations.ErrorScreen.route)
                viewModel.setLoadingState(false)
            } else if (viewModel.uiState.resultResponse != null) {
                navHostController?.navigate(AppDestinations.SuccessScreen.route) {
                    popUpTo(AppDestinations.MainScreen.route) { inclusive = true }
                }
                viewModel.setLoadingState(false)
            }
        }
    }
    /*endregion*/

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Button(
                onClick = { viewModel?.doRedSysPayment(10.0, context) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_vector_redsys),
                    contentDescription = "",
                    modifier = modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
        }
    }
}