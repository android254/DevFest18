package droiddevelopers254.devfestnairobi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.devfestnairobi.datastates.AuthenticateUserState
import droiddevelopers254.devfestnairobi.models.UserModel

class AuthenticateUserRepo {

    fun checkUserExistence(firebaseUser: FirebaseUser): LiveData<AuthenticateUserState> {
        val userStateMutableLiveData = MutableLiveData<AuthenticateUserState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document(firebaseUser.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot = task.result
                        if (!documentSnapshot.exists()) {
                            val user = UserModel(
                                     firebaseUser.uid,
                                    null,
                                    firebaseUser.email.toString(),
                                    firebaseUser.displayName.toString(),
                                    firebaseUser.photoUrl.toString()
                            )
                            //save user in firestore
                            firebaseFirestore.collection("users").document(user.user_id)
                                    .set(user)
                                    .addOnSuccessListener {
                                        userStateMutableLiveData.setValue(AuthenticateUserState(true,null,null)) }
                                    .addOnFailureListener {
                                        userStateMutableLiveData.setValue(AuthenticateUserState(false,it.message)) }
                        }else{
                            userStateMutableLiveData.value = AuthenticateUserState(false,null,documentSnapshot.toObject(UserModel::class.java))
                        }
                    }
                }
        return userStateMutableLiveData
    }

}
