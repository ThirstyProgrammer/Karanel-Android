package com.oqurystudio.karanel.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.databinding.FragmentHomeBinding
import com.oqurystudio.karanel.android.model.DashboardPosyandu
import com.oqurystudio.karanel.android.network.ViewState
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.util.defaultEmpty
import com.oqurystudio.karanel.android.util.handleViewState
import com.oqurystudio.karanel.android.util.setErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var mViewBinding: FragmentHomeBinding
    private val mViewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentHomeBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewModelObserver()
        mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getDashboardData(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            setupView(it.data)
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            if (it.first == ViewState.UNAUTHORIZED) {
                mViewModel.signOut()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                mViewBinding.viewState.handleViewState(it.first, it.second)
            }
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(data: DashboardPosyandu.Data?) {
        mViewBinding.apply {
            tvPosyandu.text = data?.posyandu?.name.defaultEmpty()
            tvAddress.text = "${data?.posyandu?.address.defaultEmpty()}, ${data?.posyandu?.city.defaultEmpty()}"
            tvTotalChild.text = data?.child.toString()
            tvTotalParent.text = data?.parent.toString()
            tvTotalHealthy.text = data?.healthy.toString()
            tvTotalStunting.text = data?.stunting.toString()
        }
    }
}