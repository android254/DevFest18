package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.devfestnairobi.database.AppDatabase
import droiddevelopers254.devfestnairobi.database.dao.SessionsDao
import droiddevelopers254.devfestnairobi.datastates.SessionsState
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.utils.DroidCoin
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DayTwoRepo {
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCoin.context)!!.sessionsDao()
    private val executor: Executor = Executors.newSingleThreadExecutor()
    val dayTwoSessions: LiveData<SessionsState>
        get() {
            val sessionsStateMutableLiveData = MutableLiveData<SessionsState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("day_two")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            val sessionsModelList = it.toObjects(SessionsModel::class.java)
                            sessionsStateMutableLiveData.value = SessionsState(sessionsModelList)
                            executor.execute { sessionsDao.saveSession(sessionsModelList) }
                        }
                    }
                    .addOnFailureListener {
                        sessionsStateMutableLiveData.value = SessionsState(null,it.message) }

            return sessionsStateMutableLiveData
        }
}
