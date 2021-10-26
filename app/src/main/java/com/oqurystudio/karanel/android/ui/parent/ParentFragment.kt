package com.oqurystudio.karanel.android.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentParentBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.util.setOnSafeClickListener

class ParentFragment : Fragment(), OnItemClickListener {

    private lateinit var mViewBinding: FragmentParentBinding
    private lateinit var mAdapter: ChildAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onItemClicked(v: View, position: Int) {
        findNavController().navigate(R.id.action_parentFragment_to_childFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ChildAdapter()
        mAdapter.setOnItemClickListener(this)
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
            val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvChilds.apply {
                layoutManager = gridLayoutManager
                adapter = mAdapter
            }
            mAdapter.setData(
                arrayListOf(
                    "Randi",
                    "Andi",
                    "Apri",
                    "Rian"
                )
            )
            btnAddNewChild.setOnSafeClickListener {
                findNavController().navigate(R.id.action_parentFragment_to_formChildFragment2)
            }
        }
    }
}