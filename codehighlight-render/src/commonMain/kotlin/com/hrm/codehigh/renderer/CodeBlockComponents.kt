package com.hrm.codehigh.renderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun CodeBlockHeaderLabels(
    title: String,
    language: String,
    colors: CodeColors,
    typography: CodeTypography,
    modifier: Modifier = Modifier,
) {
    if (title.isBlank() && language.isBlank()) return
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        if (title.isNotBlank()) {
            BasicText(
                text = title,
                style = typography.resolvedTitle.copy(color = colors.title),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            )
        }
        if (language.isNotBlank()) {
            BasicText(
                text = language.lowercase(),
                style = typography.resolvedLanguage.copy(color = colors.language),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}
