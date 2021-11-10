package com.zaka.features.settings

import android.util.Log
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.base.BaseFragment
import com.base.biometeric.BiometricUtil
import com.base.dialogs.AlertDialogManager
import com.base.extensions.clearActivityStack
import com.base.extensions.handleApiErrorWithAlert
import com.zaka.R
import com.zaka.databinding.FragmentChatRoomsBinding
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentSettingsBinding
import com.zaka.domain.AppLanguages
import com.zaka.domain.UserProfile
import com.zaka.features.common.CommonState
import com.zaka.features.login.LoginActivity
import com.zaka.features.main.MainActivity
import com.zaka.features.profile.vm.ProfileViewModel
import com.zaka.features.settings.vm.SettingsViewModel
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val profileViewModel : ProfileViewModel by viewModel()
    private val settingsViewModel :SettingsViewModel by viewModel()
    private var initLang = false

    override fun layoutResource(): Int = R.layout.fragment_settings

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding= FragmentSettingsBinding.bind(inflateView)

        initObservers()

        profileViewModel.fetchProfile(true)

        settingsViewModel.fetchAppSettings()


    }

    private fun  handleAddDeviceState(state : CommonState<String>){
        when (state) {
            CommonState.LoadingShow -> showProgressDialog()
            CommonState.LoadingFinished -> hideProgressDialog()
            is CommonState.Success -> { }
            is CommonState.Error->{
                handleApiErrorWithAlert(state.exception)
            }
        }
    }

    private fun handleLoadProfile(state:CommonState<UserProfile>){
        when (state) {
            CommonState.LoadingShow -> showProgress()
            CommonState.LoadingFinished -> hideProgress()
            is CommonState.Success -> {
                showProgress()
                _binding!!.tvUserName.text = state.data.displayName.toString()
                _binding!!.tvUserTitle.text = state.data.jobTitle.toString()
            }
            is CommonState.Error->{
                handleApiErrorWithAlert(state.exception)
            }
        }
    }

    private fun initObservers(){

        settingsViewModel.apply {
            settingsViewModel.addDeviceIdState.observe(this@SettingsFragment, {handleAddDeviceState(it)})

            addDeviceIdState.observe(this@SettingsFragment, { handleAddDeviceState(it) })
        }

        profileViewModel.profileState.observe(this, {
            handleLoadProfile(it)
        })

        settingsViewModel.settingsState.observe(this,{
            when(it){
                is CommonState.Success->{
                    _binding?.switchBiometrics?.isChecked = it.data.enableBiometricManager
                    _binding?.radioGroupLanguages?.check(if (it.data.languages==AppLanguages.EN)R.id.rButtonEn else R.id.rButtonAr)
                    initLang = true
                }
            }
        })

    }

    override fun initEventHandler() {

        _binding?.switchBiometrics?.setOnCheckedChangeListener { compoundButton, checked ->
            println("switchBiometrics")
           if(compoundButton.isPressed) {
               if (checked) {
                   settingsViewModel.addDeviceId();
               } else {
                   settingsViewModel.removeDeviceID()
               }
           }
        }
        _binding?.radioGroupLanguages?.setOnCheckedChangeListener { radioGroup, id ->
            Log.i("changeLanguage","LANG IS ${radioGroup.isPressed} ")

            if(initLang) {
               if (id==R.id.rButtonAr){
                   settingsViewModel.changeLanguage(AppLanguages.AR)
               }else{
                   settingsViewModel.changeLanguage(AppLanguages.EN)
               }
                startActivity(MainActivity.newIntent(context).clearActivityStack())
                requireActivity().overridePendingTransition(0, 0)
                requireActivity().finish()
            }
        }


        _binding?.textViewLogout?.setOnClickListener {
            val dialog = AlertDialogManager.createCustomViewDialog(requireContext(),R.layout.dialog_logout)
            dialog.apply {
                dialog_btn_no.setOnClickListener { dialog.dismiss() }
                dialog_btn_yes.setOnClickListener {
                    dialog.dismiss()
                    settingsViewModel.logOut()
                    startActivity(LoginActivity.newIntent(context).clearActivityStack())
                    requireActivity().finish()
                }
            }.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}