package com.example.core.di

import androidx.navigation.NavController
import com.example.core.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindNavigator(root: RootNavigator): Navigator
}

@Singleton
class RootNavigator @Inject constructor() : Navigator {
    lateinit var navController: NavController

    override fun doAction(block: NavController.() -> Unit) {
        navController.block()
    }

    override fun navigateBack(): Boolean = navController.navigateUp()
}