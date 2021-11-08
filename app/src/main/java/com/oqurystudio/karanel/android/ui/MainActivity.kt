package com.oqurystudio.karanel.android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.model.UserType
import com.oqurystudio.karanel.android.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KaranelAndroid)
//        setContentView(R.layout.activity_main)
//
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        navView.setupWithNavController(navController)

        handleViewModelObserver()
        mViewModel.getAuth()
    }

    private fun handleViewModelObserver() {
        val loginObserver = Observer<Boolean> {
            if (!it) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            } else {
                mViewModel.getUserType()
            }
        }
        mViewModel.isLogin.observe(this, loginObserver)
        mViewModel.userType.observe(this, {
            mViewModel.isLogin.removeObserver(loginObserver)
            when (it) {
                UserType.PARENT -> {
                    setContentView(R.layout.activity_main_parent)
                }
                UserType.POSYANDU -> {
                    setContentView(R.layout.activity_main)
                }
            }

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        })
    }


}