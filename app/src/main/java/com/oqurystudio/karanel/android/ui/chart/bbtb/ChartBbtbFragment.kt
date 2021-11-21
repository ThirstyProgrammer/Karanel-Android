package com.oqurystudio.karanel.android.ui.chart.bbtb

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentPbuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartBbtbFragment : Fragment() {

    private lateinit var mViewBinding: FragmentPbuBinding
    private val mViewModel: ChartBbtbViewModel by viewModels()
}