package eu.stefankreiner.libraries.kotlin.compose.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.stefankreiner.libraries.kotlin.compose_ui.generated.resources.Res
import eu.stefankreiner.libraries.kotlin.compose_ui.generated.resources.content_description_navigate_up
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavigateUpButton(
    onClick: () -> Unit,
    contentDescription: String = stringResource(NavigateUpButtonConfiguration.contentDescription),
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
            contentDescription = contentDescription
        )
    }
}

object NavigateUpButtonConfiguration {
    @OptIn(ExperimentalResourceApi::class)
    var contentDescription: StringResource = Res.string.content_description_navigate_up
}

