package com.oqurystudio.karanel.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.ActivityNavigationBinding

abstract class BaseNavigationActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mBinding: ActivityNavigationBinding

    abstract fun setupNavigation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KaranelAndroid)
        mBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupNavigation()
    }
}