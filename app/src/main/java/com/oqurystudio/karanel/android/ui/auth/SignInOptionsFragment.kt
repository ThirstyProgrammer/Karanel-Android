package com.oqurystudio.karanel.android.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentSignInOptionsBinding

class SignInOptionsFragment : Fragment() {

    private lateinit var mViewBinding: FragmentSignInOptionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentSignInOptionsBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.apply {
            btnParent.setOnClickListener {
                findNavController().navigate(R.id.action_signInOptionsFragment_to_signInParentFragment)
            }
            btnPosyandu.setOnClickListener {
                findNavController().navigate(R.id.action_signInOptionsFragment_to_signInPosyanduFragment)
            }
        }
    }
}