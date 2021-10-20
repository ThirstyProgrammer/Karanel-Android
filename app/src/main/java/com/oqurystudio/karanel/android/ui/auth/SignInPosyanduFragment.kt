package com.oqurystudio.karanel.android.ui.auth

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqurystudio.karanel.android.databinding.FragmentSignInPosyanduBinding
import com.oqurystudio.karanel.android.widget.hidePassword
import com.oqurystudio.karanel.android.widget.setupEditText

class SignInPosyanduFragment : Fragment() {

    private lateinit var mViewBinding: FragmentSignInPosyanduBinding

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
            }
            customTilPassword.apply {
                setupEditText(
                    title = "Password",
                    inputType = InputType.TYPE_CLASS_TEXT
                )
                hidePassword()
            }
        }
    }
}