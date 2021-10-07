package com.oqurystudio.karanel.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.base.BaseFragment
import com.oqurystudio.karanel.android.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

//    private val args: MainFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mViewBinding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
//        if (args.codeVerification != null && args.emailEncrypt != null) {
//            val bundle = Bundle()
//            bundle.putString(Extras.EXTRAS_CURRENT_URL, Constant.APP_DEEPLINK_HOME)
//            bundle.putBoolean(Extras.IS_FORCE_VERIFICATION, false)
//            bundle.putString(Extras.CODE_VERIFICATION, args.codeVerification)
//            bundle.putString(Extras.EMAIL_VERIFICATION, args.emailEncrypt)
//            val dialog = EmailVerificationDialog()
//            dialog.arguments = bundle
//            dialog.show(parentFragmentManager, EmailVerificationDialog::class.java.simpleName)
//        }
    }
}