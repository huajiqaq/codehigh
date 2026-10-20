package com.hrm.codehigh.renderer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.hrm.codehigh.ast.TokenType

/** Syntax and surface colors shared by CodeBlock and InlineCode. The constructor is a fixed light palette. */
@Immutable
data class CodeColors(
    val background: Color = Color(0xFFFFFFFF),
    val text: Color = Color(0xFF24292F),
    val keyword: Color = Color(0xFFCF222E),
    val string: Color = Color(0xFF0A3069),
    val number: Color = Color(0xFF0550AE),
    val comment: Color = Color(0xFF6E7781),
    val operator: Color = Color(0xFF24292F),
    val punctuation: Color = Color(0xFF24292F),
    val identifier: Color = Color(0xFF24292F),
    val type: Color = Color(0xFF953800),
    val function: Color = Color(0xFF8250DF),
    val variable: Color = Color(0xFF24292F),
    val constant: Color = Color(0xFF0550AE),
    val annotation: Color = Color(0xFF953800),
    val decorator: Color = Color(0xFF953800),
    val builtin: Color = Color(0xFF0550AE),
    val title: Color = text.copy(alpha = 0.92f),
    val language: Color = comment.copy(alpha = 0.7f),
    val action: Color = function.copy(alpha = 0.8f),
    val lineNumber: Color = comment.copy(alpha = 0.5f),
    val lineNumberDivider: Color = comment.copy(alpha = 0.3f),
    val inlineText: Color = text,
    val inlineBackground: Color = Color(0xFFF6F8FA),
    val inlineBorder: Color = Color(0xFFD0D7DE),
    val highlightedLineBackground: Color = Color(0x12000000),
    val diffAddedLineBackground: Color = Color(0x2234D058),
    val diffRemovedLineBackground: Color = Color(0x22FF6B6B),
    val diffMetaLineBackground: Color = Color(0x140A66A1),
    val diffHunkLineBackground: Color = diffMetaLineBackground,
    val diffAddedMarker: Color = Color(0xFF1F7A38),
    val diffAddedMarkerBackground: Color = Color(0xFFD9F5E0),
    val diffRemovedMarker: Color = Color(0xFFB42318),
    val diffRemovedMarkerBackground: Color = Color(0xFFFADADD),
    val diffMetaMarker: Color = Color(0xFF4B5563),
    val diffMetaMarkerBackground: Color = Color(0xFFE5E7EB),
    val diffHunkMarker: Color = Color(0xFF0958D9),
    val diffHunkMarkerBackground: Color = Color(0xFFDCEEFF),
    val diffMetaText: Color = Color(0xFF374151),
    val diffHunkText: Color = Color(0xFF0B4F8A),
) {
    fun colorFor(type: TokenType): Color = when (type) {
        TokenType.KEYWORD -> keyword
        TokenType.STRING -> string
        TokenType.NUMBER -> number
        TokenType.COMMENT -> comment
        TokenType.OPERATOR -> operator
        TokenType.PUNCTUATION -> punctuation
        TokenType.IDENTIFIER -> identifier
        TokenType.TYPE -> this.type
        TokenType.FUNCTION -> function
        TokenType.VARIABLE -> variable
        TokenType.CONSTANT -> constant
        TokenType.ANNOTATION -> annotation
        TokenType.DECORATOR -> decorator
        TokenType.BUILTIN -> builtin
        TokenType.PLAIN -> text
    }
}
