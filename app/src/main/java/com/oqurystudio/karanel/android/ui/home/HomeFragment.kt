package com.oqurystudio.karanel.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqurystudio.karanel.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var mViewBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentHomeBinding.inflate(inflater)
        return mViewBinding.root
    }
}