package com.zaka.base.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.zaka.R

object AlertDialogManager {

    fun showAlertMessage(context: Context, setMessage: String) {
        createOneButtonAlertDialog(
            context,
            setMessage,
            "",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() }).show()
    }


    fun createOneButtonAlertDialog(
        context: Context,
        setMessage: String,
        title: String = "",
        okAction: DialogInterface.OnClickListener
    ): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.common_ok), okAction)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        return alertDialog
    }

    fun showTryAgainDialog(
        context: Context,
        setMessage: String,
        title: String = "",
        okAction: DialogInterface.OnClickListener
    ): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setNegativeButton(context.getString(R.string.common_ok), okAction)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.common_cancel), okAction)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        return alertDialog
    }

    fun createAlertDialog(
        context: Context,
        setMessage: String,
        title: String = "",
        okAction: (Any, Any) -> Unit
    ): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.common_ok), okAction)
        alertDialogBuilder.setNegativeButton(context.getString(R.string.common_cancel)) { dialogInterface, i -> dialogInterface.dismiss() }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        return alertDialog
    }
/*
    fun popupGoAppSetting(context: Context) {
        createAlertDialog(context,
            context.getString(R.string.permission_required_body),
            okAction = { dialogInterface, i ->
                AppUtility.openSetting(context)
            }).show()
    }*/

    fun createCustomViewDialog(context: Context, @LayoutRes layoutRes: Int): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutRes)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        return dialog
    }
/*

    fun createConfirmationDialog(
        context: Context, btnOkText: String,
        btnCancelText: String, message: String,
        @DrawableRes icon: Int, onClickOk: () -> Unit
    ): Dialog {
        val dialog = createCustomViewDialog(context, R.layout.view_dialog_confirmation)
        dialog.btnYes.text = btnOkText
        dialog.btnNo.text = btnCancelText
        dialog.tvConfirmationMessage.text = message
        dialog.ivDialogLogo.setImageResource(icon)
        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.setOnClickListener {
            onClickOk()
            dialog.dismiss()
        }
        return dialog
    }
*/


/*
    fun showForceNewUpdateDialog(
        context: Activity,
        setMessage: String,
        packageName: String
    ): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(context.getString(R.string.new_version_avaialbel))
        alertDialogBuilder.setNegativeButton(
            context.getString(R.string.update_button)
        ) { dialog, which ->
            AppUtility.openGooglePlayLink(context, packageName)
        }
        alertDialogBuilder.setPositiveButton(
            context.getString(R.string.close)
        ) { dialog, which ->
            context.finish()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        return alertDialog
    }
*/
/*
    fun showLogoutDialog(activity: Activity,onLogout:()->Unit){

        createConfirmationDialog(activity,btnOkText = activity.getString(R.string.yes_sure),
            btnCancelText = activity.getString(R.string.close),
            message = activity.getString(R.string.logout_confirmation_message),
            icon = R.drawable.ic_logout, onClickOk = {
                onLogout()
                activity.finish()
                SplashActivity.start(activity)
            }).show()
    }*/
}