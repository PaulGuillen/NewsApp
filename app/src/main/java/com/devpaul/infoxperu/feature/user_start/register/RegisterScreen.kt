package com.devpaul.infoxperu.feature.user_start.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme

@Composable
fun RegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Al momento de registrarte podrás mantenerte al tanto de los cambios realizados",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO */ },
                    label = { Text("Nombre") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO */ },
                    label = { Text("Apellido") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO */ },
                    label = { Text("Correo") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO */ },
                    label = { Text("Contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO */ },
                    label = { Text("Confirmar contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Handle register action */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RectangleShape,
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Registrarse")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { navController.navigate(Screen.Login.route) }
        ) {
            Text("¿Ya tienes cuenta?")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    InfoXPeruTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
