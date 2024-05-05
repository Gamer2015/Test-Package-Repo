package eu.purplefriends.libraries.kotlin.compose.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument


// used for creating typed destinations
class TypedNamedNavArgument<T>(
    name: String,
    private val navType: NavType<T>,
    private val builder: NavArgumentBuilder.() -> Unit = {},
) {
    private val _argument: NamedNavArgument = navArgument(name) {
        this.builder()
        type = navType
    }

    val instance: NamedNavArgument
        get() = _argument

    /**
     * The name the argument is associated with
     */
    val name: String
        get() = instance.name

    /**
     * The [NavArgument] associated with the name
     */
    val argument: NavArgument
        get() = instance.argument
}


fun <T> NavType<T>.toNamedArgument(
    name: String,
    builder: NavArgumentBuilder.() -> Unit = {},
): TypedNamedNavArgument<T> = TypedNamedNavArgument(name, this, builder)
