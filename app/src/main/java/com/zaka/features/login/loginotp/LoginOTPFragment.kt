package com.zaka.features.login.loginotp

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentLoginOtpBinding
import com.zaka.databinding.FragmentReportsBinding

class LoginOTPFragment : BaseFragment() {

    private var _binding: FragmentLoginOtpBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login_otp

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginOtpBinding.bind(inflateView)
        _binding!!.textHome!!.text="hi login otp"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}