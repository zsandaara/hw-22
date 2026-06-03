package com.example.hw_2.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsBlock(want: Int, cooking: Int, cooked: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Want: $want   ", style = MaterialTheme.typography.bodyMedium)
        Text("Cooking: $cooking   ", style = MaterialTheme.typography.bodyMedium)
        Text("Cooked: $cooked", style = MaterialTheme.typography.bodyMedium)
    }
}