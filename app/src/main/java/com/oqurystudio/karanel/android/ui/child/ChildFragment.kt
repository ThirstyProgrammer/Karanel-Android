package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentChildBinding
import com.oqurystudio.karanel.android.model.Child
import com.oqurystudio.karanel.android.model.Gender
import com.oqurystudio.karanel.android.ui.parent.ParentFragmentArgs
import com.oqurystudio.karanel.android.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChildFragment : Fragment() {

    private lateinit var mViewBinding: FragmentChildBinding
    private val mViewModel: ChildViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentChildBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            mViewModel.childId = ChildFragmentArgs.fromBundle(arguments as Bundle).childId.defaultEmpty()
        }
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            btnAddNewChild.setOnSafeClickListener {
                findNavController().navigate(R.id.action_childFragment_to_formProgressFragment)
            }
        }
        handleViewModelObserver()
        mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getChild(it)
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

    private fun setupView(data: Child.Data) {
        mViewBinding.apply {
            tvName.text = data.name
            tvAge.text = Helper.getChildAge(data.birthDate.defaultEmpty())
        }
        setupGender(data.gender.defaultEmpty())
    }

    private fun setupGender(gender: String) {
        when (Gender.getEnum(gender)) {
            Gender.LAKI_LAKI -> {
                mViewBinding.tvGender.apply {
                    text = "Laki-laki"
                    setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.primary_color))
                    background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_male)
                }
            }
            Gender.PEREMPUAN -> {
                mViewBinding.tvGender.apply {
                    text = "Perempuan"
                    setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.red_text))
                    background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_female)
                }
            }
        }

    }
}