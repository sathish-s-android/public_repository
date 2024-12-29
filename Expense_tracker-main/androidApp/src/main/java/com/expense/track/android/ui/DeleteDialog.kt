package com.expense.track.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors

@Composable
fun DeleteDialog(deleteConform:()->Unit, onDisMiss:()->Unit){
    AlertDialog(
        onDismissRequest = {onDisMiss() },
        containerColor = AppColors.dialogBackground,
        title = {
            Text(text = getString(R.string.alert),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(getString(R.string.delete_conformation),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                deleteConform()
            }) {
                Text(getString(R.string.ok),
                    color = AppColors.saveButtonBackground,
                    fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDisMiss() }) {
                Text(getString(R.string.cancel),
                    color = AppColors.saveButtonBackground,
                    fontWeight = FontWeight.Bold)
            }
        }
    )
}