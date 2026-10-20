package com.hrm.codehigh.preview.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrm.codehigh.preview.data.SampleCode
import com.hrm.codehigh.renderer.CodeBlock
import com.hrm.codehigh.renderer.CodeTypography
import com.hrm.codehigh.renderer.CodeLayout
import com.hrm.codehigh.renderer.CodeDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * 主题对比分类，展示同一段代码在 4 套内置主题下的对比效果。
 */
@Composable
internal fun ThemeCategory() {
    val themes = listOf(
        "One Dark Pro（暗色）" to CodeDefaults.darkColors(),
        "GitHub Light（亮色）" to CodeDefaults.lightColors(),
        "Dracula Pro（暗色）" to CodeDefaults.draculaProColors(),
        "Solarized Light（亮色）" to CodeDefaults.solarizedLightColors(),
    )

    val sampleCode = SampleCode.KOTLIN

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("自定义字号、行高与内边距", style = MaterialTheme.typography.titleMedium)
            CodeBlock(
                code = "val answer = 42\nprintln(answer)", language = "kotlin", title = "Large type",
                typography = CodeTypography(text = TextStyle(fontSize = 22.sp, lineHeight = 34.sp)),
                layout = CodeLayout(contentPadding = PaddingValues(20.dp)),
                showLineNumbers = true,
            )
        }
        items(themes) { (name, theme) ->
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CodeBlock(
                    code = sampleCode,
                    language = "kotlin",
                    colors = theme,
                    showLineNumbers = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
