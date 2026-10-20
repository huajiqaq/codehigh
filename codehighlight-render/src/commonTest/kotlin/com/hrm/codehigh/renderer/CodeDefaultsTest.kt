package com.hrm.codehigh.renderer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeDefaultsTest {
    @Test
    fun should_preserveOriginalFontsAndGeometry_when_usingDefaults() {
        val typography = CodeTypography()
        val layout = CodeLayout()
        assertEquals(13.sp, typography.text.fontSize)
        assertEquals(20.sp, typography.text.lineHeight)
        assertEquals(FontFamily.Monospace, typography.text.fontFamily)
        assertEquals(13.sp, typography.inlineCode.fontSize)
        assertEquals(12.sp, typography.title.fontSize)
        assertEquals(11.sp, typography.language.fontSize)
        assertEquals(11.sp, typography.action.fontSize)
        assertEquals(12.sp, typography.collapse.fontSize)
        assertEquals(RectangleShape, layout.shape)
        assertEquals(12.dp, layout.contentPadding.calculateLeftPadding(LayoutDirection.Ltr))
        assertEquals(8.dp, layout.contentPadding.calculateTopPadding())
        assertEquals(40.dp, layout.lineNumberWidth)
        assertEquals(8.dp, layout.lineNumberPadding)
        assertEquals(6.dp, layout.inlineContentPadding.calculateLeftPadding(LayoutDirection.Ltr))
        assertEquals(2.dp, layout.inlineContentPadding.calculateTopPadding())
        assertEquals(1.dp, layout.inlineBorderWidth)
    }

    @Test
    fun should_preserveOriginalInlineColors_when_usingBuiltInPalettes() {
        val light = CodeDefaults.lightColors()
        val dark = CodeDefaults.darkColors()
        assertEquals(light.text, light.inlineText)
        assertEquals(Color(0xFFF6F8FA), light.inlineBackground)
        assertEquals(Color(0xFFD0D7DE), light.inlineBorder)
        assertEquals(dark.text, dark.inlineText)
        assertEquals(Color(0xFF30363D), dark.inlineBackground)
        assertEquals(Color(0xFF3D444D), dark.inlineBorder)
    }

    @Test
    fun should_preserveOriginalDiffAndToolbarColors_when_usingDarkPalette() {
        val colors = CodeDefaults.darkColors()
        assertEquals(colors.text.copy(alpha = 0.92f), colors.title)
        assertEquals(colors.comment.copy(alpha = 0.7f), colors.language)
        assertEquals(colors.comment.copy(alpha = 0.5f), colors.lineNumber)
        assertEquals(Color(0x3340A15F), colors.diffAddedLineBackground)
        assertEquals(Color(0x33A84C57), colors.diffRemovedLineBackground)
        assertEquals(Color(0x224B5563), colors.diffMetaLineBackground)
        assertEquals(Color(0xFF9BE9A8), colors.diffAddedMarker)
        assertEquals(Color(0xFFFFA8B5), colors.diffRemovedMarker)
        assertEquals(Color(0xFFBFE3FF), colors.diffHunkText)
    }
}
