package com.example.caloriestracker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Utils {
    fun formatDouble(number: Double): String {
        return when {
            number % 1 == 0.0 -> String.format("%.0f", number)
            number * 10 % 1 == 0.0 -> String.format("%.1f", number)
            else -> String.format("%.2f", number)
        }
    }

    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }

    fun trimTextToMaxLength(text: String, maxLength: Int = 35): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }
}