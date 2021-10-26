package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqurystudio.karanel.android.databinding.FragmentFormProgressBinding
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import com.oqurystudio.karanel.android.widget.setupEditText

class FormProgressFragment : Fragment() {

    private lateinit var mViewBinding: FragmentFormProgressBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormProgressBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            tilBodyWeight.apply {
                setupEditText(
                    title = "Berat Badan Lahir",
                    indicator = "Kg"
                )
            }
            tilBodyHeight.apply {
                setupEditText(
                    title = "Panjang Badan Lahir",
                    indicator = "cm"
                )
            }
            tilHeadCircumference.apply {
                setupEditText(
                    title = "Lingkar Kepala Saat Lahir",
                    indicator = "cm"
                )
            }
            btnCancel.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            btnSubmit.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}