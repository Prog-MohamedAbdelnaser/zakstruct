package com.zaka.features.login.loginscreen
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.base.biometeric.BiometricAuthListener
import com.base.biometeric.BiometricUtil
import com.base.extensions.clearActivityStack
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.base.extensions.hide
import com.zaka.base.extensions.isShow
import com.zaka.base.extensions.show
import com.zaka.data.model.LoginParams
import com.zaka.databinding.FragmentLoginBinding
import com.zaka.domain.UserProfile
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import com.zaka.features.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(), BiometricAuthListener {

    val loginViewModel:LoginViewModel by viewModel()

    private var _binding: FragmentLoginBinding? = null

    override fun layoutResource(): Int  = R.layout.fragment_login

    private  var user: UserProfile?=null

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginBinding.bind(inflateView)
        loginViewModel.getUserData()

    }

    override fun initEventHandler() {
        super.initEventHandler()

        _binding?.btnLogin?.setOnClickListener {
            loginViewModel.login(LoginParams(password = _binding?.etPassword!!.text.toString(),username = "mgafaar-c"))
        }
        _binding?.btnLoginWithFinger?.setOnClickListener {
            BiometricUtil.showBiometricPrompt(
                activity = this,
                listener = this,
                cryptoObject = null,
                allowDeviceCredential = true
            )
        }
    }


    override fun initModelObservers() {
        lifecycleScope.launch { loginViewModel.apply {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        refreshSessionState.collect {
                            when (it) {
                                CommonState.LoadingShow -> showProgressDialog()
                                CommonState.LoadingFinished -> hideProgressDialog()
                                is CommonState.Success -> {
                                    activity?.finish()
                                    startActivity(MainActivity.newIntent(context).clearActivityStack())

                                }
                                is CommonState.Error -> {
                                    handleApiErrorWithAlert(it.exception)
                                }
                            }
                        }
                    }
                    launch {
                        profileState.collect { it ->

                            when (it) {
                                CommonState.LoadingShow -> showProgressDialog()
                                CommonState.LoadingFinished -> hideProgressDialog()
                                is CommonState.Success -> {
                                    _binding?.tilUserName?.hide()
                                    user = it.data
                                    _binding?.loginUserName?.text = user!!.displayName
                                    _binding?.loginUserPostion?.text = user!!.jobTitle
                                    loginViewModel.fetchAppSettings()
                                }
                                is CommonState.Error -> {
                                    _binding?.loginWelcomTitle?.text = resources.getString(R.string.login_title_first_time)
                                    _binding?.tilUserName?.show()
                                    _binding?.profileImage?.hide()
                                    _binding?.loginUserName?.hide()
                                    _binding?.loginUserPostion?.hide()
                                    _binding?.loginAnotherAccount?.hide()
                                }
                            }
                        }
                    }
                }
            } }
        loginViewModel.enableFingerprintState.observe(this@LoginFragment, {
            println("enableFingerprintState ${it}")
            when (it) {
                CommonState.LoadingShow -> showProgressDialog()
                CommonState.LoadingFinished -> hideProgressDialog()
                is CommonState.Success -> {
                    _binding?.loutFingerprint?.isShow(it.data)
                }
                is CommonState.Error -> {
                    handleApiErrorWithAlert(it.exception)
                }
            }
        })
        loginViewModel.observeLogin(this, { handleLoginState(it) })
    }

    private fun handleLoginState(state:CommonState<String>){
        when (state) {
            CommonState.LoadingShow -> showProgressDialog()
            CommonState.LoadingFinished -> hideProgressDialog()
            is CommonState.Success -> {
                findNavController().navigate(R.id.loginOTPFragment)
            }
            is CommonState.Error -> {
                handleApiErrorWithAlert(state.exception)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        loginViewModel.refreshToken()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(context, "Biometric login failed. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
    }
}