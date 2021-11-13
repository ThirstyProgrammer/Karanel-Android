package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentFormProgressBinding
import com.oqurystudio.karanel.android.util.defaultEmpty
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.setErrorMessage
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupErrorState
import com.oqurystudio.karanel.android.widget.setupNormalState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormProgressFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormProgressBinding
    private val mViewModel: FormProgressViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormProgressBinding.inflate(inflater)
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
            tilBodyWeight.apply {
                setupEditText(
                    title = "Berat Badan",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "Kg"
                )
                setupErrorState("Silahkan Masukkan Berat Badan Anak")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Berat Badan Anak")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.progressPayload.record.weight = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormProgressState()
                }
            }
            tilBodyHeight.apply {
                setupEditText(
                    title = "Panjang/Tinggi Badan",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Panjang/Tinggi Badan Anak")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Panjang/Tinggi Badan Anak")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.progressPayload.record.height = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormProgressState()
                }
            }
            tilHeadCircumference.apply {
                setupEditText(
                    title = "Lingkar Kepala",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Lingkar Kepala Anak")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Lingkar Kepala Anak")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.progressPayload.record.headCircumference = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormProgressState()
                }
            }
            btnCancel.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            btnSubmit.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
        }
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.submitProgress(it)
        })
        mViewModel.isFormProgressCompleted.observe(viewLifecycleOwner, {
            mViewBinding.btnSubmit.isEnabled = it
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }
}