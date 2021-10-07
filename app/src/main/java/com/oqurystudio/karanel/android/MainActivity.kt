package com.oqurystudio.karanel.android

import android.os.Bundle
import com.oqurystudio.karanel.android.base.BaseNavigationActivity

class MainActivity : BaseNavigationActivity() {

    override fun setupNavigation() {
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }
}