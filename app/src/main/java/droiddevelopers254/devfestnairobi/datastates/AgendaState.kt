package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.AgendaModel

data class AgendaState (
        val agendaModelList: List<AgendaModel>? =null,
        val databaseError :String? =null
)
