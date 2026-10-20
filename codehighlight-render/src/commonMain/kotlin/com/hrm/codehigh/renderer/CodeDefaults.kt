package com.hrm.codehigh.renderer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColors = CodeColors()

private val OneDarkProColors = CodeColors(
    background = Color(0xFF282C34),
    text = Color(0xFFABB2BF),
    keyword = Color(0xFFC678DD),
    string = Color(0xFF98C379),
    number = Color(0xFFD19A66),
    comment = Color(0xFF5C6370),
    operator = Color(0xFF56B6C2),
    punctuation = Color(0xFFABB2BF),
    identifier = Color(0xFFABB2BF),
    type = Color(0xFFE5C07B),
    function = Color(0xFF61AFEF),
    variable = Color(0xFFE06C75),
    constant = Color(0xFFD19A66),
    annotation = Color(0xFFE5C07B),
    decorator = Color(0xFFE5C07B),
    builtin = Color(0xFF56B6C2),
    inlineBackground = Color(0xFF30363D),
    inlineBorder = Color(0xFF3D444D),
    highlightedLineBackground = Color(0x22FFFFFF),
    diffAddedLineBackground = Color(0x3340A15F),
    diffRemovedLineBackground = Color(0x33A84C57),
    diffMetaLineBackground = Color(0x224B5563),
    diffAddedMarker = Color(0xFF9BE9A8),
    diffAddedMarkerBackground = Color(0xFF224D35),
    diffRemovedMarker = Color(0xFFFFA8B5),
    diffRemovedMarkerBackground = Color(0xFF5A2730),
    diffMetaMarker = Color(0xFFD1D5DB),
    diffMetaMarkerBackground = Color(0xFF374151),
    diffHunkMarker = Color(0xFF9CDCFE),
    diffHunkMarkerBackground = Color(0xFF1F4B70),
    diffMetaText = Color(0xFFE5E7EB),
    diffHunkText = Color(0xFFBFE3FF),
)

private val DraculaProColors = CodeColors(
    background = Color(0xFF22212C),
    text = Color(0xFFF8F8F2),
    keyword = Color(0xFFFF79C6),
    string = Color(0xFFF1FA8C),
    number = Color(0xFFBD93F9),
    comment = Color(0xFF6272A4),
    operator = Color(0xFFFF79C6),
    punctuation = Color(0xFFF8F8F2),
    identifier = Color(0xFFF8F8F2),
    type = Color(0xFF8BE9FD),
    function = Color(0xFF50FA7B),
    variable = Color(0xFFFFB86C),
    constant = Color(0xFFBD93F9),
    annotation = Color(0xFF8BE9FD),
    decorator = Color(0xFF8BE9FD),
    builtin = Color(0xFF8BE9FD),
    inlineBackground = Color(0xFF30363D),
    inlineBorder = Color(0xFF3D444D),
    highlightedLineBackground = Color(0x22FFFFFF),
    diffAddedLineBackground = Color(0x3340A15F),
    diffRemovedLineBackground = Color(0x33A84C57),
    diffMetaLineBackground = Color(0x224B5563),
    diffAddedMarker = Color(0xFF9BE9A8),
    diffAddedMarkerBackground = Color(0xFF224D35),
    diffRemovedMarker = Color(0xFFFFA8B5),
    diffRemovedMarkerBackground = Color(0xFF5A2730),
    diffMetaMarker = Color(0xFFD1D5DB),
    diffMetaMarkerBackground = Color(0xFF374151),
    diffHunkMarker = Color(0xFF9CDCFE),
    diffHunkMarkerBackground = Color(0xFF1F4B70),
    diffMetaText = Color(0xFFE5E7EB),
    diffHunkText = Color(0xFFBFE3FF),
)

private val SolarizedLightColors = CodeColors(
    background = Color(0xFFFDF6E3),
    text = Color(0xFF657B83),
    keyword = Color(0xFF859900),
    string = Color(0xFF2AA198),
    number = Color(0xFFD33682),
    comment = Color(0xFF93A1A1),
    operator = Color(0xFF657B83),
    punctuation = Color(0xFF657B83),
    identifier = Color(0xFF657B83),
    type = Color(0xFFCB4B16),
    function = Color(0xFF268BD2),
    variable = Color(0xFF657B83),
    constant = Color(0xFFD33682),
    annotation = Color(0xFFCB4B16),
    decorator = Color(0xFFCB4B16),
    builtin = Color(0xFF268BD2),
)

/** App defaults, then the original One Dark Pro palette. Instance arguments take precedence. */
object CodeDefaults {
    fun lightColors(): CodeColors = githubLightColors()
    fun darkColors(): CodeColors = oneDarkProColors()
    fun githubLightColors(): CodeColors = LightColors
    fun oneDarkProColors(): CodeColors = OneDarkProColors
    fun draculaProColors(): CodeColors = DraculaProColors
    fun solarizedLightColors(): CodeColors = SolarizedLightColors

    /** Unspecified overrides inherit the surrounding Code defaults, including app-provided palettes. */
    @Composable
    fun colors(
        background: Color? = null,
        text: Color? = null,
        keyword: Color? = null,
        string: Color? = null,
        number: Color? = null,
        comment: Color? = null,
        operator: Color? = null,
        punctuation: Color? = null,
        identifier: Color? = null,
        type: Color? = null,
        function: Color? = null,
        variable: Color? = null,
        constant: Color? = null,
        annotation: Color? = null,
        decorator: Color? = null,
        builtin: Color? = null,
        title: Color? = null,
        language: Color? = null,
        action: Color? = null,
        lineNumber: Color? = null,
        lineNumberDivider: Color? = null,
        inlineText: Color? = null,
        inlineBackground: Color? = null,
        inlineBorder: Color? = null,
        highlightedLineBackground: Color? = null,
        diffAddedLineBackground: Color? = null,
        diffRemovedLineBackground: Color? = null,
        diffMetaLineBackground: Color? = null,
        diffHunkLineBackground: Color? = null,
        diffAddedMarker: Color? = null,
        diffAddedMarkerBackground: Color? = null,
        diffRemovedMarker: Color? = null,
        diffRemovedMarkerBackground: Color? = null,
        diffMetaMarker: Color? = null,
        diffMetaMarkerBackground: Color? = null,
        diffHunkMarker: Color? = null,
        diffHunkMarkerBackground: Color? = null,
        diffMetaText: Color? = null,
        diffHunkText: Color? = null,
    ): CodeColors {
        val base = LocalCodeColors.current ?: darkColors()
        return base.copy(
            background = background ?: base.background,
            text = text ?: base.text,
            keyword = keyword ?: base.keyword,
            string = string ?: base.string,
            number = number ?: base.number,
            comment = comment ?: base.comment,
            operator = operator ?: base.operator,
            punctuation = punctuation ?: base.punctuation,
            identifier = identifier ?: base.identifier,
            type = type ?: base.type,
            function = function ?: base.function,
            variable = variable ?: base.variable,
            constant = constant ?: base.constant,
            annotation = annotation ?: base.annotation,
            decorator = decorator ?: base.decorator,
            builtin = builtin ?: base.builtin,
            title = title ?: base.title,
            language = language ?: base.language,
            action = action ?: base.action,
            lineNumber = lineNumber ?: base.lineNumber,
            lineNumberDivider = lineNumberDivider ?: base.lineNumberDivider,
            inlineText = inlineText ?: base.inlineText,
            inlineBackground = inlineBackground ?: base.inlineBackground,
            inlineBorder = inlineBorder ?: base.inlineBorder,
            highlightedLineBackground = highlightedLineBackground ?: base.highlightedLineBackground,
            diffAddedLineBackground = diffAddedLineBackground ?: base.diffAddedLineBackground,
            diffRemovedLineBackground = diffRemovedLineBackground ?: base.diffRemovedLineBackground,
            diffMetaLineBackground = diffMetaLineBackground ?: base.diffMetaLineBackground,
            diffHunkLineBackground = diffHunkLineBackground ?: base.diffHunkLineBackground,
            diffAddedMarker = diffAddedMarker ?: base.diffAddedMarker,
            diffAddedMarkerBackground = diffAddedMarkerBackground ?: base.diffAddedMarkerBackground,
            diffRemovedMarker = diffRemovedMarker ?: base.diffRemovedMarker,
            diffRemovedMarkerBackground = diffRemovedMarkerBackground ?: base.diffRemovedMarkerBackground,
            diffMetaMarker = diffMetaMarker ?: base.diffMetaMarker,
            diffMetaMarkerBackground = diffMetaMarkerBackground ?: base.diffMetaMarkerBackground,
            diffHunkMarker = diffHunkMarker ?: base.diffHunkMarker,
            diffHunkMarkerBackground = diffHunkMarkerBackground ?: base.diffHunkMarkerBackground,
            diffMetaText = diffMetaText ?: base.diffMetaText,
            diffHunkText = diffHunkText ?: base.diffHunkText,
        )
    }

    @Composable
    fun typography(): CodeTypography = LocalCodeTypography.current

    @Composable
    fun layout(): CodeLayout = LocalCodeLayout.current
}

@Composable
fun ProvideCodeDefaults(
    colors: CodeColors = CodeDefaults.colors(),
    typography: CodeTypography = CodeDefaults.typography(),
    layout: CodeLayout = CodeDefaults.layout(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalCodeColors provides colors,
        LocalCodeTypography provides typography,
        LocalCodeLayout provides layout,
        content = content,
    )
}

private val LocalCodeColors = compositionLocalOf<CodeColors?> { null }
private val LocalCodeTypography = compositionLocalOf { CodeTypography() }
private val LocalCodeLayout = compositionLocalOf { CodeLayout() }
