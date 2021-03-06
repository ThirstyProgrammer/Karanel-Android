package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentChildBinding
import com.oqurystudio.karanel.android.model.Child
import com.oqurystudio.karanel.android.model.Gender
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
            mViewModel.parentId = ChildFragmentArgs.fromBundle(arguments as Bundle).parentId
        }
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            btnEditChild.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_formChildFragment4,
                        bundleOf("childId" to mViewModel.childId)
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_formChildFragment2,
                        bundleOf("childId" to mViewModel.childId)
                    )
                }
            }
            btnAddProgress.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_formProgressFragment2,
                        bundleOf("childId" to mViewModel.childId)
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_formProgressFragment,
                        bundleOf("childId" to mViewModel.childId, "parentId" to mViewModel.parentId)
                    )
                }
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
            tvBbuStatus.text = data.status?.bbu.defaultDash()
            tvPbuStatus.text = data.status?.pbu.defaultDash()
            tvBbtbStatus.text = data.status?.bbtb.defaultDash()
            tvLkuStatus.text = data.status?.lku.defaultDash()
            containerBbu.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_ChartBbuFragment,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_ChartBbuFragment2,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                }
            }
            containerPbu.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_chartPbuFragment,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_chartPbuFragment2,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                }
            }
            containerBbpb.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_chartBbpbFragment,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_chartBbpbFragment2,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                }
            }
            containerLku.setOnSafeClickListener {
                if (mViewModel.parentId.isBlank()) {
                    findNavController().navigate(
                        R.id.action_childFragment2_to_chartLkuFragment,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_childFragment_to_chartLkuFragment2,
                        bundleOf(
                            "childId" to mViewModel.childId,
                            "parentId" to mViewModel.parentId
                        )
                    )
                }
            }
        }
        setupGender(data.gender.defaultEmpty())
    }

    private fun setupGender(gender: String) {
        when (Gender.getEnum(gender)) {
            Gender.LAKI_LAKI -> {
                mViewBinding.apply {
                    ivAvatar.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.il_profile_male))
                    tvGender.apply {
                        text = "Laki-laki"
                        setTextColor(ContextCompat.getColor(root.context, R.color.primary_color))
                        background = ContextCompat.getDrawable(root.context, R.drawable.bg_rounded_male)
                    }
                }
            }
            Gender.PEREMPUAN -> {
                mViewBinding.apply {
                    ivAvatar.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.il_profile_female))
                    tvGender.apply {
                        text = "Perempuan"
                        setTextColor(ContextCompat.getColor(root.context, R.color.red_text))
                        background = ContextCompat.getDrawable(root.context, R.drawable.bg_rounded_female)
                    }
                }
            }
        }
    }
}