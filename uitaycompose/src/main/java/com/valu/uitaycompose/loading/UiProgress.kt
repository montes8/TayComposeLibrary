package com.valu.uitaycompose.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.time.Duration

fun Modifier.uiShowProgress(
    show: Boolean
): Modifier = composed {
    if (show) {
        UiProgressAlter()
    }
    this
}

@Composable
fun UiProgressAlter(
    duration: Int = 600,
    line: Dp = 4.dp,
    size: Dp = 40.dp,
    colorProgress: Color = Color.Magenta,
    bgProgress: Color = Color.Transparent,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f)
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            DualColorFastProgress(
                speedMillis = duration,
                color1 = colorProgress,
                color2 = bgProgress,
                strokeWidth = line,
                size = size
            )
        }
    }
}


@Composable
fun DualColorFastProgress(
    speedMillis: Int = 600,
    color1: Color = Color.Red,
    color2: Color = Color.Blue,
    strokeWidth: Dp = 4.dp,
    size: Dp = 40.dp
) {
    val transition = rememberInfiniteTransition(label = "rotation")

    val rotateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(speedMillis, easing = LinearEasing)
        ),
        label = "angle"
    )

    Canvas(
        modifier = Modifier
            .size(size)
            .rotate(rotateAnim)
    ) {
        val stroke = strokeWidth.toPx()

        drawArc(
            color = color1,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
        drawArc(
            color = color2,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}
