package com.oqurystudio.karanel.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.oqurystudio.karanel.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KaranelAndroid)
        setContentView(R.layout.activity_posyandu)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(
            R.navigation.parent_nav_graph,
            intent.extras
        )
    }
}