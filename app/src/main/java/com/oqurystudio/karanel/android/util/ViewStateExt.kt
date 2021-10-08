package com.oqurystudio.karanel.android.util

import android.view.View
import com.oqurystudio.karanel.android.databinding.ViewStateBinding
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.network.ViewState

fun ViewStateBinding.handleViewState(state: ViewState, requestType: NetworkRequestType) {
    handleLoadingType(requestType)
    when (state) {
        ViewState.LOADING -> {
            containerViewState.visibility = View.VISIBLE
            containerError.visibility = View.GONE
            containerLoading.visibility = View.VISIBLE
        }
        ViewState.SUCCESS -> {
            containerViewState.visibility = View.GONE
            containerError.visibility = View.GONE
            containerLoading.visibility = View.GONE
        }
        ViewState.ERROR -> {
            containerViewState.visibility = View.VISIBLE
            containerError.visibility = View.VISIBLE
            containerLoading.visibility = View.GONE
        }
    }
}

fun ViewStateBinding.setErrorMessage(message: String) {
    tvErrorMessage.text = message
}

fun ViewStateBinding.setOnRetakeClicked(onClick: () -> Unit) {
    btnRetake.setOnClickListener {
        onClick()
    }
}

private fun ViewStateBinding.handleLoadingType(requestType: NetworkRequestType) {
    when (requestType) {
//        NetworkRequestType.USER_DETAIL -> {
//            layoutShimmerUsers.containerMain.visibility = View.GONE
//            layoutShimmerUserDetail.containerMain.visibility = View.VISIBLE
//        }
//        NetworkRequestType.LIST_USERS -> {
//            layoutShimmerUsers.containerMain.visibility = View.VISIBLE
//            layoutShimmerUserDetail.containerMain.visibility = View.GONE
//        }
    }
}