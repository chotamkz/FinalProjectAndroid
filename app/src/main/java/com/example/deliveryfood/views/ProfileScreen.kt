package com.example.deliveryfood.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.deliveryfood.utils.Constants
import com.example.deliveryfood.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavHostController) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Constants.LOG_IN
        )
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text(text = Constants.LOGIN_TEXT) },
            placeholder = { Text(text = Constants.LOGIN_TEXT) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = Constants.PASSWORD_TEXT) },
            placeholder = { Text(text = Constants.PASSWORD_TEXT) }
        )
        Button(
            onClick = { /*TODO*/ }) {
            Text(text = Constants.SIGN_IN)
        }

        Button(
            onClick = {
                navController.navigate(Constants.REGISTRY)
            }) {
            Text(text = Constants.CREATE_ACCOUNT)
        }
    }
}