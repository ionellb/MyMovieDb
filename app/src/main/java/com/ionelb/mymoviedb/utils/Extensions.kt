package com.ionelb.mymoviedb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

/**
 * Checks whether the network capabilities are present and internet is available
 */
fun AppCompatActivity.isInternetAvailable(context: Context) : Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                {return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                {return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                {return true}
            }
        }
    } else {
        val activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    return false
}