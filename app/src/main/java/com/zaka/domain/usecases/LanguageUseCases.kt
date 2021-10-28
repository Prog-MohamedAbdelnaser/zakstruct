package com.zaka.domain.usecases

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.zaka.data.repositories.LocaleRepository
import timber.log.Timber
import java.util.*

class LanguageUseCases(private val localeRepository: LocaleRepository) {

    fun changeLanguageTo(langaugeName: String){
        Timber.i("language name ${langaugeName.toString().lowercase(Locale.ROOT)}")
        localeRepository.setLanguage(langaugeName)
    }

    fun execute(param: Context?): Context {
        var newContext = param
        val language = localeRepository.getLanguage()
        if (!language.isNullOrEmpty()) {


            val res = param!!.resources
            val dm = res.displayMetrics
            var configuration = res.configuration

            println("WRAB LANGAUGE ${language.toLowerCase()}")
            val locale = Locale(language.toLowerCase(),"")

            Locale.setDefault(locale)

            configuration.setLocale(locale)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val localeList = LocaleList(locale)

                LocaleList.setDefault(localeList)

                configuration.setLocales(localeList)
            }
            res.updateConfiguration(configuration, dm)

            newContext = param.createConfigurationContext(configuration)
        }
        return newContext!!
    }

    fun wrap(context: Context): Context {

        val config = context.resources.configuration

        val language = localeRepository.getLanguage()
        Timber.i("getSharedPreferences ${language}")
        val resourc = context.resources

        val dm = resourc.displayMetrics

        if (language != "") {
            val locale = Locale(language)
            Locale.setDefault(locale)
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val localList = LocaleList(locale)
                LocaleList.setDefault(localList)
                config.setLocales(localList)
            }

            resourc.updateConfiguration(config, dm)
        }
        return context.createConfigurationContext(config)
    }

    fun getLanguage()=localeRepository.getLanguage()

    private fun setSystemLocale(config: Configuration, locale: Locale) {
        Locale.setDefault(locale)
        config.setLocale(locale)
    }
}
