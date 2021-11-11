package com.zaka.features.login.loginotp

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_DEL
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.base.BaseFragment
import com.base.dialogs.AlertDialogManager
import com.base.extensions.clearActivityStack
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.base.extensions.hide
import com.zaka.base.extensions.show
import com.zaka.databinding.FragmentLoginOtpBinding
import com.zaka.domain.UserProfile
import com.zaka.features.common.CommonState
import com.zaka.features.login.vm.LoginViewModel
import com.zaka.features.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class LoginOTPFragment : BaseFragment() {

    private val loginViewModel:LoginViewModel by viewModel()
    private var _binding: FragmentLoginOtpBinding? = null
    private  var user: UserProfile?=null
    private val loginOTPFragmentArgs :LoginOTPFragmentArgs by navArgs()

    override fun layoutResource(): Int  = R.layout.fragment_login_otp

    @SuppressLint("StringFormatInvalid")
    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding=FragmentLoginOtpBinding.bind(inflateView)
        if (loginOTPFragmentArgs.phoneNumber!=null){
            setMaskedPhone (loginOTPFragmentArgs.phoneNumber!!)
        }
        initEventHandler()
        startResendCounter()
    }


    override fun initEventHandler() {
        super.initEventHandler()
        _binding?.btnVerify?.setOnClickListener {
            if (getOtp().length==4)
            loginViewModel.confirmOtp(getOtp())
            else AlertDialogManager.showAlertMessage(requireContext(),resources.getString(R.string.login_otp_error_message))
        }

        _binding?.textResendCode?.setOnClickListener {
            startResendCounter()
            loginViewModel.generateOtp("")
        }
        checkOtp()
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

    fun checkOtp(){
        _binding?.includedOtp?.d1?.addTextChangedListener {
            _binding?.includedOtp?.d1?.isSelected = it.toString().isNotEmpty()
            if (it.toString().isEmpty()){

            }else {
                _binding?.includedOtp?.d2?.requestFocus()
            }
        }

        _binding?.includedOtp?.d2?.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action==ACTION_DOWN && keyEvent.keyCode == KEYCODE_DEL){
                if (_binding?.includedOtp?.d2!!.text.isEmpty()) {
                    _binding?.includedOtp?.d1?.setText("")
                    _binding?.includedOtp?.d1?.requestFocus()
                }
            }
            return@setOnKeyListener false
        }

        _binding?.includedOtp?.d3?.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action==ACTION_DOWN && keyEvent.keyCode == KEYCODE_DEL){

                if (_binding?.includedOtp?.d3!!.text.isEmpty()) {
                    _binding?.includedOtp?.d2?.setText("")
                    _binding?.includedOtp?.d2?.requestFocus()
                }
            }
            return@setOnKeyListener false
        }
        _binding?.includedOtp?.d4?.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action==ACTION_DOWN && keyEvent.keyCode == KEYCODE_DEL){
                if (_binding?.includedOtp?.d4!!.text.isEmpty()) {
                    _binding?.includedOtp?.d3?.setText("")
                    _binding?.includedOtp?.d3?.requestFocus()
                }
            }
            return@setOnKeyListener false
        }

        _binding?.includedOtp?.d2?.addTextChangedListener {

            if (it.toString().isEmpty()){
            //    _binding?.includedOtp?.d1?.requestFocus()
            }else {
                _binding?.includedOtp?.d3?.requestFocus()
            }
            _binding?.includedOtp?.d2?.isSelected = it.toString().isNotEmpty()
        }
        _binding?.includedOtp?.d3?.addTextChangedListener {
            if (it.toString().isEmpty()){
               // _binding?.includedOtp?.d2?.requestFocus()
            }else{
            _binding?.includedOtp?.d4?.requestFocus()
            }

            _binding?.includedOtp?.d3?.isSelected = it.toString().isNotEmpty()
        }
        _binding?.includedOtp?.d4?.addTextChangedListener {
            if (it.toString().isEmpty()){
              //  _binding?.includedOtp?.d3?.requestFocus()
            }
            _binding?.includedOtp?.d4?.isSelected = it.toString().isNotEmpty()
        }
    }

    fun getOtp():String{
        return  "${_binding!!.includedOtp.d1.text?:""}${_binding!!.includedOtp.d2.text?:""}${_binding!!.includedOtp.d3.text?:""}${_binding!!.includedOtp.d4.text?:""}"
    }
    override  fun initModelObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { loginViewModel.apply {
                    otpState.collect { it ->
                        when (it) {
                            CommonState.LoadingShow -> showProgressDialog()
                            CommonState.LoadingFinished -> hideProgressDialog()
                            is CommonState.Success -> {
                                startActivity(MainActivity.newIntent(context))
                                requireActivity().finish()
                            }
                            is CommonState.Error -> {
                                handleApiErrorWithAlert(it.exception)
                            }
                        }
                    }

                }

            }

            loginViewModel.
            generateOtpState.collect { it ->
                when (it) {
                    CommonState.LoadingShow->showProgressDialog()
                    CommonState.LoadingFinished -> hideProgressDialog()
                    is CommonState.Success -> {
                        startResendCounter()
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


    @SuppressLint("StringFormatInvalid")
    private fun setMaskedPhone(phone: String) {
        Locale.getDefault().language
        val maskedPhoneNumber ="+966" + "*".repeat(phone.length - 2) + phone.takeLast(2)
        val localizedPhoneNumber = if (Locale.getDefault().language == "ar")
            "\u200E $maskedPhoneNumber"
        else maskedPhoneNumber

        _binding?.textOtpDesc?.text = getString(R.string.otp_sent_to_phone, localizedPhoneNumber)
    }

    companion object {
        private const val TIMER_LENGTH: Long = 60 * 1000
    }
}