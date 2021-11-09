package com.zaka.data.repositories

import com.locationpicker.sample.data.sources.resources.AppResources
import com.zaka.R

class StringsRepository(private val appResources: AppResources) {

    fun getNetworkExceptionMessage(): String = appResources.getString(R.string.check_your_network_connection_and_try_again)

    fun getUnknownErrorMessage(): String = appResources.getString(R.string.unknown_error_message)

    fun getSocketTimeoutExceptionMessage(): String = appResources.getString(R.string.timeout_error_message)


}