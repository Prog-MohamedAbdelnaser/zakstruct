package com.zaka.di

import com.zaka.domain.usecases.LanguageUseCases
import org.koin.dsl.module


val useCaseModule = module {

    single { LanguageUseCases(get()) }

}