package com.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.base.dialogs.CustomProgressDialog

abstract class BaseActivity : AppCompatActivity() {

  /*  override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUseCaseProvider.getLanguageUseCase(base).execute(base))
    }*/

    fun showProgressDialog(){
        progressDialog.show()
    }

    fun hideProgressDialog(){
        progressDialog.dismiss()
    }


    lateinit var progressDialog: CustomProgressDialog

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog= CustomProgressDialog(this)

    }


}