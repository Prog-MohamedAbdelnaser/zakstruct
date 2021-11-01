package com.zaka.di

import android.content.Context
import com.locationpicker.sample.data.sources.local.AppPreferenceConstants.DEFAULT_LOCALE
import com.locationpicker.sample.data.sources.local.AppPreferenceConstants.PREFERENCE_FILE_NAME
import com.locationpicker.sample.data.sources.resources.AppResources
import com.zaka.data.repositories.LocaleRepository
import com.zaka.data.repositories.StringsRepository
import com.zaka.data.sources.local.AppPreference
import com.zaka.di.DIConstants.KEY_CURRENT_LANGUAGE
import com.zaka.di.DIConstants.KEY_GLIDE_OKHTTP_CLIENT
import com.zaka.di.DIConstants.KEY_USER_TOKEN
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val applicationModule = module {

    single { AppResources(get()) }

    single { StringsRepository(get()) }

    single(StringQualifier(PREFERENCE_FILE_NAME)) { androidApplication().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE) }


    single { AppPreference(get(StringQualifier(PREFERENCE_FILE_NAME))) }

/*
    factory { UserRepository(get()) }
*/


/*
    factory(StringQualifier(KEY_NEW_FIREBASE_TOKEN)) { get<AppPreference>().getString(KEY_FIREBASE_TOKEN,"")!! }
*/


    single { LocaleRepository(get()) }

    single(StringQualifier(DEFAULT_LOCALE)) { get<LocaleRepository>().getLocale() }

    factory(StringQualifier(KEY_CURRENT_LANGUAGE)) { get<LocaleRepository>().getLanguage() }


    factory(StringQualifier(KEY_USER_TOKEN)) { get<LocaleRepository>().getUserToken() }



    single(StringQualifier(KEY_GLIDE_OKHTTP_CLIENT)) {
        OkHttpClient.Builder()
                .sslSocketFactory(
                        get<SSLContext>().socketFactory,
                        get<Array<TrustManager>>()[0] as X509TrustManager)
                .hostnameVerifier(get())
                .build()
    }

}