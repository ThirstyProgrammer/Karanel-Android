package com.oqurystudio.karanel.android.ui.parents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oqurystudio.karanel.android.databinding.FragmentParentsBinding
import com.oqurystudio.karanel.android.ui.auth.AuthActivity
import com.oqurystudio.karanel.android.ui.form.FormActivity
import com.oqurystudio.karanel.android.util.MarginItemDecoration
import com.oqurystudio.karanel.android.util.ViewUtil

class ParentsFragment : Fragment() {

    private lateinit var mViewBinding: FragmentParentsBinding
    private lateinit var mAdapter: ParentsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentParentsBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ParentsAdapter()
        mViewBinding.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            rvParents.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = mAdapter
                if (itemDecorationCount == 0) {
                    addItemDecoration(MarginItemDecoration(ViewUtil.dpToPx(8), MarginItemDecoration.RvType.RV_VERTICAL, false))
                }
            }
            addParent.setOnClickListener {
                val intent = Intent(requireActivity(), FormActivity::class.java)
                startActivity(intent)
            }
        }
        mAdapter.setData(
            arrayListOf(
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
                "Randi Apriansyah",
            )
        )
    }
}