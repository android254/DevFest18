package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.SpeakersModel

data class SpeakersState (
        val speakerModelList: List<SpeakersModel>?,
        val databaseError : String?
)
