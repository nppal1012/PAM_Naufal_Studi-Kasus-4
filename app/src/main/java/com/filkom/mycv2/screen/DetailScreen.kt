package com.filkom.mycv2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    nim: String,
    nama: String,
    email: String,
    alamat: String,
    onDaftar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Detail", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text("NIM   : $nim")
        Text("Nama  : $nama")
        Text("Email : ${email.ifBlank { "-" }}")
        Text("Alamat: ${alamat.ifBlank { "-" }}")

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp),
            onClick = onDaftar
        ) { Text("DAFTAR") }
    }
}