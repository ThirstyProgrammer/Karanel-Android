package com.oqurystudio.karanel.android.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.oqurystudio.karanel.android.databinding.FragmentParentBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentFragment : Fragment(), OnItemClickListener {

    private lateinit var mViewBinding: FragmentParentBinding
    private lateinit var mAdapter: ChildAdapter
    private val mViewModel: ParentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getString(Constant.Extras.PARENT_ID)
    }

    override fun onItemClicked(v: View, position: Int) {
        val directions = ParentFragmentDirections.actionParentFragmentToChildFragment(
            childId = mAdapter.getItem(position).id.toString(),
            parentId = (mViewModel.parentId)
        )
        findNavController().navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            mViewModel.parentId = ParentFragmentArgs.fromBundle(arguments as Bundle).idParent.defaultEmpty()
        }
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
            btnAddNewChild.setOnSafeClickListener {
                val directions = ParentFragmentDirections.actionParentFragmentToFormChildFragment2(false, mViewModel.parentId)
                findNavController().navigate(directions)
            }
        }
        handleViewModelObserver()
        mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getParent(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            if (it.data != null) setupView(it.data)
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(data: Parent.Data) {
        mViewBinding.apply {
            tvMotherName.text = data.motherName
            tvFatherName.text = data.fatherName
            tvAddress.text = data.address
        }
        mAdapter.setData(data.children ?: arrayListOf())
    }
}