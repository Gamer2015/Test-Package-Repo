package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.navOptions

class CompiledNavigationDestination(
    val route: String
) {
    fun openWith(
        navController: NavController,
        builder: NavOptionsBuilder.() -> Unit = {},
    ) {
        this.openWith(
            navController = navController,
            navOptions = navOptions(builder),
        )
    }

    fun openWith(
        navController: NavController,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null,
    ) {
        navController.navigate(
            route = route,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras,
        )
    }
}