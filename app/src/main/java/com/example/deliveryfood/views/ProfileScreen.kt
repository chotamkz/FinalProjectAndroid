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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp




@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavHostController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Constants.LOG_IN,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text(text = Constants.LOGIN_TEXT) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = Constants.PASSWORD_TEXT) },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),

            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*  */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = Constants.SIGN_IN)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                navController.navigate(Constants.REGISTRY)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = Constants.CREATE_ACCOUNT,
                style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.secondary)
            )
        }
    }
}
