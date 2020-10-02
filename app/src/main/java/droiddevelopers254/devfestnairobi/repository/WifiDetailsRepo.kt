package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import java.util.concurrent.Executor

import droiddevelopers254.devfestnairobi.models.WifiDetailsModel

class WifiDetailsRepo {

    val wifiDetails: LiveData<WifiDetailsModel>
        get() {
            val wifiDetailsModelMutableLiveData = MutableLiveData<WifiDetailsModel>()
            val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            return wifiDetailsModelMutableLiveData
        }
}
