package com.oqurystudio.karanel.android.ui.form

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormChildBinding
import com.oqurystudio.karanel.android.listener.AlertDialogButtonListener
import com.oqurystudio.karanel.android.util.makeToast
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import com.oqurystudio.karanel.android.util.transformIntoDatePicker
import com.oqurystudio.karanel.android.widget.*
import java.util.*

class FormChildFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormChildBinding
    private val mViewModel: FormViewModel by activityViewModels()
    private var isFromParentForm = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormChildBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFromParentForm = FormChildFragmentArgs.fromBundle(arguments as Bundle).isFromParentForm
        mViewBinding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tilName.apply {
                setupEditText(
                    title = "Nama Anak",
                )
                setupErrorState("Silahkan Masukkan Nama Anak")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nama Anak")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.name = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            spinnerGender.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    arrayOf("Laki-laki", "Perempuan")
                )
                setupSpinner(title = "Jenis Kelamin", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.gender = adapter?.getItemAtPosition(position).toString()
                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilBirthPlace.apply {
                setupEditText(
                    title = "Tempat Lahir",
                )
                setupErrorState("Silahkan Masukkan Tempat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Tempat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.birthPlace = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            tilBirthDate.apply {
                setupEditText(
                    title = "Tanggal Lahir",
                    suffixDrawable = R.drawable.ic_calendar
                )
                etCustom.transformIntoDatePicker(requireContext(), "dd/mm/yyyy", Date())
                btnSuffix.setOnClickListener {
                    etCustom.performClick()
                }
                setupErrorState("Silahkan Masukkan Tanggal Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Tanggal Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.birthDate = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            spinnerBirthType.apply {
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrayOf("Tunggal", "Kembar 1", "Kembar 2", "Kembar 3")
                    )
                setupSpinner(title = "Jenis Kelahiran", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.birthType = adapter?.getItemAtPosition(position).toString()
                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilAgeWhenBirth.apply {
                setupEditText(
                    title = "Usia Kehamilan Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                    indicator = "Minggu"
                )
                setupErrorState("Silahkan Masukkan Usia Kehamilan Ibu Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Usia Kehamilan Ibu Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.week = if (!text.isNullOrBlank()) text.toString().toInt() else 0
                    mViewModel.updateFormChildState()
                }
                etCustom.filters = arrayOf(InputFilter.LengthFilter(3))
            }
            tilBodyWeight.apply {
                setupEditText(
                    title = "Berat Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "Kg"
                )
                setupErrorState("Silahkan Masukkan Berat Badan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Berat Badan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.weight = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            tilBodyHeight.apply {
                setupEditText(
                    title = "Panjang Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Panjang Badan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Panjang Badan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.height = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            tilHeadCircumference.apply {
                setupEditText(
                    title = "Lingkar Kepala Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Lingkar Kepala Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Lingkar Kepala Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.headCircumference = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            spinnerBloodType.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    arrayOf("A", "AB", "B", "O")
                )
                setupSpinner(title = "Golongan Darah", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.bloodType = adapter?.getItemAtPosition(position).toString()
                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilChildOrder.apply {
                setupEditText(
                    title = "Urutan Anak",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan Urutan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Urutan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.childOrder = if (!text.isNullOrBlank()) text.toString().toInt() else 0
                    mViewModel.updateFormChildState()
                }
                etCustom.filters = arrayOf(InputFilter.LengthFilter(2))
            }
            btnSubmit.setOnSafeClickListener {
                // TODO HIT API
                // ON SUCCESS SHOW DIALOG
                if (isFromParentForm) {
                    makeToast("from parent")
                } else {
                    DialogFactory.createDialogCodeTracking(
                        requireContext(),
                        "29467ajdhauh4935438",
                        object : AlertDialogButtonListener {
                            override fun onPositiveButtonClicked(dialog: Dialog) {
                                Toast.makeText(requireContext(), "Download Card", Toast.LENGTH_LONG)
                                    .show()
                                dialog.dismiss()
                            }

                            override fun onNegativeButtonCLicked(dialog: Dialog) {
                            }

                        }
                    ).show()
                }
            }
        }
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.isFormChildCompleted.observe(viewLifecycleOwner, {
            mViewBinding.btnSubmit.isEnabled = it
        })
    }
}