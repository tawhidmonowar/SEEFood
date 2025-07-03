package org.onedroid.seefood.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onCategorySelected: (String) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(24.dp),
                color = backgroundColor
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable { onCategorySelected(category) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}