package eu.stefankreiner.libraries.kotlin.compose.ui.navigation

import eu.stefankreiner.libraries.kotlinmp.compose.navigation.TypedNamedNavArgumentValue

abstract class NavigationDestination1<ArgumentType> : NavigationDestination() {
    abstract val navArgument: TypedNamedNavArgument<ArgumentType>
    override val arguments: List<TypedNamedNavArgument<*>>
        get() = listOf(navArgument)

    fun compile(parameter: ArgumentType?): CompiledNavigationDestination {
        return super.compile(
            listOf(
                TypedNamedNavArgumentValue(
                    navType = navArgument,
                    value = parameter
                )
            )
        )
    }
}
