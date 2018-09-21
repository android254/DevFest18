package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.SessionsModel

data class SessionsState (
        val sessionsModelList: List<SessionsModel>?=null,
        val databaseError : String?=null
)
