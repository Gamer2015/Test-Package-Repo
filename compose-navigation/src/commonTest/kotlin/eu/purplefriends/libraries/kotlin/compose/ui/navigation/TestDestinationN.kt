package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType


object TestDestinationN : NavigationDestinationN<TestDestinationN.Parameters>() {
    data class Parameters(
        val itemId: Long? = null,
    ) : NavigationArguments() {
        override fun value() = this
    }

    init {
        addArgument(NavType.LongType, { it.itemId }, { it, value -> it.copy(itemId = value) })
    }

    @Composable
    override fun content(
        navController: NavController,
        backStackEntry: NavBackStackEntry,
        modifier: Modifier
    ) {
    }
}