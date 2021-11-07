package com.oqurystudio.karanel.android.ui.form

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormChildBinding
import com.oqurystudio.karanel.android.listener.AlertDialogButtonListener
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import com.oqurystudio.karanel.android.util.transformIntoDatePicker
import com.oqurystudio.karanel.android.widget.DialogFactory
import com.oqurystudio.karanel.android.widget.setupEditText
import com.oqurystudio.karanel.android.widget.setupSpinner
import java.util.*

class FormChildFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormChildBinding
    private val mViewModel: FormViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormChildBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tilName.apply {
                setupEditText(
                    title = "Nama Anak",
                )
            }
            spinnerGender.apply {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Laki-laki", "Perempuan"))
                setupSpinner(title = "Jenis Kelamin", adapter)
            }
            tilBirthDate.apply {
                setupEditText(
                    title = "Tempat, Tanggal Lahir",
                    suffixDrawable = R.drawable.ic_calendar
                )
                etCustom.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())
                btnSuffix.setOnClickListener {
                    etCustom.performClick()
                }
            }
            spinnerBirthType.apply {
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Tunggal", "Kembar 1", "Kembar 2", "Kembar 3"))
                setupSpinner(title = "Jenis Kelahiran", adapter)
            }
            tilAgeWhenBirth.apply {
                setupEditText(
                    title = "Usia Kehamilan Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                    indicator = "Minggu"
                )
            }
            tilBodyWeight.apply {
                setupEditText(
                    title = "Berat Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "Kg"
                )
            }
            tilBodyHeight.apply {
                setupEditText(
                    title = "Panjang Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
            }
            tilHeadCircumference.apply {
                setupEditText(
                    title = "Lingkar Kepala Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
            }
            spinnerBloodType.apply {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("A", "AB", "B", "O"))
                setupSpinner(title = "Golongan Darah", adapter)
            }
            tilChildOrder.apply {
                setupEditText(
                    title = "Urutan Anak",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
            }
            btnSubmit.setOnSafeClickListener {
                // TODO HIT API
                // ON SUCCESS SHOW DIALOG
                DialogFactory.createDialogCodeTracking(
                    requireContext(),
                    "29467ajdhauh4935438",
                    object : AlertDialogButtonListener {
                        override fun onPositiveButtonClicked(dialog: Dialog) {
                            Toast.makeText(requireContext(), "Download Card", Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }

                        override fun onNegativeButtonCLicked(dialog: Dialog) {
                        }

                    }
                ).show()
            }
        }
    }
}