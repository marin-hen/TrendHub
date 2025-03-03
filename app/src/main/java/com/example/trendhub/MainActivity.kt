package com.example.trendhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.designsystem.ThemePreferenceProvider
import com.example.core.designsystem.theme.TrendHubTheme
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

    @Inject
    lateinit var themePreferenceProvider: ThemePreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme by themePreferenceProvider.isDarkTheme.collectAsState()
            TrendHubTheme(darkTheme = darkTheme) {
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
            modifier = Modifier.consumeWindowInsets(paddingValues = paddingValues)
        ) {

            navGraphProviders.forEach { provider ->
                provider.provideGraph(this)
            }
        }
    }
}