package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation


interface NavigationGraph : NavigationGraphNode {
    val children: List<NavigationGraphNode>
    fun navigation(
        builder: NavGraphBuilder,
        navController: NavController,
        deepLinks: Map<NavigationDestination, List<NavDeepLink>>,
        modifier: Modifier = Modifier,
    ) {
        builder.navigation(
            route = this.route,
            startDestination = this.children.first().route,
        ) {
            children.forEach {
                when(it) {
                    is NavigationGraph -> it.navigation(
                        builder = this,
                        navController = navController,
                        deepLinks = deepLinks,
                        modifier = modifier,
                    )

                    is NavigationDestination -> it.composable(
                        builder = this,
                        navController = navController,
                        deepLinks = deepLinks[it] ?: listOf(),
                        modifier = modifier,
                    )
                }
            }
        }
    }
}