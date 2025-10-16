package com.filkom.mycv2.data

import com.filkom.mycv2.model.User
import kotlinx.coroutines.delay

class AuthRepository {
    // penyimpanan dummy (in-memory)
    private val users = mutableListOf<User>()

    suspend fun register(user: User): Result<User> {
        delay(300) // simulasi I/O
        // validasi sederhana: NIM unik
        if (users.any { it.nim == user.nim }) {
            return Result.failure(IllegalArgumentException("NIM sudah terdaftar"))
        }
        users += user
        return Result.success(user)
    }

    suspend fun login(nim: String, nama: String): Result<User> {
        delay(300)
        val u = users.find { it.nim == nim && it.nama.equals(nama, ignoreCase = true) }
        return if (u != null) Result.success(u)
        else Result.failure(IllegalArgumentException("User tidak ditemukan"))
    }
}