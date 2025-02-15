package com.example.trendhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.designsystem.TrendHubTheme
import com.example.core.di.RootNavigator
import com.example.core.navigation.NavGraphProvider
import com.example.trending.ui.navigation.TrendingScreenRoute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphProviders: Set<@JvmSuppressWildcards NavGraphProvider>

    @Inject
    lateinit var rootNavigator: RootNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrendHubTheme {
                val navController = rememberNavController()

                rootNavigator.navController = navController

                MainScreen(
                    navController = navController,
                    navGraphProviders = navGraphProviders
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    navGraphProviders: Set<NavGraphProvider>
) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TrendingScreenRoute,
            modifier = Modifier.padding(paddingValues)
        ) {

            navGraphProviders.forEach { provider ->
                provider.provideGraph(this)
            }
        }
    }
}