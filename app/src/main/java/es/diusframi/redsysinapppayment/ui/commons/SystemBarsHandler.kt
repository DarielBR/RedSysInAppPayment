package es.diusframi.redsysinapppayment.ui.commons

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Hides windows insets for the screen showing the composition function from its been called. This
 * can be done iin two ways: 
 * - Transient Mode: system bars will be shown over the content with a transparent look, and will remain
 * visible for a few seconds allowing user interaction.
 * - Default Mode: system bars will displace the content, however, this function will force them
 * to hide as soon they appear on screen.
 * 
 * @param desiredBehavior The desire behavior for the insets in the screen from which this functions is called.
 * (allowed values WindowInsetsControllerCompat.BEHAVIOR_DEFAULT = 1 or WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 4)
 * @param avoidDefaultOnNonPhysicalDevices whether or not to use hard DEFAULT behavior on devices without physical navigation buttons. Default value set to TRUE
 */
@Composable
fun HideSystemBars(
    context: Context,
    view: View,
    desiredBehavior: Int,
    avoidDefaultOnNonPhysicalDevices: Boolean = true
){
    val window = (context as? ComponentActivity)?.window
    var visibility by remember{ mutableStateOf(true) }

    LaunchedEffect(key1 = visibility) {
        val controller = window?.let { WindowCompat.getInsetsController(it, view) }
        controller.let {
            it?.hide(WindowInsetsCompat.Type.systemBars())
            it?.systemBarsBehavior =
                if (desiredBehavior == WindowInsetsControllerCompat.BEHAVIOR_DEFAULT){
                    if (avoidDefaultOnNonPhysicalDevices){
                        if (hasSoftwareKeys(context)){
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        } else {
                            desiredBehavior
                        }
                    } else {
                        desiredBehavior
                    }
                } else {
                    desiredBehavior
                }
        }
        visibility = !visibility
    }

    DisposableEffect(visibility) {
        val listener = View.OnApplyWindowInsetsListener { _, insets ->
            val isVisible = insets.isVisible(WindowInsetsCompat.Type.systemBars())
            if (isVisible) {
                // Hide the system bars again if they become visible
                window?.let {
                    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.systemBars())
                }
            }
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, listener as? OnApplyWindowInsetsListener?)

        onDispose {
            ViewCompat.setOnApplyWindowInsetsListener(view, null)
        }
    }
    /*endregion*/
}

fun hasSoftwareKeys(context: Context): Boolean {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // For Android 11 (API level 30) and higher
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(android.view.WindowInsets.Type.navigationBars())
        val bounds = windowMetrics.bounds
        val displayHeight = bounds.height() - insets.bottom - insets.top
        val displayWidth = bounds.width() - insets.left - insets.right

        displayWidth < bounds.width() || displayHeight < bounds.height()
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        // For Android 4.2 (API level 17) to Android 10 (API level 29)
        val displayMetrics = DisplayMetrics()
        val realMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        windowManager.defaultDisplay.getRealMetrics(realMetrics)

        val realHeight = realMetrics.heightPixels
        val realWidth = realMetrics.widthPixels

        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels

        realWidth > displayWidth || realHeight > displayHeight
    } else {
        // Older versions cannot reliably check this
        true
    }
}