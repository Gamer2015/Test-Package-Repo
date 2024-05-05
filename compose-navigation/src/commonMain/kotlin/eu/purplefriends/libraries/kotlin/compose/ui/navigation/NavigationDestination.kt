package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.stefankreiner.libraries.kotlinmp.compose.navigation.TypedNamedNavArgumentValue

abstract class NavigationDestination : NavigationGraphNode {
    override val route: String
        get() = this.compile(listOf()).route

    internal open val arguments: List<TypedNamedNavArgument<*>>
        get() = listOf()

    private val optionalArguments: List<TypedNamedNavArgument<*>>
        get() = arguments.filter {
            it.argument.isNullable
        }

    private val requiredArguments: List<TypedNamedNavArgument<*>>
        get() = arguments.filter {
            it.argument.isNullable == false
        }

    fun compile(parameters: List<TypedNamedNavArgumentValue<*>>): CompiledNavigationDestination {
        val requiredPath = if (requiredArguments.isEmpty()) "" else "/${
            requiredArguments.joinToString("/") { argument ->
                parameters.firstOrNull { it.navType == argument }?.value?.toString() 
                    ?: argument.instance.encoded
            }
        }"
        val optionalQuery = if (optionalArguments.isEmpty()) "" else "?${
            optionalArguments.mapNotNull { argument ->
                parameters.firstOrNull { it.navType == argument }?.value?.let {
                    "${argument.name}=$it"
                }
            }.joinToString("&")
        }"
        return CompiledNavigationDestination(
            "${super.route}$requiredPath$optionalQuery"
        )
    }

    fun composable(
        builder: NavGraphBuilder,
        navController: NavController,
        deepLinks: List<NavDeepLink>,
        modifier: Modifier = Modifier,
    ) {
        builder.composable(
            route = this.route,
            arguments = this.arguments.map {
                it.instance
            },
            deepLinks = deepLinks,
        ) {
            content(
                navController = navController,
                backStackEntry = it,
                modifier = modifier
            )
        }
    }

    @Composable
    abstract fun content(
        navController: NavController,
        backStackEntry: NavBackStackEntry,
        modifier: Modifier,
    )
}

private val NamedNavArgument.encoded: String
    get() = "{${name}}"