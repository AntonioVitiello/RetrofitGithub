package com.vitiello.android.retrofitgithub.tools

/**
 * Created by Antonio Vitiello on 16/10/2019.
 */
fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun isNotEmpty(p1: String?, p2: String?, block: (String, String) -> Unit) {
    if (p1?.isNotEmpty() == true && p2?.isNotEmpty() == true) block(p1, p2)
}
