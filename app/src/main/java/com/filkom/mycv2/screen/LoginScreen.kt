package com.filkom.mycv2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    nim: String,
    nama: String,
    isLoading: Boolean,
    errorMessage: String?,
    onNimChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onDismissError: () -> Unit,
    onLogin: () -> Unit,
    onDaftar: () -> Unit
) {
    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = onDismissError,
            confirmButton = {
                TextButton(onClick = onDismissError) { Text("OK") }
            },
            title = { Text("Info") },
            text = { Text(errorMessage) }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nim,
            onValueChange = onNimChange,
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )

        Button(
            enabled = !isLoading,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp),
            onClick = onLogin
        ) { Text(if (isLoading) "Loading..." else "LOGIN") }

        Button(
            enabled = !isLoading,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp),
            onClick = onDaftar
        ) { Text("DAFTAR") }
    }
}