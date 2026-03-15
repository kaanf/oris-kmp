package com.kaanf.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kaanf.auth.presentation.register.RegisterRoot
import com.kaanf.auth.presentation.register_verification.RegisterVerificationRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Register,
    ) {
        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(
                        AuthGraphRoutes.RegisterVerification(it),
                    )
                },
            )
        }
        composable<AuthGraphRoutes.RegisterVerification> {
            RegisterVerificationRoot()
        }
    }
}
