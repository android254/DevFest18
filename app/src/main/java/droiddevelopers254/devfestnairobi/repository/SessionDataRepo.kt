package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.devfestnairobi.database.AppDatabase
import droiddevelopers254.devfestnairobi.database.dao.SessionsDao
import droiddevelopers254.devfestnairobi.datastates.SessionDataState
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.utils.DroidCoin

class SessionDataRepo {
    internal var databaseReference: DatabaseReference? = null
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCoin.context)!!.sessionsDao()

    fun getSessionData(sessionId: Int): LiveData<SessionDataState> {
        val sessionsModelMutableLiveData = MutableLiveData<SessionDataState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("schedule")
                .whereEqualTo("id", sessionId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (queryDocumentSnapshot in it.result) {
                            val sessionsModel = queryDocumentSnapshot.toObject(SessionsModel::class.java)
                            val newSessionsModel= sessionsModel.copy(documentId = queryDocumentSnapshot.id)
                            sessionsModelMutableLiveData.value = SessionDataState(newSessionsModel,null)
                        }
                    } else {
                        sessionsModelMutableLiveData.value = SessionDataState(null,"Error getting session details")
                    }
                }

        return sessionsModelMutableLiveData
    }
}
