package com.filkom.mycv2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DaftarScreen(
    nim: String,
    nama: String,
    email: String,
    alamat: String,
    isLoading: Boolean,
    errorMessage: String?,
    onNimChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAlamatChange: (String) -> Unit,
    onDismissError: () -> Unit,
    onSimpan: () -> Unit
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
        Text(text = "Form Daftar", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nim, onValueChange = onNimChange,
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )
        OutlinedTextField(
            value = nama, onValueChange = onNamaChange,
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )
        OutlinedTextField(
            value = email, onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )
        OutlinedTextField(
            value = alamat, onValueChange = onAlamatChange,
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )

        Button(
            enabled = !isLoading,
            onClick = onSimpan,
            modifier = Modifier.padding(top = 20.dp)
        ) { Text(if (isLoading) "Loading..." else "SIMPAN") }
    }
}