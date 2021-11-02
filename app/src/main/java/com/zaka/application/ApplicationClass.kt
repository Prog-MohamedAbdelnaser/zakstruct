package com.zaka.application

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import com.zaka.BuildConfig
import com.zaka.data.sources.remote.di.remoteModule
import com.zaka.di.LanguageUseCaseProvider
import com.zaka.di.applicationModule
import com.zaka.di.useCaseModule
import com.zaka.features.login.di.loginModule
import com.zaka.features.profile.di.profileModule
import com.zaka.features.settings.di.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ApplicationClass : Application() {

    companion object {
        var currentActivity: ComponentName? = null
        var isTestVersion:Boolean=true
        lateinit var appContext:Application
        lateinit var application: ApplicationClass
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        application = this

        startKoin {
            this.androidContext(this@ApplicationClass)
            this.modules(listOf(
                applicationModule,
                remoteModule,
                useCaseModule,
                loginModule, profileModule,settingsModule
            ))
        }




        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity?.componentName
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                currentActivity = null

            }

        })
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUseCaseProvider.getLanguageUseCase(base).wrap(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LanguageUseCaseProvider.getLanguageUseCase(this).wrap(this)
    }

    var versionCode = BuildConfig.VERSION_CODE

    var versionName = BuildConfig.VERSION_NAME

}