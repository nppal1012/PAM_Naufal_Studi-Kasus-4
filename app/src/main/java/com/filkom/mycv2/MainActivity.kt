package com.filkom.mycv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filkom.mycv2.screen.DaftarScreen
import com.filkom.mycv2.screen.DetailScreen
import com.filkom.mycv2.screen.LoginScreen
import com.filkom.mycv2.ui.theme.MyCV2Theme
import com.filkom.mycv2.vm.AuthViewModel

sealed class Route(val route: String) {
    data object Login : Route("login")
    data object Daftar : Route("daftar")
    data object Detail : Route("detail")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCV2Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val vm: AuthViewModel = viewModel() // <- ViewModel bersama (shared)

    NavHost(navController = nav, startDestination = Route.Login.route) {

        composable(Route.Login.route) {
            LoginScreen(
                nim = vm.loginForm.nim,
                nama = vm.loginForm.nama,
                isLoading = vm.isLoading,
                errorMessage = vm.errorMessage,
                onNimChange = vm::onLoginNim,
                onNamaChange = vm::onLoginNama,
                onDismissError = vm::clearError,
                onLogin = {
                    vm.login {
                        nav.navigate(Route.Detail.route)
                    }
                },
                onDaftar = { nav.navigate(Route.Daftar.route) }
            )
        }

        composable(Route.Daftar.route) {
            DaftarScreen(
                nim = vm.registerForm.nim,
                nama = vm.registerForm.nama,
                email = vm.registerForm.email,
                alamat = vm.registerForm.alamat,
                isLoading = vm.isLoading,
                errorMessage = vm.errorMessage,
                onNimChange = vm::onRegNim,
                onNamaChange = vm::onRegNama,
                onEmailChange = vm::onRegEmail,
                onAlamatChange = vm::onRegAlamat,
                onDismissError = vm::clearError,
                onSimpan = {
                    vm.register {
                        nav.navigate(Route.Detail.route)
                    }
                }
            )
        }

        composable(Route.Detail.route) {
            val u = vm.currentUser
            DetailScreen(
                nim = u?.nim.orEmpty(),
                nama = u?.nama.orEmpty(),
                email = u?.email.orEmpty(),
                alamat = u?.alamat.orEmpty(),
                onDaftar = { nav.navigate(Route.Daftar.route) }
            )
        }
    }
}
