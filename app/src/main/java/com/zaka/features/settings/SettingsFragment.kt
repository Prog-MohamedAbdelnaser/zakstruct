package com.zaka.features.settings

import android.view.View
import android.view.ViewStub
import com.base.BaseFragment
import com.zaka.R
import com.zaka.databinding.FragmentChatRoomsBinding
import com.zaka.databinding.FragmentLettersBinding
import com.zaka.databinding.FragmentSettingsBinding
import com.zaka.features.common.CommonState
import com.zaka.features.profile.vm.ProfileViewModel
import com.zaka.features.settings.vm.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    val profileViewModel : ProfileViewModel by viewModel()
    val settingsViewModel :SettingsViewModel by viewModel()

    override fun layoutResource(): Int = R.layout.fragment_settings

    override fun onViewInflated(parentView: View, inflateView: View) {
        super.onViewInflated(parentView, inflateView)
        _binding= FragmentSettingsBinding.bind(inflateView)
        initEventHandler()
        profileViewModel.fetchProfile(true)
        settingsViewModel.fetchAppSettings()
    }

    override fun initEventHandler() {

        _binding?.switchBiometrics?.setOnCheckedChangeListener { compoundButton, checked ->
            println("switchBiometrics")
            if (checked){
                settingsViewModel.addDeviceId();
            }else{
                settingsViewModel.removeDeviceID()
            }
        }
    }
    override suspend fun initModelObservers(coroutineScope: CoroutineScope) {
        super.initModelObservers(coroutineScope)

        profileViewModel.profileState.collect {
            when(it){
                CommonState.LoadingShow->showProgress()
                CommonState.LoadingFinished->hideProgress()
                is CommonState.Success->{
                    _binding!!.tvUserName.text = it.data.displayName.toString()
                    _binding!!.tvUserTitle.text = it.data.jobTitle.toString()
                }
            }
        }

        settingsViewModel.settingsState.collect {
            when(it){
                CommonState.LoadingShow ->showProgress()
                CommonState.LoadingFinished->hideProgress()
                is CommonState.Success->{
                    _binding?.switchBiometrics?.isChecked = it.data.enableBiometricManager==true
                }
            }
        }

        coroutineScope.launch {
            settingsViewModel.addDeviceIdState.collect {
                when (it) {
                    CommonState.LoadingShow -> showProgress()
                    CommonState.LoadingFinished -> hideProgress()
                    is CommonState.Success -> {

                    }
                     is CommonState.Error->{
                         _binding?.switchBiometrics?.isChecked = false
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