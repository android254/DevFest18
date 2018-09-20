package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.EventTypeModel

data class EventTypeState (
        val eventTypeModelList: List<EventTypeModel>?,
        val databaseError : String?
)
