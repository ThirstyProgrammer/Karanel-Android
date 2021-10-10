package com.oqurystudio.karanel.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqurystudio.karanel.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KaranelAndroid)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        handleViewModelObserver()
//        mViewModel.saveAuth()
        mViewModel.getAuth()
    }

    private fun handleViewModelObserver() {
        mViewModel.isLogin.observe(this, {
            if (!it){
                Toast.makeText(this, "NOT LOGIN", Toast.LENGTH_LONG).show()
                // TODO GO TO LOGIN ACTIVITY
            }
        })
    }


}