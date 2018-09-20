package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.UserModel

data class AuthenticateUserState (
        val isUserExists : Boolean = false,
        val error : String?= null,
        val userModel: UserModel?=null
)
