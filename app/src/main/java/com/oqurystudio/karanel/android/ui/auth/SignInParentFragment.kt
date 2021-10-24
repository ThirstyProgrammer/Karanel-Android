package com.oqurystudio.karanel.android.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentSignInParentBinding
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.widget.hidePassword
import com.oqurystudio.karanel.android.widget.setupEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInParentFragment : Fragment() {

    private lateinit var mViewBinding: FragmentSignInParentBinding
    private val mViewModel: SignInViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentSignInParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            customTilIdParent.apply {
                setupEditText(
                    title = "ID Orang Tua",
                )
                etCustom.doOnTextChanged { text, _, _, _ ->
                    btnSignIn.isEnabled = !text.isNullOrEmpty()
                }
            }
            btnSignIn.setOnClickListener {
                mViewModel.signIn()
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }
        }
    }
}