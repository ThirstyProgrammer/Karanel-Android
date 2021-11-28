package com.oqurystudio.karanel.android.ui.form

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormParentBinding
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.setErrorMessage
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupErrorState
import com.oqurystudio.karanel.android.widget.setupNormalState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormParentFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormParentBinding
    private val mViewModel: FormParentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.isEditParentData = FormParentFragmentArgs.fromBundle(arguments as Bundle).isEditParentData
        mViewBinding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tilNikMother.apply {
                setupEditText(
                    title = "NIK Ibu",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan NIK")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan NIK")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.motherNIK = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilMotherName.apply {
                setupEditText(
                    title = "Nama Ibu",
                )
                setupErrorState("Silahkan Masukkan Nama Ibu")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nama Ibu")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.motherName = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilMotherWork.apply {
                setupEditText(
                    title = "Pekerjaan Ibu",
                )
                setupErrorState("Silahkan Masukkan Pekerjaan Ibu")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Pekerjaan Ibu")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.motherWork = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilMotherPhone.apply {
                setupEditText(
                    title = "Nomor Handphone Ibu",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan Nomor Handphone Ibu")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nomor Handphone Ibu")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.motherPhone = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilNikFather.apply {
                setupEditText(
                    title = "NIK Ayah",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan NIK")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan NIK")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.fatherNIK = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilFatherName.apply {
                setupEditText(
                    title = "Nama Ayah",
                )
                setupErrorState("Silahkan Masukkan Nama Ayah")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nama Ayah")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.fatherName = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilFatherWork.apply {
                setupEditText(
                    title = "Pekerjaan Ayah",
                )
                setupErrorState("Silahkan Masukkan Pekerjaan Ayah")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Pekerjaan Ayah")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.fatherWork = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilFatherPhone.apply {
                setupEditText(
                    title = "Nomor Handphone Ayah",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan Nomor Handphone Ayah")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nomor Handphone Ayah")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.fatherPhone = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            tilAddress.apply {
                setupEditText(
                    title = "Alamat",
                )
                setupErrorState("Silahkan Masukkan Alamat")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Alamat")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.address = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            if (mViewModel.isEditParentData) {
                btnNext.text = getString(R.string.save)
                btnNext.setOnSafeClickListener {
                    mViewModel.updateParentData()
                }
            } else {
                btnNext.text = getString(R.string.next)
                btnNext.setOnSafeClickListener {
                    val directions =
                        FormParentFragmentDirections.actionFormParentFragmentToFormChildFragment(
                            true,
                            parentPayload = mViewModel.parentPayload
                        )
                    findNavController().navigate(directions)
                }
            }
        }
        handleViewModelObserver()
        if (mViewModel.isEditParentData) mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.isFormParentCompleted.observe(viewLifecycleOwner, {
            mViewBinding.btnNext.isEnabled = it
        })
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getParent(it)
        })
        mViewModel.responseGetParent.observe(viewLifecycleOwner, {
            mViewModel.updateParentPayload(it.data)
            updateField()
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun updateField() {
        mViewBinding.apply {
            tilNikMother.etCustom.setText(mViewModel.parentPayload.motherNIK)
            tilMotherName.etCustom.setText(mViewModel.parentPayload.motherName)
            tilMotherPhone.etCustom.setText(mViewModel.parentPayload.motherWork)
            tilMotherWork.etCustom.setText(mViewModel.parentPayload.motherPhone)
            tilNikFather.etCustom.setText(mViewModel.parentPayload.fatherNIK)
            tilFatherName.etCustom.setText(mViewModel.parentPayload.fatherName)
            tilFatherPhone.etCustom.setText(mViewModel.parentPayload.fatherWork)
            tilFatherWork.etCustom.setText(mViewModel.parentPayload.fatherPhone)
            tilAddress.etCustom.setText(mViewModel.parentPayload.address)
        }
    }


}