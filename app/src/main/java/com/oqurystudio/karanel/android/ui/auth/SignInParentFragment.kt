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
import com.oqurystudio.karanel.android.model.UserType
import com.oqurystudio.karanel.android.network.ViewState
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.makeToast
import com.oqurystudio.karanel.android.util.setErrorMessage
import com.oqurystudio.karanel.android.widget.hidePassword
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupErrorState
import com.oqurystudio.karanel.android.widget.setupNormalState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

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
                    mViewModel.updateIdParent(text.toString())
                    setupNormalState()
                }
            }
            btnSignIn.setOnClickListener {
                mViewModel.signInParent()
            }
        }
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.isSignInEnable.observe(viewLifecycleOwner, {
            mViewBinding.btnSignIn.isEnabled = it
        })
        mViewModel.parent.observe(viewLifecycleOwner, {
            if (it.data != null) {
                mViewModel.updateUserPreferences(it.data, UserType.PARENT)
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            } else {
                makeToast(it.stat_msg)
            }
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            if (it.first == ViewState.INVALID_LOGIN) {
                makeToast("ID Tidak Ditemukan")
            }
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }
}