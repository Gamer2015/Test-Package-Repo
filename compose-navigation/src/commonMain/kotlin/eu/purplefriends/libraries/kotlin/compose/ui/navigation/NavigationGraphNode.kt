package eu.purplefriends.libraries.kotlin.compose.ui.navigation

sealed interface NavigationGraphNode {
    val route: String
        get() = this.toString()
}

