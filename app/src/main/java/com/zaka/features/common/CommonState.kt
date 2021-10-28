package com.zaka.features.common


/**
 * Created by mohamed abdelnaser on 28/10/2021
 */

sealed class CommonState<out T> {
    object UnInit : CommonState<Nothing>()
    object LoadingShow : CommonState<Nothing>()
    object LoadingFinished : CommonState<Nothing>()
    object NoInternet : CommonState<Nothing>()
    data class Success<out R>(val data: R) : CommonState<R>()
    data class Error(val exception: Throwable) : CommonState<Nothing>()
}
