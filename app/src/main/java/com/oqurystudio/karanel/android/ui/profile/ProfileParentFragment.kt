package com.oqurystudio.karanel.android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentProfileParentBinding
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.setErrorMessage
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileParentFragment : Fragment() {

    private lateinit var mViewBinding: FragmentProfileParentBinding
    private val mViewModel: ProfileParentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentProfileParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewModelObserver()
        mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getParent(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            if (it.data != null) setupView(it.data)
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(data: Parent.Data) {
        mViewBinding.apply {
            tvParent.text = data.motherName
            tvAddress.text = data.address
            tvParentAddress.text = data.address
            tvMotherName.text = data.motherName
            tvMotherNik.text = data.motherNIK
            tvMotherWork.text = data.motherJob
            tvMotherPhone.text = data.motherPhone
            tvFatherName.text = data.fatherName
            tvFatherNik.text = data.fatherNIK
            tvFatherWork.text = data.fatherJob
            tvFatherPhone.text = data.fatherPhone
            btnSignOut.setOnSafeClickListener {
                mViewModel.signOut()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }
}