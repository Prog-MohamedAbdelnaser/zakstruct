package com.zaka.features.settings.di



import com.zaka.data.repositories.SettingsRepository
import com.zaka.data.sources.remote.api.SettingsAPI
import com.zaka.features.settings.vm.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val settingsModule = module {
    factory {  get<Retrofit>().create(SettingsAPI::class.java)}
    factory { SettingsRepository(get()) }
    viewModel { SettingsViewModel(get()) }
}