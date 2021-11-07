package com.oqurystudio.karanel.android.ui.form

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private val mViewModel: FormViewModel by activityViewModels()

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
        mViewBinding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
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
            tilNik.apply {
                setupEditText(
                    title = "NIK",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan NIK")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan NIK")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.parentPayload.nik = text.toString()
                    mViewModel.updateFormParentState()
                }
            }
            btnNext.setOnSafeClickListener {
                val directions =
                    FormParentFragmentDirections.actionFormParentFragmentToFormChildFragment(true)
                findNavController().navigate(directions)
            }
        }
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.isFormParentCompleted.observe(viewLifecycleOwner, {
            mViewBinding.btnNext.isEnabled = it
        })
    }


}