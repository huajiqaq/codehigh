package com.hrm.codehigh.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.i18n.Strings
import com.hrm.codehigh.ast.CodeToken
import com.hrm.codehigh.stream.IncrementalHighlighter
import kotlinx.coroutines.delay

/**
 * 代码块渲染组件。
 * 对外公开，支持完整的代码高亮功能。
 *
 * @param code 代码字符串
 * @param language 语言标识符（如 "kotlin"、"python"）
 * @param modifier Compose Modifier
 * @param isStreaming 是否处于流式输出状态，为 true 时在末尾显示光标动画
 * @param colors 语法与容器配色
 * @param typography 字体排版，与 measureCodeBlockSize 共用
 * @param layout 几何布局，与 measureCodeBlockSize 共用
 * @param showLineNumbers 是否显示行号
 * @param showCopyButton 是否显示复制按钮
 * @param maxVisibleLines 最大可见行数，超出时显示折叠按钮（null 表示不限制）
 * @param onTokenClick Token 点击回调
 */
@Composable
fun CodeBlock(
    code: String,
    language: String = "",
    title: String = "",
    modifier: Modifier = Modifier,
    colors: CodeColors = CodeDefaults.colors(),
    typography: CodeTypography = CodeDefaults.typography(),
    layout: CodeLayout = CodeDefaults.layout(),
    isStreaming: Boolean = false,
    showLineNumbers: Boolean = false,
    startLine: Int = 1,
    highlightedLines: Set<Int> = emptySet(),
    showCopyButton: Boolean = true,
    maxVisibleLines: Int? = null,
    onTokenClick: ((CodeToken) -> Unit)? = null,
) {
    val currentOnTokenClick by rememberUpdatedState(onTokenClick)
    val highlighter = remember(language) { IncrementalHighlighter() }

    var isExpanded by remember { mutableStateOf(false) }
    val lines = code.split("\n")
    val totalLines = lines.size
    val isCollapsible = maxVisibleLines != null && totalLines > maxVisibleLines
    val visibleLineCount = when {
        !isCollapsible || isExpanded -> totalLines
        else -> maxVisibleLines // isCollapsible 为 true 时 maxVisibleLines 一定非空
    }

    val visibleLines = lines.take(visibleLineCount)
    val visibleCode = visibleLines.joinToString("\n")
    val lineOffsets = remember(visibleLines) {
        var offset = 0
        IntArray(visibleLines.size) { index -> offset.also { offset += visibleLines[index].length + 1 } }
    }
    val visibleAst = remember(visibleCode, language) { highlighter.update(visibleCode, language) }
    val lineHighlights = remember(visibleAst, colors, language, highlightedLines, visibleLineCount) {
        buildLineRenders(
            sourceLines = visibleLines,
            tokens = visibleAst.tokens,
            colors = colors,
            language = language,
            highlightedLines = highlightedLines,
        )
    }
    val fallbackToPlainLines = remember(lineHighlights, visibleLines) {
        visibleLines.isNotEmpty() && (
            lineHighlights.isEmpty() ||
                lineHighlights.size < visibleLines.size ||
                lineHighlights.all { it.text.text.isBlank() }
            )
    }
    val showToolbar = title.isNotBlank() || language.isNotBlank() || showCopyButton
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val layoutDirection = LocalLayoutDirection.current
    val effectiveTextStyle = typography.resolvedText
    val dimensions = remember(visibleCode, language, title, showCopyButton, typography, layout, density, textMeasurer) {
        measureCodeBlockSize(visibleCode, language, title, showCopyButton, typography, layout, density, textMeasurer)
    }
    val codeLineHeightDp = with(density) { dimensions.lineHeight.toDp() }
    val lineNumberStyle = effectiveTextStyle.copy(
        color = colors.lineNumber,
        textAlign = androidx.compose.ui.text.style.TextAlign.End,
    )
    val diffMarkerStyle = effectiveTextStyle.copy(fontWeight = FontWeight.Bold)

    Column(
        modifier = modifier
            .then(if (layout.shape != androidx.compose.ui.graphics.RectangleShape) Modifier.clip(layout.shape) else Modifier)
            .background(colors.background)
            .fillMaxWidth()
    ) {
        if (showToolbar) {
            DisableSelection {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CodeBlockHeaderLabels(
                        title = title,
                        language = language,
                        colors = colors,
                        typography = typography,
                        modifier = Modifier.padding(end = layout.lineNumberPadding)
                    )
                    if (showCopyButton) {
                        CopyButton(code = code, colors = colors, textStyle = typography.resolvedAction)
                    }
                }
            }
        }

        // 代码内容区域（逐行渲染，行号与代码行严格对齐）
        val horizontalScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState)
        ) {
            Column(
                modifier = Modifier.padding(top = layout.contentPadding.calculateTopPadding(), bottom = layout.contentPadding.calculateBottomPadding())
            ) {
                val renderedLines = if (fallbackToPlainLines) {
                    visibleLines.map { line ->
                        CodeLineRender(
                            kind = CodeLineKind.NORMAL,
                            text = AnnotatedString(line),
                        )
                    }
                } else {
                    lineHighlights
                }
                renderedLines.forEachIndexed { index, lineRender ->
                    key(index) {
                        val isLastLine = index == renderedLines.lastIndex
                        val diffMarker = diffMarkerForLine(lineRender.kind)
                        val rawLine = visibleLines.getOrElse(index) { "" }
                        val displayText = if (lineRender.text.text.isEmpty() && rawLine.isNotEmpty()) {
                            AnnotatedString(rawLine)
                        } else {
                            lineRender.text
                        }
                        Row(
                            modifier = Modifier.height(codeLineHeightDp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (showLineNumbers) {
                                DisableSelection {
                                    BasicText(
                                        text = (startLine + index).toString(),
                                        style = lineNumberStyle,
                                        modifier = Modifier
                                            .width(layout.lineNumberWidth)
                                            .padding(end = layout.lineNumberPadding),
                                        maxLines = 1
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(layout.lineNumberDividerWidth)
                                            .height(codeLineHeightDp)
                                            .background(
                                                colors.lineNumberDivider
                                            )
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .background(colors.backgroundForLine(lineRender.kind))
                                    .padding(start = layout.contentPadding.calculateStartPadding(layoutDirection), end = layout.contentPadding.calculateEndPadding(layoutDirection)),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                if (diffMarker != null) {
                                    BasicText(
                                        text = diffMarker,
                                        style = diffMarkerStyle.copy(
                                            color = colors.diffMarkerColorForLine(lineRender.kind)
                                        ),
                                        modifier = Modifier
                                            .background(colors.diffMarkerBackgroundForLine(lineRender.kind))
                                            .padding(horizontal = 6.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                var textLayout by remember { mutableStateOf<TextLayoutResult?>(null) }
                                BasicText(
                                    text = displayText,
                                    onTextLayout = { textLayout = it },
                                    modifier = if (onTokenClick == null) Modifier else Modifier.pointerInput(displayText, lineOffsets[index], visibleAst.tokens) {
                                        detectTapGestures { point ->
                                            val offset = textLayout?.getOffsetForPosition(point) ?: return@detectTapGestures
                                            visibleAst.tokens.firstOrNull { lineOffsets[index] + offset in it.range }
                                                ?.let { currentOnTokenClick?.invoke(it) }
                                        }
                                    },
                                    style = effectiveTextStyle.copy(color = colors.textColorForLine(lineRender.kind), fontWeight = if (lineRender.kind == CodeLineKind.DIFF_META_HEADER || lineRender.kind == CodeLineKind.DIFF_META_HUNK) FontWeight.SemiBold else effectiveTextStyle.fontWeight)
                                )
                                if (isLastLine) {
                                    StreamingCursor(
                                        isStreaming = isStreaming,
                                        color = colors.text,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 折叠/展开按钮
        if (isCollapsible) {
            DisableSelection {
                BasicText(
                    text = if (isExpanded) Strings.collapse() else Strings.expand(totalLines - visibleLineCount),
                    style = typography.resolvedCollapse.copy(color = colors.function),
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }
    }
}

private fun diffMarkerForLine(kind: CodeLineKind): String? = when (kind) {
    CodeLineKind.DIFF_ADDED -> "+"
    CodeLineKind.DIFF_REMOVED -> "-"
    CodeLineKind.DIFF_META_HEADER -> "⋯"
    CodeLineKind.DIFF_META_HUNK -> "@"
    else -> null
}

private fun CodeColors.diffMarkerBackgroundForLine(kind: CodeLineKind) = when (kind) {
    CodeLineKind.DIFF_ADDED -> diffAddedMarkerBackground
    CodeLineKind.DIFF_REMOVED -> diffRemovedMarkerBackground
    CodeLineKind.DIFF_META_HEADER -> diffMetaMarkerBackground
    CodeLineKind.DIFF_META_HUNK -> diffHunkMarkerBackground
    else -> androidx.compose.ui.graphics.Color.Transparent
}

private fun CodeColors.diffMarkerColorForLine(kind: CodeLineKind) = when (kind) {
    CodeLineKind.DIFF_ADDED -> diffAddedMarker
    CodeLineKind.DIFF_REMOVED -> diffRemovedMarker
    CodeLineKind.DIFF_META_HEADER -> diffMetaMarker
    CodeLineKind.DIFF_META_HUNK -> diffHunkMarker
    else -> text
}

private fun CodeColors.textColorForLine(kind: CodeLineKind) = when (kind) {
    CodeLineKind.DIFF_META_HEADER -> diffMetaText
    CodeLineKind.DIFF_META_HUNK -> diffHunkText
    else -> text
}

/**
 * 复制按钮组件。
 * 标记为 internal，仅供 CodeBlock 内部使用。
 */
@Composable
internal fun CopyButton(
    code: String,
    colors: CodeColors,
    textStyle: TextStyle,
) {
    @Suppress("DEPRECATION")
    val clipboardManager = LocalClipboardManager.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
            copied = false
        }
    }

    DisableSelection {
        BasicText(
            text = if (copied) "✓ ${Strings.copied()}" else Strings.copy(),
            style = textStyle.copy(color = colors.action),
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    @Suppress("DEPRECATION")
                    clipboardManager.setText(AnnotatedString(code))
                    copied = true
                }
        )
    }
}
