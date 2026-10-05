package me.basehub.scannerapp.route

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.basehub.scannerapp.feature.home.HomeScreen

@Composable
fun AppRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.HOME.route,
        modifier = modifier.fillMaxSize(),
    ) {
        composable(AppScreen.HOME.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(AppScreen.RESULT.route) {
            PendingScreen(
                title = "Scan Result",
                onBack = {
                    navController.popBackStack(
                        AppScreen.HOME.route,
                        inclusive = false,
                    )
                },
            )
        }

        composable(AppScreen.ABOUT.route) {
            PendingScreen(
                title = "About",
                onBack = { navController.popBackStack() },
            )
        }

        composable(AppScreen.PRIVACY_POLICY.route) {
            PendingScreen(
                title = "Privacy Policy",
                onBack = { navController.popBackStack() },
            )
        }
    }
}

// Tampilan sementara agar route bisa dikompilasi sebelum layarnya dibuat.
@Composable
private fun PendingScreen(
    title: String,
    onBack: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        if (onBack != null) {
            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}