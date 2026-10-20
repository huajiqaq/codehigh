package com.hrm.codehigh.renderer

import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class CodeColorsTest {

    /** Regression snapshots from the four original presets, in TokenType declaration order. */
    @Test
    fun should_preserveEveryOriginalTokenColor_when_usingBuiltInPresets() {
        val cases = listOf(
            CodeDefaults.githubLightColors() to listOf(
                0xFFCF222E, 0xFF0A3069, 0xFF0550AE, 0xFF6E7781, 0xFF24292F, 0xFF24292F, 0xFF24292F, 0xFF953800,
                0xFF8250DF, 0xFF24292F, 0xFF0550AE, 0xFF953800, 0xFF953800, 0xFF0550AE, 0xFF24292F,
            ),
            CodeDefaults.oneDarkProColors() to listOf(
                0xFFC678DD, 0xFF98C379, 0xFFD19A66, 0xFF5C6370, 0xFF56B6C2, 0xFFABB2BF, 0xFFABB2BF, 0xFFE5C07B,
                0xFF61AFEF, 0xFFE06C75, 0xFFD19A66, 0xFFE5C07B, 0xFFE5C07B, 0xFF56B6C2, 0xFFABB2BF,
            ),
            CodeDefaults.draculaProColors() to listOf(
                0xFFFF79C6, 0xFFF1FA8C, 0xFFBD93F9, 0xFF6272A4, 0xFFFF79C6, 0xFFF8F8F2, 0xFFF8F8F2, 0xFF8BE9FD,
                0xFF50FA7B, 0xFFFFB86C, 0xFFBD93F9, 0xFF8BE9FD, 0xFF8BE9FD, 0xFF8BE9FD, 0xFFF8F8F2,
            ),
            CodeDefaults.solarizedLightColors() to listOf(
                0xFF859900, 0xFF2AA198, 0xFFD33682, 0xFF93A1A1, 0xFF657B83, 0xFF657B83, 0xFF657B83, 0xFFCB4B16,
                0xFF268BD2, 0xFF657B83, 0xFFD33682, 0xFFCB4B16, 0xFFCB4B16, 0xFF268BD2, 0xFF657B83,
            ),
        )
        for ((colors, expected) in cases) {
            assertEquals(TokenType.entries.size, expected.size)
            TokenType.entries.forEachIndexed { index, type ->
                assertEquals(Color(expected[index]), colors.colorFor(type), "${colors.background}: $type")
            }
        }
    }

    @Test
    fun should_returnColor_when_validTokenType() {
        val theme = CodeDefaults.darkColors()
        val color = theme.colorFor(TokenType.KEYWORD)
        assertNotNull(color)
        assertNotEquals(Color.Unspecified, color)
    }

    @Test
    fun should_coverAllTokenTypes_when_oneDarkProTheme() {
        val theme = CodeDefaults.darkColors()
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_githubLightTheme() {
        val theme = CodeDefaults.lightColors()
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_draculaProTheme() {
        val theme = CodeDefaults.draculaProColors()
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_coverAllTokenTypes_when_solarizedLightTheme() {
        val theme = CodeDefaults.solarizedLightColors()
        for (type in TokenType.entries) {
            val color = theme.colorFor(type)
            assertNotNull(color)
        }
    }

    @Test
    fun should_preserveOriginalBackground_when_oneDarkColors() {
        assertEquals(Color(0xFF282C34), CodeDefaults.darkColors().background)
    }

    @Test
    fun should_preserveOriginalBackground_when_githubColors() {
        assertEquals(Color(0xFFFFFFFF), CodeDefaults.lightColors().background)
    }

    @Test
    fun should_preserveOriginalBackground_when_draculaColors() {
        assertEquals(Color(0xFF22212C), CodeDefaults.draculaProColors().background)
    }

    @Test
    fun should_preserveOriginalBackground_when_solarizedColors() {
        assertEquals(Color(0xFFFDF6E3), CodeDefaults.solarizedLightColors().background)
    }

    @Test
    fun should_customizeOneTokenColor_withoutMutatingPreset() {
        val colors = CodeDefaults.darkColors().copy(keyword = Color.Magenta)
        assertEquals(Color.Magenta, colors.colorFor(TokenType.KEYWORD))
        assertEquals(Color(0xFFC678DD), CodeDefaults.darkColors().keyword)
        assertEquals(CodeDefaults.darkColors().string, colors.string)
    }

    @Test
    fun should_provideLineBackgroundColors_when_themeUsedForAdvancedRendering() {
        val theme = CodeDefaults.darkColors()
        assertNotEquals(Color.Unspecified, theme.highlightedLineBackground)
        assertNotEquals(Color.Unspecified, theme.diffAddedLineBackground)
        assertNotEquals(Color.Unspecified, theme.diffRemovedLineBackground)
        assertNotEquals(Color.Unspecified, theme.diffMetaLineBackground)
    }
}
