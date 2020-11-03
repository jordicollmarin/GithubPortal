package cat.jorcollmar.githubportal.commons.extensions

import android.app.ActivityManager
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.ErrorWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createSingleChoiceAlertDialog(
    title: String, values: Array<String>, defaultValue: Int = 0,
    buttonAction: (dialog: DialogInterface, which: Int) -> Unit
): AlertDialog {
    return AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Light_Dialog)
        .setTitle(title)
        .setCancelable(true)
        .setSingleChoiceItems(values, defaultValue) { dialog, which ->
            buttonAction(dialog, which)
        }
        .create()
}

fun Context.showErrorDialog(errorWrapper: ErrorWrapper) {
    val builder = MaterialAlertDialogBuilder(this)
        .setTitle(errorWrapper.title)
        .setMessage(errorWrapper.message)

    if (errorWrapper.retryCallback != null) {
        builder.setNegativeButton(R.string.button_exit) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
            exitApp()
        }
        builder.setPositiveButton(R.string.button_retry) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
            errorWrapper.retryCallback.invoke()
        }
    } else {
        builder.setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
    }

    builder.show()
}

fun Context.showErrorDialog(throwable: Throwable) {
    val builder = MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.unknown_error_title))
        .setMessage(
            getString(
                R.string.unknown_error_message_with_details,
                getString(R.string.unknown_error_message),
                "${throwable::class.java.simpleName} -> ${throwable.message}"
            )
        )
        .setPositiveButton(R.string.button_exit) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
            exitApp()
        }
    builder.show()
}

private fun Context.exitApp() {
    val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    am.appTasks[0].finishAndRemoveTask()
}