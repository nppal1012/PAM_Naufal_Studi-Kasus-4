package com.filkom.mycv2.model

data class User(
    val nim: String,
    val nama: String,
    val email: String = "",
    val alamat: String = ""
)