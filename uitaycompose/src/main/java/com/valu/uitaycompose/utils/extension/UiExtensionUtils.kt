package com.valu.uitaycompose.utils.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Modifier.uiTayNoRippleClickable(
    onClick: () -> Unit
) = this.then(
    Modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
)


fun uiTayDriveUrl(originalUrl: String): String {
    if (!originalUrl.contains("drive.google.com")) return originalUrl
    val idPattern = "/d/([^/]+)".toRegex()
    val match = idPattern.find(originalUrl)
    val id = match?.groupValues?.get(1)

    return if (id != null) {
        "https://drive.google.com/uc?export=view&id=$id"
    } else {
        originalUrl
    }
}

@Composable
fun Modifier.uiDelayed(
    delayMillis: Long = 1000L,
    onAction: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()

    this.clickable {
        scope.launch {
            delay(delayMillis)
            onAction()
        }
    }
}