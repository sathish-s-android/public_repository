package com.google.bottomsheettest.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * State holder for [HandleDraggableBottomSheet].
 *
 * Use [rememberHandleDraggableBottomSheetState] to create and remember an instance.
 */
@Stable
class HandleDraggableBottomSheetState internal constructor(
    internal val sheetHeightPx: Float,
    internal val dismissThresholdFraction: Float,
) {
    internal val offsetY = Animatable(sheetHeightPx)

    /** Current offset of the sheet in pixels. 0 = fully expanded, sheetHeightPx = collapsed. */
    val currentOffsetPx: Float get() = offsetY.value

    /** Whether the sheet is fully expanded (offset ~0). */
    val isExpanded: Boolean get() = offsetY.value <= 1f

    /** Programmatically expand the sheet. */
    suspend fun expand(animationSpec: AnimationSpec<Float> = tween(300)) {
        offsetY.animateTo(0f, animationSpec)
    }

    /** Programmatically collapse (hide) the sheet. */
    suspend fun collapse(animationSpec: AnimationSpec<Float> = tween(250)) {
        offsetY.animateTo(sheetHeightPx, animationSpec)
    }
}

/**
 * Creates and remembers a [HandleDraggableBottomSheetState].
 *
 * @param sheetHeight The total height of the sheet.
 * @param dismissThresholdFraction Fraction (0‒1) of sheet height the user must drag past to dismiss.
 */
@Composable
fun rememberHandleDraggableBottomSheetState(
    sheetHeight: Dp = 520.dp,
    dismissThresholdFraction: Float = 0.4f,
): HandleDraggableBottomSheetState {
    val density = LocalDensity.current
    val sheetHeightPx = with(density) { sheetHeight.toPx() }
    return remember(sheetHeightPx, dismissThresholdFraction) {
        HandleDraggableBottomSheetState(sheetHeightPx, dismissThresholdFraction)
    }
}

/**
 * A bottom sheet that can **only** be dismissed by dragging from a handle zone at the top.
 *
 * Scrollable content inside the sheet (e.g. `LazyColumn`) works independently and never
 * triggers the sheet's drag‑to‑dismiss behavior.
 *
 * @param onDismiss Called when the user drags past the dismiss threshold and the sheet finishes
 *   its collapse animation. The caller should set its visibility flag to `false` here.
 * @param modifier Modifier applied to the full‑screen overlay container.
 * @param state Sheet state created via [rememberHandleDraggableBottomSheetState].
 * @param sheetHeight Total height of the sheet (handle + content).
 * @param handleHeight Height of the draggable handle zone at the top.
 * @param scrimColor Color of the backdrop scrim. Alpha is animated with the sheet position.
 * @param containerColor Background color of the sheet surface.
 * @param shape Shape of the sheet (applied to the top corners).
 * @param shadowElevation Elevation shadow rendered behind the sheet.
 * @param scrimDismissEnabled If `true`, tapping the scrim also dismisses. Default is `false`.
 * @param dragHandle Composable rendered inside the handle zone. Defaults to a small pill indicator.
 * @param content The sheet body, provided in a [ColumnScope] so you can use `Modifier.weight()`.
 */
@Composable
fun HandleDraggableBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    state: HandleDraggableBottomSheetState = rememberHandleDraggableBottomSheetState(),
    sheetHeight: Dp = 520.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
    containerColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    shadowElevation: Dp = 8.dp,
    scrimDismissEnabled: Boolean = false,
    dragHandle: @Composable () -> Unit = { DefaultDragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetHeightPx = state.sheetHeightPx
    val dismissThreshold = sheetHeightPx * state.dismissThresholdFraction

    // Animate in on first composition
    LaunchedEffect(Unit) {
        state.expand()
    }

    // ── Full-screen overlay ──
    Box(modifier = modifier.fillMaxSize()) {

        // Scrim
        val scrimAlpha = scrimColor.alpha * (1f - state.offsetY.value / sheetHeightPx).coerceIn(0f, 1f)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(scrimColor.copy(alpha = scrimAlpha))
                .pointerInput(scrimDismissEnabled) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                            if (scrimDismissEnabled) {
                                scope.launch {
                                    state.collapse()
                                    onDismiss()
                                }
                            }
                            // If not scrimDismissEnabled, the event is consumed (blocks pass-through)
                            // but nothing happens — sheet stays open.
                        }
                    }
                }
        )

        // ── Sheet surface ──
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset { IntOffset(0, state.offsetY.value.roundToInt()) }
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .height(sheetHeight)
//                .shadow(shadowElevation, shape)
                .clip(shape)
                .background(containerColor)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            // ── Handle zone ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onDragEnd = {
                                scope.launch {
                                    if (state.offsetY.value > dismissThreshold) {
                                        state.collapse()
                                        onDismiss()
                                    } else {
                                        state.expand()
                                    }
                                }
                            },
                            onDragCancel = {
                                scope.launch { state.expand() }
                            },
                            onVerticalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newValue = (state.offsetY.value + dragAmount)
                                        .coerceIn(0f, sheetHeightPx)
                                    state.offsetY.snapTo(newValue)
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                dragHandle()
            }

            // ── Caller-provided content ──
            content()
        }
    }
}

/** Default pill-shaped drag indicator. */
@Composable
fun DefaultDragHandle(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    width: Dp = 36.dp,
    height: Dp = 4.dp,
    cornerRadius: Dp = 2.dp,
) {
    Box(
        modifier = modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(cornerRadius))
            .background(color)

    )
}
