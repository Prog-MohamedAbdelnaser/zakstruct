package com.zaka.features.login.loginscreen


import android.util.Log
import android.view.View

import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.data.model.LoginParams
import com.zaka.databinding.FragmentLoginBinding
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment() : BaseFragment() {

    val loginViewModel:LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginBinding.bind(inflateView)
        initEventHandler()
    }

    override fun initEventHandler() {
        super.initEventHandler()

        _binding?.btnLogin?.setOnClickListener {
            loginViewModel.login(LoginParams(password = _binding?.etUsername!!.text.toString(),username = "mgafaar-c"))
        }
    }

    override suspend fun initModelObservers() {

        loginViewModel.apply {
            loginState.collect { it ->
                when (it) {
                     CommonState.LoadingShow->showProgressDialog()
                    CommonState.LoadingFinished -> hideProgressDialog()
                    is CommonState.Success -> {
                        findNavController().navigate(R.id.action_loginFragment_to_loginOTPFragment)
                    }
                    is CommonState.Error -> {
                        handleApiErrorWithAlert(it.exception)
                    }
                }
            }


            getUserData().onEach {
                Log.e("cv",it.displayName.toString())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}