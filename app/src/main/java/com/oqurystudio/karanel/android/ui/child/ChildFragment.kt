package com.oqurystudio.karanel.android.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentChildBinding
import com.oqurystudio.karanel.android.util.setOnSafeClickListener

class ChildFragment : Fragment() {

    private lateinit var mViewBinding: FragmentChildBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentChildBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            btnAddNewChild.setOnSafeClickListener {
                findNavController().navigate(R.id.action_childFragment_to_formProgressFragment)
            }
        }
    }
}