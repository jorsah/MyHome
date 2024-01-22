package com.example.myhome.ui.uikit

import android.R
import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 300


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    name: String,
    isFavorite: Boolean,
    url: String,
    isRevealed: Boolean,
    cardOffset: Float = 200f,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onEditClicked: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, label = "")

    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset - offsetX else -offsetX },
    )

    Spacer(modifier = Modifier.height(4.dp))

    Box(Modifier.background(Color.White)) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .clickable {
                    onEditClicked()
                },
            painter = painterResource(id = R.drawable.ic_menu_edit),
            contentDescription = "edit"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            colors = cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset((-offsetX - offsetTransition).roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val original = Offset(offsetX, 0f)
                        val summed = original + Offset(x = dragAmount, y = 0f)
                        val newValue = Offset(x = summed.x.coerceIn(-cardOffset, 0f), y = 0f)
                        if (newValue.x <= -10) {
                            onExpand()
                            return@detectHorizontalDragGestures
                        } else if (newValue.x >= 0) {
                            onCollapse()
                            return@detectHorizontalDragGestures
                        }
                        if (change.positionChange() != Offset.Zero) change.consume()
                        offsetX = newValue.x
                    }
                }
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
            content = {
                ItemCard(name = name, isFavorite = isFavorite, imageUrl = url)
            }
        )
    }
}