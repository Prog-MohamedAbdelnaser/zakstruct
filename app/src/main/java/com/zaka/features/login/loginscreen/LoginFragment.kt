package com.zaka.features.login.loginscreen
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import com.base.BaseFragment
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.base.extensions.hide
import com.zaka.base.extensions.show
import com.zaka.data.model.LoginParams
import com.zaka.data.model.RefreshTokenParams
import com.zaka.databinding.FragmentLoginBinding
import com.zaka.domain.UserProfile
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    val loginViewModel:LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    override fun layoutResource(): Int  = R.layout.fragment_login
    private  var user: UserProfile?=null

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginBinding.bind(inflateView)
        initEventHandler()
        loginViewModel.getUserData()
        loginViewModel.liveDate.observe(this, Observer {
            Log.e("observe" , it.toString())
        })

//        loginViewModel.refreshToken(RefreshTokenParams("","" ))
    }

    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnLogin?.setOnClickListener {
            loginViewModel.login(LoginParams(password = _binding?.etPassword!!.text.toString(),username = "mgafaar-c"))
        }
    }

     override  suspend fun initModelObservers(coroutineScope: CoroutineScope) {
         loginViewModel.apply {

             coroutineScope.launch {
                 loginState.collect {
                 when (it) {
                     CommonState.LoadingShow -> showProgressDialog()
                     CommonState.LoadingFinished -> hideProgressDialog()
                     is CommonState.Success -> {
                         findNavController().navigate(R.id.action_loginFragment_to_loginOTPFragment)
                     }
                     is CommonState.Error -> {
                         handleApiErrorWithAlert(it.exception)
                     }
                 }
             }
             }

             coroutineScope.launch { profileState.collect { it ->

                 when (it) {
                     CommonState.LoadingShow -> showProgressDialog()
                     CommonState.LoadingFinished -> hideProgressDialog()
                     is CommonState.Success -> {
                         _binding?.tilUserName?.hide()
                         user = it.data
                         _binding?.loginUserName?.text = user!!.displayName
                         _binding?.loginUserPostion?.text = user!!.jobTitle
                     }
                     is CommonState.Error -> {
                         _binding?.loginWelcomTitle?.text=resources.getString(R.string.login_title_first_time)
                         _binding?.tilUserName?.show()
                         _binding?.profileImage?.hide()
                         _binding?.loginUserName?.hide()
                         _binding?.loginUserPostion?.hide()
                         _binding?.loginAnotherAccount?.hide()
                     }
                 }
             } }
         }
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}