package com.filkom.mycv2.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filkom.mycv2.data.AuthRepository
import com.filkom.mycv2.model.User
import kotlinx.coroutines.launch

data class LoginForm(val nim: String = "", val nama: String = "")
data class RegisterForm(
    val nim: String = "",
    val nama: String = "",
    val email: String = "",
    val alamat: String = ""
)

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var loginForm by mutableStateOf(LoginForm())
        private set
    var registerForm by mutableStateOf(RegisterForm())
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var currentUser by mutableStateOf<User?>(null)
        private set

    fun onLoginNim(v: String)  { loginForm = loginForm.copy(nim = v) }
    fun onLoginNama(v: String) { loginForm = loginForm.copy(nama = v) }

    fun onRegNim(v: String)    { registerForm = registerForm.copy(nim = v) }
    fun onRegNama(v: String)   { registerForm = registerForm.copy(nama = v) }
    fun onRegEmail(v: String)  { registerForm = registerForm.copy(email = v) }
    fun onRegAlamat(v: String) { registerForm = registerForm.copy(alamat = v) }

    fun login(onSuccess: () -> Unit) {
        // validasi dasar
        if (loginForm.nim.isBlank() || loginForm.nama.isBlank()) {
            errorMessage = "NIM dan Nama wajib diisi"
            return
        }
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            val result = repo.login(loginForm.nim.trim(), loginForm.nama.trim())
            isLoading = false
            result.onSuccess {
                currentUser = it
                // Prefill ke form daftar juga (opsional)
                registerForm = registerForm.copy(
                    nim = it.nim, nama = it.nama, email = it.email, alamat = it.alamat
                )
                onSuccess()
            }.onFailure {
                errorMessage = it.message ?: "Login gagal"
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        if (registerForm.nim.isBlank() || registerForm.nama.isBlank()
            || registerForm.email.isBlank() || registerForm.alamat.isBlank()) {
            errorMessage = "Semua field wajib diisi"
            return
        }
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            val user = User(
                nim = registerForm.nim.trim(),
                nama = registerForm.nama.trim(),
                email = registerForm.email.trim(),
                alamat = registerForm.alamat.trim()
            )
            val result = repo.register(user)
            isLoading = false
            result.onSuccess {
                currentUser = it
                onSuccess()
            }.onFailure {
                errorMessage = it.message ?: "Registrasi gagal"
            }
        }
    }

    fun clearError() { errorMessage = null }
}