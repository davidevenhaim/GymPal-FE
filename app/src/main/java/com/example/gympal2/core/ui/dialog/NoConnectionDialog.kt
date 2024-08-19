import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NoInternetConnectionDialog(
    onDismiss: () -> Unit,
    canDismiss: Boolean,
    title: String = "No Internet Connection",
    subTitle: String = "You are currently offline. Please check your internet connection.",
    confirmText: String = "OK"
) {
    val currentSubTitle =
        if (canDismiss) subTitle else "You are currently offline. Check your internet connection to continue."

    AlertDialog(
        onDismissRequest = { if (canDismiss) onDismiss(); else Unit },
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = currentSubTitle,
                fontSize = 16.sp
            )
        },

        confirmButton = {
            if (canDismiss) {
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(confirmText)
                }
            }
        },
    )
}
