package com.oqurystudio.karanel.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.oqurystudio.karanel.android.databinding.FragmentHomeParentBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.ui.form.FormChildActivity
import com.oqurystudio.karanel.android.ui.parent.ChildAdapter
import com.oqurystudio.karanel.android.util.Constant
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.setErrorMessage
import com.oqurystudio.karanel.android.util.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeParentFragment : Fragment(), OnItemClickListener {

    private lateinit var mViewBinding: FragmentHomeParentBinding
    private lateinit var mAdapter: ChildAdapter
    private val mViewModel: HomeParentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentHomeParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getString(Constant.Extras.PARENT_ID)
    }

    override fun onItemClicked(v: View, position: Int) {
//        findNavController().navigate(R.id.action_parentFragment_to_childFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ChildAdapter()
        mAdapter.setOnItemClickListener(this)
        mViewBinding.apply {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvChilds.apply {
                layoutManager = gridLayoutManager
                adapter = mAdapter
            }
            btnAddNewChild.setOnSafeClickListener {
                val intent = Intent(requireActivity(), FormChildActivity::class.java)
                startActivity(intent)
                // TODO Update Journey
//                val directions = ParentFragmentDirections.actionParentFragmentToFormChildFragment2(false, )
//                findNavController().navigate(directions)
            }
        }
        mViewModel.getToken()
        handleViewModelObserver()
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
            tvAddress.text = data.address
        }
        mAdapter.setData(data.children ?: arrayListOf())
    }
}