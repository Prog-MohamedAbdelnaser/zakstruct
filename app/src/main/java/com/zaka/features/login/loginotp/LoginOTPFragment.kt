package com.zaka.features.login.loginotp

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentLoginOtpBinding
import com.zaka.databinding.FragmentReportsBinding
import com.zaka.features.main.MainActivity

class LoginOTPFragment : BaseFragment() {

    private var _binding: FragmentLoginOtpBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login_otp

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginOtpBinding.bind(inflateView)
        initEventHandler()
    }

    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnVerify?.setOnClickListener {
            startActivity(MainActivity.newIntent(context))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}