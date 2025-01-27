package com.example.jet_ecommerce.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CustomAlertDialog(
    dialogTitle : String,
    showDialog : Boolean,
    dialogDescription : String,
    onDismiss: () -> Unit  = {},
    onConfirm: () -> Unit
) {
      if(showDialog)
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "Yes") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            },
            title = { Text(text = dialogTitle) },
            text = { Text(text = dialogDescription) }
        )
}