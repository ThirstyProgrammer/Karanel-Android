package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormProgressBinding
import com.oqurystudio.karanel.android.util.*
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupErrorState
import com.oqurystudio.karanel.android.widget.setupNormalState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FormProgressFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormProgressBinding
    private val mViewModel: FormProgressViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormProgressBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            mViewModel.childId = FormProgressFragmentArgs.fromBundle(arguments as Bundle).childId.defaultEmpty()
            mViewModel.parentId = FormProgressFragmentArgs.fromBundle(arguments as Bundle).parentId
            mViewModel.recordId = FormProgressFragmentArgs.fromBundle(arguments as Bundle).recordId
        }
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            tilGrowthDate.apply {
                setupEditText(
                    title = "Tanggal Pemeriksaan",
                    suffixDrawable = R.drawable.ic_calendar
                )
                etCustom.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())
                btnSuffix.setOnClickListener {
                    etCustom.performClick()
                }
                setupErrorState("Silahkan Masukkan Tanggal Pemeriksaan")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Tanggal Pemeriksaan")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.progressPayload.growthDate = changeDateFormat(text.toString())
                    mViewModel.updateFormProgressState()
                }
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
                if (mViewModel.recordId.isBlank()) {
                    mViewModel.getToken()
                } else {
                    mViewModel.updateProgress()
                }
            }
        }
        handleViewModelObserver()
        if (mViewModel.recordId.isNotBlank()) mViewModel.getToken()
    }

    private fun updateField() {
        mViewBinding.apply {
            if (mViewModel.progressPayload.growthDate.isNotBlank()){
                tilGrowthDate.apply {
                    etCustom.setText(changeBackDateFormat(mViewModel.progressPayload.growthDate))
                    val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val date = try {
                        output.parse(mViewModel.progressPayload.growthDate)
                    } catch (e: Exception) {
                        Date()
                    }
                    etCustom.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", date)
                }
            }
            tilBodyHeight.etCustom.setText(mViewModel.progressPayload.record.height.toString())
            tilBodyWeight.etCustom.setText(mViewModel.progressPayload.record.weight.toString())
            tilHeadCircumference.etCustom.setText(mViewModel.progressPayload.record.headCircumference.toString())
        }
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            if (mViewModel.recordId.isBlank()) {
                mViewModel.submitProgress(it)
            } else {
                mViewModel.getRecord(it)
            }
        })
        mViewModel.responseGetRecord.observe(viewLifecycleOwner, {
            mViewModel.updateProgressPayload(it.data)
            updateField()
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            makeToast("Data Progress Telah Ditambahkan")
            requireActivity().onBackPressed()
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

    private fun changeDateFormat(text: String): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val data = sdf.parse(text)
        return output.format(data)
    }

    private fun changeBackDateFormat(text: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val output = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val data = sdf.parse(text)
        return output.format(data)
    }
}