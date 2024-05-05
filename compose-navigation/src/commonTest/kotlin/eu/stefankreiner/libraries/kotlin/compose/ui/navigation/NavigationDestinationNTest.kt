package eu.stefankreiner.libraries.kotlin.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import eu.stefankreiner.libraries.kotlinmp.compose.navigation.TypedNamedNavArgumentValue
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe

@Suppress("unused")
class NavigationDestinationNTest : FreeSpec({

    "compiling with one parameter works the same for NavigationDestination1 and NavigationDestinationN" {
        val testDestination1 = object : NavigationDestination1<Long>() {
            override val navArgument: TypedNamedNavArgument<Long> =
                NavType.LongType.toNamedArgument("itemId")

            @Composable
            override fun content(
                navController: NavController,
                backStackEntry: NavBackStackEntry,
                modifier: Modifier
            ) {
            }
        }
        val itemId = 42L
        val compiledDestinationN = TestDestinationN.compile(
            TestDestinationN.Parameters(
                itemId = itemId
            )
        )
        val compiledDestination1 = testDestination1.compile(itemId)
        compiledDestination1.route.substringAfter("/") shouldBe "42"
        compiledDestinationN.route.substringAfter("/") shouldBe compiledDestination1.route.substringAfter(
            "/"
        )
    }

    "given NavigationDestination1 - when compiling - then gives the value after /" {
        val testDestination1 = object : NavigationDestination1<Long>() {
            override val navArgument = NavType.LongType.toNamedArgument("itemId")

            @Composable
            override fun content(
                navController: NavController,
                backStackEntry: NavBackStackEntry,
                modifier: Modifier
            ) {
            }
        }
        val itemId = 42L
        val compiledDestination1 = testDestination1.compile(itemId)
        val compiledDestination2 = testDestination1.compile(
            listOf(
                TypedNamedNavArgumentValue(testDestination1.navArgument, itemId)
            )
        )
        testDestination1.navArgument.shouldBeIn(testDestination1.arguments)
        compiledDestination1.route shouldBe compiledDestination2.route
        compiledDestination1.route.substringAfter("/") shouldBe "42"
    }
})

