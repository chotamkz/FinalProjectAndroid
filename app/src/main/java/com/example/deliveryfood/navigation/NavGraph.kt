package com.example.deliveryfood.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.deliveryfood.utils.Constants
import com.example.deliveryfood.viewmodels.CartViewModel
import com.example.deliveryfood.viewmodels.MenuViewModel
import com.example.deliveryfood.viewmodels.ProfileViewModel
import com.example.deliveryfood.views.CartScreen
import com.example.deliveryfood.views.MenuScreen
import com.example.deliveryfood.views.ProfileScreen
import com.example.deliveryfood.views.RegistryScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Menu.route
    ) {
        composable(route = BottomBarScreen.Menu.route) {
            MenuScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(profileViewModel, navController)        }
        composable(route = BottomBarScreen.Cart.route) {
            CartScreen()
        }
        composable(route = Constants.REGISTRY) {
            RegistryScreen()// This is your registration screen composable
        }
    }
}