package com.oqurystudio.karanel.android.ui.parents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqurystudio.karanel.android.databinding.FragmentParentsBinding

class ParentsFragment : Fragment() {

    private lateinit var mViewBinding: FragmentParentsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentParentsBinding.inflate(inflater)
        return mViewBinding.root
    }
}