package com.zaka.features.login.loginscreen

import android.content.Intent
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
import com.zaka.features.main.MainActivity

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login


    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginBinding.bind(inflateView)

        //_binding!!.btnLogin!!.text="hi login"
        initEventHandler()
    }

    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnLogin?.setOnClickListener {
            startActivityWithFading(Intent(requireContext(),MainActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}