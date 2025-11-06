package com.asistentedelmago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asistentedelmago.feature.repertory.ui.AddTrickScreen
import com.asistentedelmago.feature.repertory.ui.ArsenalScreen
import com.asistentedelmago.ui.theme.AsistenteDelMagoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsistenteDelMagoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "arsenal"
                    ) {
                        composable("arsenal") {
                            ArsenalScreen(
                                onNavigateToAddTrick = {
                                    navController.navigate("add_trick")
                                }
                            )
                        }
                        composable("add_trick") {
                            AddTrickScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

