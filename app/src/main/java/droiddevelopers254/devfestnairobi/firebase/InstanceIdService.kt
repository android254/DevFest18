package droiddevelopers254.devfestnairobi.firebase

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import droiddevelopers254.devfestnairobi.utils.SharedPref.FIREBASE_TOKEN
import droiddevelopers254.devfestnairobi.utils.SharedPref.PREF_NAME


class InstanceIdService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveToken(token)
    }

    private fun saveToken(token: String?) {
        val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(FIREBASE_TOKEN, token).apply()
    }
}
