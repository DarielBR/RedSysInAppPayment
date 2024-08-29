package es.diusframi.fidelizacion.navigation

/**
 * A container for al in-app destinations identifies that will be used with Compose Navigation component.
 */
sealed class AppDestinations(val route: String) {
    data object MainScreen: AppDestinations("main_screen")
    data object SuccessScreen: AppDestinations("error_screen")
    data object ErrorScreen: AppDestinations("error_screen")
}
