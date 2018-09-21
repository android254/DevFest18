package droiddevelopers254.devfestnairobi.datastates

import droiddevelopers254.devfestnairobi.models.FiltersModel

data class FiltersState (
        val filtersModelList: List<FiltersModel>?,
        val databaseError : String?
)
