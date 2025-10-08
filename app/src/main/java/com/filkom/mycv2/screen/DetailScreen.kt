package com.filkom.mycv2.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(
    nim: String,
    nama: String,
    email: String,
    alamat: String,
    onDaftar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Detail", fontSize = 20.sp)
        Text(
            text = "NIM: $nim\nNama: $nama\nEmail: ${if (email.isBlank()) "-" else email}\nAlamat: ${if (alamat.isBlank()) "-" else alamat}",
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            textAlign = TextAlign.Start
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp),
            onClick = onDaftar
        ) { Text("DAFTAR") }
    }
}