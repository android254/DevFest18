package droiddevelopers254.devfestnairobi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.devfestnairobi.database.AppDatabase
import droiddevelopers254.devfestnairobi.database.dao.SessionsDao
import droiddevelopers254.devfestnairobi.datastates.SessionsState
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.utils.DroidCoin
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DayOneRepo {
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCoin.context)!!.sessionsDao()
    private val executor: Executor =  Executors.newSingleThreadExecutor()

    val dayOneSessions: LiveData<SessionsState>
        get() {
            val sessionsStateMutableLiveData = MutableLiveData<SessionsState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("schedule")
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
                        sessionsStateMutableLiveData.value = SessionsState(null,it.message)}

            return sessionsStateMutableLiveData
        }


}
