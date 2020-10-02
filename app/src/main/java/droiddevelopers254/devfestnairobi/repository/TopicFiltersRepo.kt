package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.devfestnairobi.datastates.FiltersState
import droiddevelopers254.devfestnairobi.models.FiltersModel

class TopicFiltersRepo {

    val filters: LiveData<FiltersState>
        get() {
            val filtersStateMutableLiveData = MutableLiveData<FiltersState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("session_topics")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        val filtersModelList = it.toObjects(FiltersModel::class.java)
                        filtersStateMutableLiveData.setValue(FiltersState(filtersModelList,null))

                    }
                    .addOnFailureListener {
                        filtersStateMutableLiveData.setValue(FiltersState(null,it.message)) }
            return filtersStateMutableLiveData
        }
}
