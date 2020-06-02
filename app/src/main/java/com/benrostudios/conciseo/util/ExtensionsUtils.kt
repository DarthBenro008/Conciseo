package com.benrostudios.conciseo.util

import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.shortToaster(message: String){
    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
}
fun Fragment.longToaster(message: String){
    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
}

fun CharSequence?.isValidURL() = !isNullOrEmpty() && Patterns.WEB_URL.matcher(this ?: "").matches()