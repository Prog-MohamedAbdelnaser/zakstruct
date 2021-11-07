package com.zaka.data.repositories

import com.zaka.data.repositories.RepositoriesConstants.KEY_ENABLE_FINGER_PRINT
import com.zaka.data.repositories.RepositoriesConstants.KEY_LANGUAGE_CODE
import com.zaka.data.repositories.RepositoriesConstants.LANGUAGE_ARABIC
import com.zaka.data.sources.local.AppPreference
import com.zaka.domain.AppLanguages
import com.zaka.domain.AppSettings
import java.util.*


class LocaleRepository(private val appPreference: AppPreference) {

    fun setLanguage(newLanguage: String) = appPreference.putStringWithCommit(KEY_LANGUAGE_CODE, newLanguage.toString())

    fun setEnableFingerPrint(enabled: Boolean) = appPreference.putStringWithCommit(KEY_ENABLE_FINGER_PRINT, enabled.toString())

    fun getLanguage(): String = appPreference.getString(KEY_LANGUAGE_CODE, LANGUAGE_ARABIC)!!

    fun isEnableFingerPrint(): Boolean = appPreference.getString(KEY_ENABLE_FINGER_PRINT, "false") =="true"

    fun isRtl(): Boolean = getLanguage() == AppLanguages.AR.toString()

    fun getLocale(): Locale = Locale(getLanguage())

    fun getSettings(): AppSettings {
        return AppSettings(enableBiometricManager = isEnableFingerPrint(),languages =getAppLangEnum() )
    }

    private fun  getAppLangEnum():AppLanguages{
       return when(getLanguage()){
           LANGUAGE_ARABIC->AppLanguages.AR
            else->AppLanguages.EN
        }
    }
}

