package com.hrm.codehigh.renderer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlin.math.max

/** Full, uncollapsed code block dimensions in pixels, including its toolbar and content padding. */
@Immutable
data class CodeBlockSize(val height: Float, val lineHeight: Float, val toolbarHeight: Float)

fun measureCodeBlockSize(
    code: String,
    language: String = "",
    title: String = "",
    showCopyButton: Boolean = true,
    typography: CodeTypography = CodeTypography(),
    layout: CodeLayout = CodeLayout(),
    density: Density,
    textMeasurer: TextMeasurer,
): CodeBlockSize {
    fun textHeight(text: String, textStyle: TextStyle): Float = textMeasurer.measure(
        text = text.ifEmpty { " " }, style = textStyle, softWrap = false, maxLines = 1, constraints = Constraints(),
    ).size.height.toFloat()

    val textStyle = typography.resolvedText
    val lineHeight = max(textHeight("Mg", textStyle), textHeight("Mg", textStyle.copy(fontWeight = FontWeight.Bold)))
    val labelsHeight = (if (title.isNotBlank()) textHeight(title, typography.resolvedTitle) + with(density) { 4.dp.toPx() } else 0f) +
        (if (language.isNotBlank()) textHeight(language, typography.resolvedLanguage) + with(density) { 8.dp.toPx() } else 0f) +
        (if (title.isNotBlank() && language.isNotBlank()) with(density) { 2.dp.toPx() } else 0f)
    val actionHeight = if (showCopyButton) textHeight("Copy", typography.resolvedAction) + with(density) { 8.dp.toPx() } else 0f
    val toolbarHeight = max(labelsHeight, actionHeight)
    val padding = with(density) {
        (layout.contentPadding.calculateTopPadding() + layout.contentPadding.calculateBottomPadding()).toPx()
    }
    return CodeBlockSize((code.count { it == '\n' } + 1) * lineHeight + padding + toolbarHeight, lineHeight, toolbarHeight)
}
