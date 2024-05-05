package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import eu.stefankreiner.libraries.kotlinmp.compose.navigation.TypedNamedNavArgumentValue


abstract class NavigationDestinationN<Arguments : NavigationDestinationN<Arguments>.NavigationArguments> :
    NavigationDestination() {
    abstract inner class NavigationArguments {
        fun toList(arguments: Arguments): List<TypedNamedNavArgumentValue<*>> =
            argumentDefinitions.map {
                it.getValue(arguments)
            }

        fun toList(): List<TypedNamedNavArgumentValue<*>> = toList(value())

        fun withValues(savedStateHandle: SavedStateHandle): Arguments {
            var arguments = value()
            argumentDefinitions.forEach {
                arguments = it.setValue(arguments, savedStateHandle)
            }
            return arguments
        }

        abstract fun value(): Arguments
    }

    inner class ArgumentDefinition<T>(
        navType: NavType<T>,
        val getter: (Arguments) -> T?,
        val setter: (Arguments, T?) -> Arguments,
        builder: NavArgumentBuilder.() -> Unit = {},
    ) {
        private val name = this.toString()
        val navArgument = navType.toNamedArgument(this.name, builder = builder)

        fun getValue(arguments: Arguments): TypedNamedNavArgumentValue<T> {
            return TypedNamedNavArgumentValue(navArgument, getter(arguments))
        }

        fun setValue(arguments: Arguments, savedStateHandle: SavedStateHandle): Arguments {
            val value = savedStateHandle.get<T>(this.name).let {
                if (this.navArgument.argument.isNullable == false) {
                    checkNotNull(it)
                } else it
            }
            return setter(arguments, value)
        }
    }

    final override val arguments: List<TypedNamedNavArgument<*>>
        get() = argumentDefinitions.map { it.navArgument }

    internal val argumentDefinitions: MutableList<ArgumentDefinition<*>> = mutableListOf()

    protected fun <T> addArgument(
        navType: NavType<T>,
        getter: (Arguments) -> T?,
        setter: (Arguments, T?) -> Arguments,
        builder: NavArgumentBuilder.() -> Unit = {},
    ): ArgumentDefinition<T> {
        val argument = ArgumentDefinition(
            navType = navType,
            getter = getter,
            setter = setter,
            builder = builder,
        )
        argumentDefinitions.add(argument)
        return argument
    }

    fun compile(parameters: Arguments): CompiledNavigationDestination {
        return super.compile(parameters.toList())
    }
}