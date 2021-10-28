package com.zaka.features.login.loginotp

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentLoginOtpBinding
import com.zaka.databinding.FragmentReportsBinding
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import com.zaka.features.main.MainActivity
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginOTPFragment : BaseFragment() {

    private val loginViewModel:LoginViewModel by viewModel()

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
            loginViewModel.confirmOtp(_binding!!.otpView.text.toString())
        }

    }

    override suspend fun initModelObservers() {

        loginViewModel.apply {
            otpState.collect { it ->
                when (it) {
                    CommonState.LoadingShow->showProgressDialog()
                    CommonState.LoadingFinished -> hideProgressDialog()
                    is CommonState.Success -> {
                        startActivity(MainActivity.newIntent(context))
                    }
                    is CommonState.Error -> {
                        handleApiErrorWithAlert(it.exception)
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}