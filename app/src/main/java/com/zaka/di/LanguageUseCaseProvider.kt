package com.zaka.di

import android.content.Context
import com.locationpicker.sample.data.sources.local.AppPreferenceConstants.PREFERENCE_FILE_NAME
import com.zaka.data.repositories.LocaleRepository
import com.zaka.data.sources.local.AppPreference
import com.zaka.domain.usecases.LanguageUseCases

object LanguageUseCaseProvider {

    private var languageUseCases : LanguageUseCases?=null

    fun getLanguageUseCase(context: Context): LanguageUseCases {
        if (languageUseCases ==null){
            languageUseCases = LanguageUseCases(getLocalRepository(context))
        }
        return languageUseCases as LanguageUseCases
    }

    fun getLocalRepository(context:Context)=
        LocaleRepository(AppPreference(context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)))
}