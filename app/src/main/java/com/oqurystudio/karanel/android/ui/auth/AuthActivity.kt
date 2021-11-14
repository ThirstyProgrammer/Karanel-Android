package com.oqurystudio.karanel.android.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.oqurystudio.karanel.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KaranelAndroid)
        setContentView(R.layout.activity_auth)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(
            R.navigation.auth_nav_graph,
            intent.extras
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentFragment = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.first()
        if (currentFragment is SignInOptionsFragment) {
            finishAffinity()
        }
    }
}