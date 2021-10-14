package com.zaka.features.login.loginscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login


    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginBinding.bind(inflateView)

        _binding!!.btnLogin!!.text="hi login"
        initEventHandler()
    }

    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_loginOTPFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}