package com.oqurystudio.karanel.android.ui.parents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oqurystudio.karanel.android.databinding.FragmentParentsBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parents
import com.oqurystudio.karanel.android.ui.form.FormActivity
import com.oqurystudio.karanel.android.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentsFragment : Fragment(), OnItemClickListener {

    private lateinit var mViewBinding: FragmentParentsBinding
    private lateinit var mAdapter: ParentsAdapter
    private val mViewModel: ParentsViewModel by viewModels()
    private var isLoading: Boolean = false

    override fun onItemClicked(v: View, position: Int) {
        val toPosyanduActivity = ParentsFragmentDirections.actionParentsFragmentToPosyanduActivity(mAdapter.getItem(position).id.defaultEmpty())
        findNavController().navigate(toPosyanduActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentParentsBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ParentsAdapter()
        mAdapter.setOnItemClickListener(this)
        mViewBinding.apply {
            containerSearch.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                        mViewModel.getParents()
                        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        return true
                    }
                    return false
                }

            })
            containerSearch.etSearch.doOnTextChanged { text, _, _, _ ->
                mViewModel.updateQuery(text.toString())
            }
            containerSearch.btnSuffix.setOnSafeClickListener {
                mViewModel.getParents()
            }
            val linearLayoutManager = LinearLayoutManager(requireContext())
            rvParents.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = mAdapter
                if (itemDecorationCount == 0) {
                    addItemDecoration(MarginItemDecoration(ViewUtil.dpToPx(8), MarginItemDecoration.RvType.RV_VERTICAL, false))
                }
                addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                    override fun isLastPage(): Boolean = !mViewModel.isNextUrlAvailable()

                    override fun isLoading(): Boolean = isLoading

                    override fun loadMoreItems() {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.itemCount.minus(1)) {
                            loadMore()
                        }
                    }

                })
            }
            addParent.setOnClickListener {
                val intent = Intent(requireActivity(), FormActivity::class.java)
                startActivity(intent)
            }
        }
        mViewModel.getToken()
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getParents(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            isLoading = false
            if (it.data.isNullOrEmpty()) {
                mViewBinding.containerEmptySearch.visibility = View.VISIBLE
                mViewBinding.rvParents.visibility = View.GONE
            } else {
                mViewBinding.containerEmptySearch.visibility = View.GONE
                mViewBinding.rvParents.visibility = View.VISIBLE
            }
            if (it.meta?.last_page.defaultZero() > it.meta?.current_page.defaultZero()) {
                mViewModel.updateNextPage(it.meta?.current_page.defaultZero() + 1)
            } else {
                mViewModel.updateNextPage(0)
            }
            setupView(it)
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            if (!isLoading) mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            if (!isLoading) mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(response: Parents.Response?) {
        mAdapter.updateData(
            response?.data ?: arrayListOf(),
            (response?.meta?.last_page ?: 0 > response?.meta?.current_page ?: 0),
            response?.meta?.current_page.defaultZero() > 1
        )
    }

    private fun loadMore() {
        isLoading = true
        mViewModel.loadMoreParents()
    }
}