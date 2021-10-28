package com.zaka.data.repositories

import com.zaka.data.repositories.RepositoriesConstants.KEY_LANGUAGE_CODE
import com.zaka.data.repositories.RepositoriesConstants.LANGUAGE_ARABIC
import com.zaka.data.sources.local.AppPreference
import com.zaka.domain.AppLanguages
import java.util.*


class LocaleRepository(private val appPreference: AppPreference) {

    fun setLanguage(newLanguage: String) = appPreference.putStringWithCommit(KEY_LANGUAGE_CODE, newLanguage.toString())

    fun getLanguage(): String = appPreference.getString(KEY_LANGUAGE_CODE, LANGUAGE_ARABIC)!!

    fun isRtl(): Boolean = getLanguage() == AppLanguages.AR.toString()

    fun getLocale(): Locale = Locale(getLanguage())

}

