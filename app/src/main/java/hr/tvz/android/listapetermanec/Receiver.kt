package hr.tvz.android.listapetermanec

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class Receiver() : BroadcastReceiver() {
    private lateinit var baseContext: Context

    constructor(baseContext: Context) : this() {
        this.baseContext = baseContext
    }

    override fun onReceive(p0: Context, p1: Intent) {
        when(baseContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        }
    }
}