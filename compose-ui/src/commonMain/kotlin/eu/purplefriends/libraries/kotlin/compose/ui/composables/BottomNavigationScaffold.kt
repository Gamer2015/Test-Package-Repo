package eu.purplefriends.libraries.kotlin.compose.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import eu.purplefriends.libraries.kotlin.compose.ui.navigation.NavigationDestination
import eu.purplefriends.libraries.kotlin.compose.ui.navigation.NavigationGraph

@Composable
fun BottomNavigationScaffold(
    navigationGraph: NavigationGraph,
    navigationDestinations: List<Pair<String, NavigationItemData>>,
    navController: NavHostController = rememberNavController(),
    deepLinks: Map<NavigationDestination, List<NavDeepLink>> = mapOf(),
    modifier: Modifier = Modifier,
) {
    val startDestination = navigationGraph.children.first().route
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                startDestination = startDestination,
                navigationDestinations = navigationDestinations,
            )
        },
        modifier = modifier,
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            navigationGraph.navigation(
                builder = this,
                navController = navController,
                deepLinks = deepLinks,
            )
        }
    }
}
