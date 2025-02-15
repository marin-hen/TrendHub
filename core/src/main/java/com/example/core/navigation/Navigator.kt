package com.example.core.navigation

import androidx.navigation.NavController

interface Navigator {
    fun doAction(block: NavController.() -> Unit)
    fun navigateBack(): Boolean
}