package com.filkom.mycv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.filkom.mycv2.ui.theme.MyCV2Theme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import com.filkom.mycv2.screen.LoginScreen
import com.filkom.mycv2.screen.DaftarScreen
import com.filkom.mycv2.screen.DetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.navArgument

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

private fun enc(s: String): String = URLEncoder.encode(s, StandardCharsets.UTF_8.toString())
private fun dec(s: String): String = URLDecoder.decode(s, StandardCharsets.UTF_8.toString())

sealed class Route(val route: String) {
    data object Login : Route("login")
    // Detail menerima 4 argumen (boleh kosong): nim, nama, email, alamat
    data object Detail : Route("detail/{nim}/{nama}/{email}/{alamat}") {
        fun build(nim: String, nama: String, email: String = "", alamat: String = ""): String {
            return "detail/${enc(nim)}/${enc(nama)}/${enc(email)}/${enc(alamat)}"
        }
    }
    data object Daftar : Route("daftar")
}

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Route.Login.route) {
        composable(Route.Login.route) {
            LoginScreen(
                onLogin = { nim, nama ->
                    nav.navigate(Route.Detail.build(nim, nama))
                },
                onDaftar = {
                    nav.navigate(Route.Daftar.route)
                }
            )
        }
        composable(Route.Daftar.route) {
            DaftarScreen(
                onSimpan = { nim, nama, email, alamat ->
                    nav.navigate(Route.Detail.build(nim, nama, email, alamat))
                }
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("nim") { type = NavType.StringType },
                navArgument("nama") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("alamat") { type = NavType.StringType }
            )
        ) { backStack ->
            val nim = dec(backStack.arguments?.getString("nim") ?: "")
            val nama = dec(backStack.arguments?.getString("nama") ?: "")
            val email = dec(backStack.arguments?.getString("email") ?: "")
            val alamat = dec(backStack.arguments?.getString("alamat") ?: "")

            DetailScreen(
                nim = nim,
                nama = nama,
                email = email,
                alamat = alamat,
                onDaftar = { nav.navigate(Route.Daftar.route) }
            )
        }
    }
}
