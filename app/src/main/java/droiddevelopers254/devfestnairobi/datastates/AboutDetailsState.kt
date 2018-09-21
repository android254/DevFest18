package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.AboutDetailsModel

data class AboutDetailsState (
        val aboutDetailsModelList : List<AboutDetailsModel>? = null,
        val databaseError :String? = null
)

