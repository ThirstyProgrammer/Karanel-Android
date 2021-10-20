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
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.widget.hidePassword
import com.oqurystudio.karanel.android.widget.setupEditText
import dagger.hilt.android.AndroidEntryPoint

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
                mViewModel.signIn()
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }
        }
        mViewModel.isSignInEnable.observe(viewLifecycleOwner, {
            mViewBinding.btnSignIn.isEnabled = it
        })
    }
}