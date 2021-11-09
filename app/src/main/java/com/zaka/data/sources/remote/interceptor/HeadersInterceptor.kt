package com.zaka.data.sources.remote.interceptor

import com.zaka.data.repositories.UserRepository
import com.zaka.di.DIConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.StringQualifier
class HeadersInterceptor(private val userRepository: UserRepository) : Interceptor, KoinComponent {

    private val keyAuthorization = "authorization"
    private val keyApiKey = "apiKey"
    private val apiKeyValue = "Nas@manpoweragent"
    private val keyLanguage = "LanguageCode"

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(createNewRequestWithApiKey(chain.request()))

    private fun createNewRequestWithApiKey(oldRequest: Request): Request {
        println("oldRequest.url ${oldRequest.url.encodedPath}")
        val token = if (oldRequest.url.encodedPath.contains("accounts/confirmotp")){
            userRepository.getOtpToken()
        }else{
            userRepository.getUserToken()
        }
        val requestBuilder = oldRequest.newBuilder()
                .addHeader(keyLanguage, get(StringQualifier(DIConstants.KEY_CURRENT_LANGUAGE)))
          .addHeader(keyAuthorization, "Bearer $token")

        /*  mainRepository.getCurrentLoggedInUser()?.apply {
            //  requestBuilder.addHeader(keyAuthorization, "Bearer ${this.accessToken}")
          }*/

        return requestBuilder.build()
    }
}