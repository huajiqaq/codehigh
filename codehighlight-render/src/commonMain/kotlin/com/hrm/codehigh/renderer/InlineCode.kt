package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/** Shares the exact same defaults and style groups as CodeBlock. */
@Composable
fun InlineCode(
    text: String,
    modifier: Modifier = Modifier,
    colors: CodeColors = CodeDefaults.colors(),
    typography: CodeTypography = CodeDefaults.typography(),
    layout: CodeLayout = CodeDefaults.layout(),
) {
    BasicText(
        text = text,
        style = TextStyle(color = colors.inlineText).merge(typography.resolvedInlineCode),
        modifier = modifier
            .background(colors.inlineBackground, layout.inlineShape)
            .then(if (layout.inlineBorderWidth.value > 0f) {
                Modifier.border(layout.inlineBorderWidth, colors.inlineBorder, layout.inlineShape)
            } else Modifier)
            .padding(layout.inlineContentPadding),
    )
}
