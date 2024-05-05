package eu.purplefriends.libraries.kotlin.compose.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import eu.purplefriends.libraries.kotlin.compose.ui.navigation.NavigationDestination
import eu.purplefriends.libraries.kotlin.compose.ui.navigation.NavigationGraph
import eu.purplefriends.libraries.kotlin.compose.ui.navigation.NavigationGraphNode

@Composable
fun BottomNavigationBar(
    navController: NavController,
    startDestination: String,
    navigationDestinations: List<Pair<String, NavigationItemData>>,
) {
    // Get current back stack entry
    val navigationStack by navController.currentBackStack.collectAsState()
    val currentRoute = navigationStack.lastOrNull()?.destination?.route ?: startDestination
    val selectedNavigationItemDestination = navigationStack.lastOrNull {
        navigationDestinations.map {
            it.first
        }.contains(it.destination.route)
    }?.destination?.route ?: startDestination

    NavigationBar {
        navigationDestinations.forEach {
            val itemDestination = it.first
            val isDestination = itemDestination == selectedNavigationItemDestination
            this.NavigationBarItem(
                selected = isDestination,
                icon = {
                    Icon(
                        imageVector = it.second.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(it.second.label)
                },
                onClick = {
                    if (isDestination == false) {
                        navController.navigate(
                            route = it.first,
                        ) {
                            restoreState = true
                            popUpTo(
                                route = selectedNavigationItemDestination,
                            ) {
                                inclusive = true
                                saveState = true
                            }
                        }
                    } else if (currentRoute != it.second.startDestination) {
                        navController.navigate(
                            route = it.first,
                        ) {
                            restoreState = true
                            launchSingleTop = true
                            popUpTo(
                                route = selectedNavigationItemDestination,
                            ) {
                                inclusive = false
                                saveState = false
                            }
                        }
                    }
                }
            )
        }
    }
}

data class NavigationItemData(
    val label: String,
    val icon: ImageVector,
    val startDestination: String,
)

fun NavigationGraphNode.startDestination(visitedNodes: Set<NavigationGraphNode> = setOf()): String {
    if(this in visitedNodes) {
        throw Exception("Recursive accessor")
    }
    return when(this) {
        is NavigationGraph -> children.first().startDestination(visitedNodes + this)
        is NavigationDestination -> route
    }
}
