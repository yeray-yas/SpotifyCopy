package com.yerayyas.cursofirebaselite

import android.app.Application
import android.content.Context

class CursoFirebaseLiteApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}