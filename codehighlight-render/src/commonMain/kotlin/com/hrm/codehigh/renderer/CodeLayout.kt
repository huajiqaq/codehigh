package com.hrm.codehigh.renderer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Geometry only. Both native rendering and embedding measurements consume these values. */
@Immutable
data class CodeLayout(
    val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    val shape: Shape = RectangleShape,
    val lineNumberWidth: Dp = 40.dp,
    val lineNumberPadding: Dp = 8.dp,
    val lineNumberDividerWidth: Dp = 1.dp,
    val inlineContentPadding: PaddingValues = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
    val inlineShape: Shape = RoundedCornerShape(4.dp),
    val inlineBorderWidth: Dp = 1.dp,
)
