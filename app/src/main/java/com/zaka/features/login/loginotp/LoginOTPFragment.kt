package com.zaka.features.login.loginotp

import android.os.CountDownTimer
import android.view.View
import com.base.BaseFragment
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.databinding.FragmentLoginOtpBinding
import com.zaka.domain.UserProfile
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import com.zaka.features.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginOTPFragment : BaseFragment() {

    private val loginViewModel:LoginViewModel by viewModel()
    private var _binding: FragmentLoginOtpBinding? = null
    private  var user: UserProfile?=null
    override fun layoutResource(): Int  = R.layout.fragment_login_otp

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginOtpBinding.bind(inflateView)
        initEventHandler()
        startResendCounter()
    }

    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnVerify?.setOnClickListener {
            loginViewModel.confirmOtp(_binding!!.otpView.text.toString())
        }

        _binding?.textResendCode?.setOnClickListener {
            startResendCounter()
        }
    }

    private fun startResendCounter() {
        object : CountDownTimer(TIMER_LENGTH, 1000) {
            override fun onFinish() {
                if (isAdded) {
                   _binding?.textOtpNotReceived?.visibility=View.VISIBLE
                    _binding?.textResendCode?.visibility=View.VISIBLE
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                if (isAdded)
                _binding?.textOtpTimer?.text = "00:${millisUntilFinished / 1000}"
                _binding?.textOtpNotReceived?.visibility=View.GONE
                _binding?.textResendCode?.visibility=View.GONE
            }
        }.start()
    }

    override suspend fun initModelObservers(coroutineScope: CoroutineScope) {

        loginViewModel.apply {
            otpState.collect { it ->
                when (it) {
                    CommonState.LoadingShow->showProgressDialog()
                    CommonState.LoadingFinished -> hideProgressDialog()
                    is CommonState.Success -> {
                        loginViewModel.getUserData()
                        startActivity(MainActivity.newIntent(context))
                    }
                    is CommonState.Error -> {
                        handleApiErrorWithAlert(it.exception)
                    }
                }
            }

            coroutineScope.launch { profileState.collect { it ->

                when (it) {
                    CommonState.LoadingShow -> showProgressDialog()
                    CommonState.LoadingFinished -> hideProgressDialog()
                    is CommonState.Success -> { user = it.data }
                    is CommonState.Error -> { handleApiErrorWithAlert(it.exception) }
                }
            }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TIMER_LENGTH: Long = 60 * 1000
    }
}