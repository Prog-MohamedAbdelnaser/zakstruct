package com.zaka.data.sources.remote.interceptor

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.zaka.data.exceptions.APIException
import com.zaka.data.exceptions.NetworkException
import com.zaka.data.repositories.StringsRepository
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.nio.charset.Charset
import java.util.*


class ErrorHandlerInterceptor(private val stringsRepository: StringsRepository) : Interceptor {

    private val successStatus = "200"
    private val successCode = "Ok"
    private val keyStatus = "status"
    private val keyCode = "code"
    private val keyMessage = "message"
    private val keyJson = "json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            if (e is SocketTimeoutException) {
                throw NetworkException(stringsRepository.getSocketTimeoutExceptionMessage())
            } else {
                throw NetworkException(stringsRepository.getNetworkExceptionMessage())
            }
        }
        val body = response.body!!
        Timber.i("ErrorIntercept: %s", response.message)

        // Only intercept JSON type responses and ignore the rest.
        if (isJsonTypeResponse(body)) {

            var message = ""
            var code = successCode // Assume default OK
            var status = successStatus
            try {
                val source = body.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()
                val charset = body.contentType()!!.charset(Charset.forName("UTF-8"))!!
                // Clone the existing buffer is they can only read once so we still want to pass the original one to the chain.
                val json = buffer.clone().readString(charset)
                val obj = JsonParser().parse(json)
                // Capture error code an message_received.

                if (obj is JsonObject && obj.has(keyStatus)) {
                    status = obj.get(keyStatus).asString
                }
                if (obj is JsonObject && obj.has(keyCode)) {
                    code = obj.get(keyCode).asString
                }
                if (obj is JsonObject && obj.has(keyMessage)) {
                    if(obj.get(keyMessage).toString()!="null"){
                        message = obj.get(keyMessage).asString
                        Log.e("naser: %s", obj.get(keyMessage).toString())
                    }

                }
            } catch (e: Exception) {
                Timber.i("ErrorIntercept: %s", e.message)
                throw e
            }

            // Check if status has an error code then throw and exception so retrofit can trigger the onFailure callback method.
            // Anything above 400 is treated as a server error.
            if (status == successStatus) {
            }else{
                Timber.i("ErrorIntercept: %s", "UNknown")

                throw APIException(code, message)

            }
        }else{
            Timber.i("ErrorIntercept: else parsing",)
            throw APIException(code = response.code.toString(),stringsRepository.getUnknownErrorMessage())
        }

        return response
    }

    private fun isJsonTypeResponse(body: ResponseBody?): Boolean {
        return body?.contentType() != null && body.contentType()!!.subtype.lowercase(Locale.ROOT) == keyJson
    }

}