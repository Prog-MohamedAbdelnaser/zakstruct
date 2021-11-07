package com.zaka.data.repositories

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import timber.log.Timber
import com.zaka.data.repositories.RepositoriesConstants.KEY_DEVICE_INFO
import com.zaka.data.sources.local.AppPreference


class DeviceInfoRepository(private val appPreference: AppPreference, private val context: Context) {


    private fun setDeviceID(deviceId: String) { appPreference.putStringWithCommit(KEY_DEVICE_INFO, deviceId) }

    fun getDeviceID(): String? {
     val deviceId=  appPreference.getString(KEY_DEVICE_INFO,null)
        println("getDeviceIdFromSetting now ${deviceId}")

        if (deviceId==null) return getDeviceIdFromSetting() else return deviceId
    }

   private fun getDeviceIdFromSetting(): String {
       val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
       val deviceId = Build.SERIAL + "#" + androidId
       Log.e("getDeviceIdFromSetting" , "deviceId id ${deviceId}")
       //    <uses-permission android:name="android.permission.CALL_PHONE" />
           setDeviceID(deviceId)

        return deviceId
    }

    private fun isMarshmallowOrLater(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}