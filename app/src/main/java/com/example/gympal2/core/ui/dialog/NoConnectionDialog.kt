import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showNoConnectionDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("No Internet Connection")
        .setMessage("Please check your internet connection and try again.")
        .setPositiveButton("OK", null)
        .show()
}