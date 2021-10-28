package com.base.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.base.dialogs.AlertDialogManager
import com.zaka.application.ApplicationClass
import com.zaka.data.exceptions.APIException
import com.zaka.data.repositories.UserRepository
import org.koin.android.ext.android.inject
import retrofit2.HttpException
import timber.log.Timber

fun isInternetConnected(): Boolean {

    val connectivityManager =
        ApplicationClass.appContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
}

    inline fun Throwable.handleApiError(action: (String, String) -> Unit) {
        if (this is HttpException) {
            if (this.message!=null) {

                action(code().toString(), this.message())
            }else{
                val message ="unsepcified error !!"
                action(code().toString(), message)
            }

        } else if (this is APIException){
            val errorMessage: String = message!!
            action(this.code,errorMessage)
            Log.d("API EXCEPTION", errorMessage)
        } else {
            val errorMessage: String = message!!
            action("0",errorMessage)
            Log.d("Not_HTTP", errorMessage)
        }

    }

inline fun Throwable.handleApiError(action: (Int, String) -> Unit,onSessionExpired: () -> Unit) {
    if (this is HttpException) {
        if (this.message!=null) {

            action(code(), this.message())
        }else{
            val message ="unsepcified error !!"
            action(code(), message)
        }

    } else if (this is APIException){
        val errorMessage: String = message!!
        action(this.code.toInt(),errorMessage)
        Log.d("API EXCEPTION", errorMessage)
    } else {
        val errorMessage: String = message!!
        action(0,errorMessage)
        Log.d("Not_HTTP", errorMessage)
    }

}

    fun Fragment.handleApiError(e: Throwable) {

        val mainRepository: UserRepository by inject()
        e.handleApiError { code, message ->
            Timber.i("handleApiError $code")
            /*   if (code in EXPIRE_SESSION_LIST_STATUS) {
                if (mainRepository.isLoged()) {
                    mainRepository.clear()
                    //    showUnAuthorizedAlerDialog(requireActivity(),message_received)
                } else showErrorDialog(message)
        }*/

        }
    }


fun Fragment.handleApiErrorWithAlert(e: Throwable) {

    val mainRepository : UserRepository by inject()
    e.handleApiError { code, message ->
        Timber.i("handleApiError $code" )
       /* if (code in EXPIRE_SESSION_LIST_STATUS) {
            if (mainRepository.isLoged()) {
                mainRepository.clear()
                //    showUnAuthorizedAlerDialog(requireActivity(),message_received)
            } else showErrorDialog(message)
        } else */
        showErrorDialog(message)
    }
}

fun Activity.handleApiErrorWithAlert(e: Throwable) {

    val mainRepository : UserRepository by inject()
    e.handleApiError { code, message ->
        Timber.i("handleApiError $code" )
//        if (code in EXPIRE_SESSION_LIST_STATUS) {
//            if (mainRepository.isLoged()) {
//                mainRepository.clear()
//                //    showUnAuthorizedAlerDialog(requireActivity(),message_received)
//            } else showErrorDialog(message)
//        } else
        showErrorDialog(message)
    }

}




  fun Activity.showErrorDialog(message: String){
      AlertDialogManager.showAlertMessage(this,message)
  }

fun Fragment.showErrorDialog(message: String){
      AlertDialogManager.showAlertMessage(requireContext(),message)
  }


fun Fragment.showUnAuthorizedAlertDialog(activity: Activity, message:String) {

        AlertDialogManager.createOneButtonAlertDialog(
            activity,
            message,
            okAction = { dialogInterface, i ->
                activity.overridePendingTransition(0, 0)
                activity.finish()
            }).show()
    }

