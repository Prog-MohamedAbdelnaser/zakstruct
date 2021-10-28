package com.zaka.domain.usecases

import io.reactivex.disposables.CompositeDisposable

interface UseCase<in P, R> {

    fun getDisposabel()=CompositeDisposable()
    suspend  fun execute(param: P? = null): R
    fun clearDispose(){
        getDisposabel().dispose()
    }
}