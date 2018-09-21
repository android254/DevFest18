package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.StarredSessionModel

data class StarSessionState (
        val isStarred : Boolean = false,
        val databaseError : String?,
        val starredSessionModel: StarredSessionModel?,
        val starMessage : Int?
)