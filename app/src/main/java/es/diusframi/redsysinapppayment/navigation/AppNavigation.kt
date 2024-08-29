package es.diusframi.redsystest.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.diusframi.fidelizacion.navigation.AppDestinations
import es.diusframi.redsystest.ui.screens.ErrorScreen
import es.diusframi.redsystest.ui.screens.MainScreen
import es.diusframi.redsystest.ui.screens.SuccessScreen
import es.diusframi.redsysinapppayment.viewmodel.RedSysTestViewModel

/**
 * Compose Navigation composable function. it handles in app navigation.
 *
 * @param navHostController an [NavHostController] instance providing access to navigation functions.
 * @param viewModel an instance of [RedSysTestViewModel] with UI/UX logic and UI hoisted state.
 */
@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navHostController: NavHostController,
    viewModel: RedSysTestViewModel? = null,
){
    val context = LocalContext.current
    //Console logs on navigation status.
    LaunchedEffect(
        key1 = navHostController.currentDestination?.route,
        key2 = viewModel?.uiState
    ) {
        //Writing to console logs navigation backstack
        Log.d("LOYALGO_BACKSTACK", "================================")
        Log.d("LOYALGO_BACKSTACK", "==> Current navigation backstack")
        Log.d("LOYALGO_BACKSTACK", "--------------------------------")
        navHostController.currentBackStack.value.forEachIndexed { index, entry ->
            Log.d("LOYALGO_BACKSTACK", "==> $index - ${entry.destination.route}")
        }
        Log.d("LOYALGO_BACKSTACK", "================================")
        Log.d("LOYALGO_BACKSTACK", "")
        Log.d("LOYALGO_BACKSTACK", "")
    }

    NavHost(
        navController=navHostController,
        startDestination= AppDestinations.MainScreen.route,//AppDestinations.ComerciaMainScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
    ) {
        composable(AppDestinations.MainScreen.route){
            MainScreen(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        composable(AppDestinations.SuccessScreen.route){
            SuccessScreen(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        composable(AppDestinations.ErrorScreen.route){
            ErrorScreen(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
    }
}
