package com.vitiello.android.retrofitgithub.tools

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
open class SingleEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}