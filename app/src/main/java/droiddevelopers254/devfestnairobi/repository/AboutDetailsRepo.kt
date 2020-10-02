package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.devfestnairobi.datastates.AboutDetailsState
import droiddevelopers254.devfestnairobi.models.AboutDetailsModel

class AboutDetailsRepo {

    fun getAboutDetails(aboutType: String): LiveData<AboutDetailsState> {
        val detailsStateMutableLiveData = MutableLiveData<AboutDetailsState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(aboutType)
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {
                    val aboutDetailsModelList = it.toObjects(AboutDetailsModel::class.java)
                    detailsStateMutableLiveData.setValue(AboutDetailsState(aboutDetailsModelList))
                }
                .addOnFailureListener { e -> detailsStateMutableLiveData.setValue(AboutDetailsState(null,e.message)) }
        return detailsStateMutableLiveData
    }
}
