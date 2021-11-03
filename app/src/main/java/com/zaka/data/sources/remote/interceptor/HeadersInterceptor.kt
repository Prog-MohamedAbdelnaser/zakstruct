package com.zaka.data.sources.remote.interceptor

import com.zaka.di.DIConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.StringQualifier
class HeadersInterceptor() : Interceptor, KoinComponent {

    private val keyAuthorization = "authorization"
    private val keyApiKey = "apiKey"
    private val apiKeyValue = "Nas@manpoweragent"
    private val keyLanguage = "Language"

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(createNewRequestWithApiKey(chain.request()))

    private fun createNewRequestWithApiKey(oldRequest: Request): Request {
        val requestBuilder = oldRequest.newBuilder()
                .addHeader(keyLanguage, get(StringQualifier(DIConstants.KEY_CURRENT_LANGUAGE)))
          .addHeader(keyAuthorization, "Bearer "+get(StringQualifier(DIConstants.KEY_USER_TOKEN)))

        /*  mainRepository.getCurrentLoggedInUser()?.apply {
            //  requestBuilder.addHeader(keyAuthorization, "Bearer ${this.accessToken}")
          }*/

        return requestBuilder.build()
    }
}