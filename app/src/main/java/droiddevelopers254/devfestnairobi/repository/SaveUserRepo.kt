package droiddevelopers254.devfestnairobi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.devfestnairobi.datastates.UpdateTokenState
import droiddevelopers254.devfestnairobi.models.UserModel

class SaveUserRepo {

    fun saveUser(user : UserModel): LiveData<UpdateTokenState> {
        val updateTokenStateMutableLiveData = MutableLiveData<UpdateTokenState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document(user.user_id)
                .set(user)
                .addOnSuccessListener {
                    updateTokenStateMutableLiveData.setValue(UpdateTokenState(true,null)) }
                .addOnFailureListener {
                    updateTokenStateMutableLiveData.setValue(UpdateTokenState(false,it.message)) }
        return updateTokenStateMutableLiveData
    }
}
