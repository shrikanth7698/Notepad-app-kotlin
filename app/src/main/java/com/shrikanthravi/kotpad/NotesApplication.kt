package com.shrikanthravi.kotpad

import android.app.Application
import com.shrikanthravi.kotpad.data.DataStore


class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.init(this)
    }
}
