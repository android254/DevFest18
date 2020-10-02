package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.devfestnairobi.datastates.SpeakersState
import droiddevelopers254.devfestnairobi.models.SpeakersModel

class SpeakersRepo {

    fun getSpeakersInfo(speakerId: Int): LiveData<SpeakersState> {
        val speakersStateMutableLiveData = MutableLiveData<SpeakersState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("speakers")
                .whereEqualTo("id", speakerId)
                .get()
                .addOnSuccessListener {
                    val speakersModel = it.toObjects(SpeakersModel::class.java)
                    speakersStateMutableLiveData.value = SpeakersState(speakersModel,null)
                }
                .addOnFailureListener {
                    speakersStateMutableLiveData.value =SpeakersState(null,it.message) }

        return speakersStateMutableLiveData
    }
}
