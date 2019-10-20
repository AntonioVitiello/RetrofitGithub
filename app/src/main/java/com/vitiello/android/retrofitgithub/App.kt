package com.vitiello.android.retrofitgithub

import android.app.Application
import androidx.annotation.StringRes

/**
 * Created by Antonio Vitiello on 20/10/2019.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set

        fun getString(@StringRes resId: Int): String {
            return instance.getString(resId)
        }

    }

}
