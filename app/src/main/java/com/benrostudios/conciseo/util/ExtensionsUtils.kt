package com.benrostudios.conciseo.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.benrostudios.conciseo.R
import com.google.android.material.snackbar.Snackbar


fun Fragment.shortToaster(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToaster(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Context.shortToaster(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.appear() {
    this.visibility = View.VISIBLE
}

fun Context.isConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun View.errorSnackbar(error: String){
    val snackbar = Snackbar.make(this , error,Snackbar.LENGTH_LONG)
    val snackView = snackbar.view
    snackView.setBackgroundColor(resources.getColor(R.color.snackRed))
    snackbar.show()
}


fun CharSequence?.isValidURL() = !isNullOrEmpty() && Patterns.WEB_URL.matcher(this ?: "").matches()