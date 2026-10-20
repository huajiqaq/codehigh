package com.hrm.codehigh.renderer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Unspecified TextStyle attributes inherit the corresponding built-in code style. */
@Immutable
data class CodeTypography(
    val text: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 13.sp, lineHeight = 20.sp),
    val inlineCode: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 13.sp, lineHeight = 20.sp),
    val title: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, fontWeight = FontWeight.Medium),
    val language: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
    val action: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
    val collapse: TextStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
)

private val DefaultTypography = CodeTypography()
internal val CodeTypography.resolvedText get() = DefaultTypography.text.merge(text)
internal val CodeTypography.resolvedInlineCode get() = DefaultTypography.inlineCode.merge(inlineCode)
internal val CodeTypography.resolvedTitle get() = DefaultTypography.title.merge(title)
internal val CodeTypography.resolvedLanguage get() = DefaultTypography.language.merge(language)
internal val CodeTypography.resolvedAction get() = DefaultTypography.action.merge(action)
internal val CodeTypography.resolvedCollapse get() = DefaultTypography.collapse.merge(collapse)
