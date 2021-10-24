package com.oqurystudio.karanel.android.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormParentBinding
import com.oqurystudio.karanel.android.widget.setupEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormParentFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormParentBinding
    private val mViewModel: FormViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
            }
            tilMotherWork.apply {
                setupEditText(
                    title = "Pekerjaan Ibu",
                )
            }
            tilMotherPhone.apply {
                setupEditText(
                    title = "Nomor Handphone Ibu",
                )
            }
            tilFatherName.apply {
                setupEditText(
                    title = "Nama Ayah",
                )
            }
            tilFatherWork.apply {
                setupEditText(
                    title = "Pekerjaan Ayah",
                )
            }
            tilFatherPhone.apply {
                setupEditText(
                    title = "Nomor Handphone Ayah",
                )
            }
            tilAddress.apply {
                setupEditText(
                    title = "Alamat",
                )
            }
            tilNik.apply {
                setupEditText(
                    title = "NIK",
                )
            }
            btnNext.setOnClickListener {
                findNavController().navigate(R.id.action_formParentFragment_to_formChildFragment)
            }
        }
    }
}