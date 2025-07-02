package org.onedroid.seefood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import org.onedroid.seefood.app.navigation.NavigationScreen
import org.onedroid.seefood.app.auth.AuthViewModel
import org.onedroid.seefood.app.auth.ForgotPasswordScreen
import org.onedroid.seefood.app.auth.LoginScreen
import org.onedroid.seefood.app.auth.RegisterScreen
import org.onedroid.seefood.home.HomeScreen
import org.onedroid.seefood.ui.theme.SEEFoodTheme

class App : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            SEEFoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationScreen()
                }
            }
        }
    }
}
