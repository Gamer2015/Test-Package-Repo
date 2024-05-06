package eu.purplefriends.libraries.kotlin.compose.ui.navigation

sealed interface NavigationGraphNode {
    fun NavigationGraphNode.startDestination(visitedNodes: List<NavigationGraphNode> = listOf()): String {
        if(this in visitedNodes) {
            throw CircularRecursionException(visitedNodes + this)
        }
        return when(this) {
            is NavigationGraph -> children.first().startDestination(visitedNodes + this)
            is NavigationDestination -> route
        }
    }

    val route: String
        get() = this.toString()
}

class CircularRecursionException(visitedNodes: List<NavigationGraphNode>): Exception(
    "[${visitedNodes.joinToString(" -> ") { it.route }}]"
)

