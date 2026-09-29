package me.basehub.scannerapp.core.data.local.pref

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val VIBRATE_ON_SCAN = booleanPreferencesKey("vibrate_on_scan")
    val SOUND_ON_SCAN = booleanPreferencesKey("sound_on_scan")
    val PREFERRED_BROWSER_PACKAGE = stringPreferencesKey("preferred_browser_package")
}