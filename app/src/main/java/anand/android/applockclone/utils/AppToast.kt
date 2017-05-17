package anand.android.applockclone.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by anandm on 09/02/17.
 */

object AppToast {

    fun showToast(context: Context, message: String) = Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show()


    fun showDebugToast(context: Context, message: String) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show()
    }

}
