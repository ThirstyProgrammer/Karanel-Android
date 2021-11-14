package com.oqurystudio.karanel.android.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentSignInPosyanduBinding
import com.oqurystudio.karanel.android.model.UserType
import com.oqurystudio.karanel.android.network.ViewState
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.util.*
import com.oqurystudio.karanel.android.widget.hidePassword
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupErrorState
import com.oqurystudio.karanel.android.widget.setupNormalState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SignInPosyanduFragment : Fragment() {

    private lateinit var mViewBinding: FragmentSignInPosyanduBinding
    private val mViewModel: SignInViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentSignInPosyanduBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            customTilEmail.apply {
                setupEditText(
                    title = "Email",
                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                )
                etCustom.doOnTextChanged { text, _, _, _ ->
                    mViewModel.updateEmail(text.toString())
                    if (RegexUtil.checkRegex(text.toString(), RegexUtil.EMAIL_PATTERN)) {
                        setupNormalState()
                    } else {
                        setupErrorState("Silahkan Masukkan Email yang Valid")
                    }
                }
            }
            customTilPassword.apply {
                setupEditText(
                    title = "Password",
                    inputType = InputType.TYPE_CLASS_TEXT
                )
                etCustom.doOnTextChanged { text, _, _, _ ->
                    mViewModel.updatePassword(text.toString())
                }
                hidePassword()
            }
            btnSignIn.setOnClickListener {
                mViewModel.signInPosyandu()
            }
        }
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.isSignInEnable.observe(viewLifecycleOwner, {
            mViewBinding.btnSignIn.isEnabled = it
        })
        mViewModel.users.observe(viewLifecycleOwner, {
            if (it.data != null) {
                mViewModel.updateUserPreferences(it.data, UserType.POSYANDU)
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            } else {
                makeToast(it.stat_msg)
            }
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            if (it.first == ViewState.INVALID_LOGIN) {
                makeToast("Email/Password Tidak Tepat")
            }
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }
}