package org.onedroid.seefood.app.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = AuthViewModel()
) {
    var email by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.isPasswordResetSent) {
        if (viewModel.isPasswordResetSent) {
            // Todo, Could show a snackbar or dialog here
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Enter your email address and we'll send you a link to reset your password.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = !viewModel.isLoading
        )

        if (viewModel.errorMessage != null) {
            Text(
                text = viewModel.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (viewModel.isPasswordResetSent) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Password reset email sent! Check your inbox.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Button(
            onClick = {
                viewModel.clearErrorMessage()
                viewModel.resetPassword(email)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            enabled = !viewModel.isLoading && email.isNotBlank()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Send Reset Email")
            }
        }

        TextButton(
            onClick = {
                viewModel.clearPasswordResetState()
                onNavigateBack()
            },
            enabled = !viewModel.isLoading
        ) {
            Text("Back to Sign In")
        }
    }
}
