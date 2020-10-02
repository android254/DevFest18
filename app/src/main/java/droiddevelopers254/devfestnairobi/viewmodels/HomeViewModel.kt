package droiddevelopers254.devfestnairobi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.datastates.FiltersState
import droiddevelopers254.devfestnairobi.datastates.UpdateTokenState
import droiddevelopers254.devfestnairobi.models.FiltersModel
import droiddevelopers254.devfestnairobi.models.UserModel
import droiddevelopers254.devfestnairobi.repository.TopicFiltersRepo
import droiddevelopers254.devfestnairobi.repository.TypeFiltersRepo
import droiddevelopers254.devfestnairobi.repository.SaveUserRepo

class HomeViewModel : ViewModel() {
    private val filtersList: LiveData<List<FiltersModel>>? = null
    private val typeFiltersRepo: TypeFiltersRepo = TypeFiltersRepo()
    private val filtersStateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val stateMediatorLiveData: MediatorLiveData<FiltersState> = MediatorLiveData()
    private val updateTokenStateMediatorLiveData: MediatorLiveData<UpdateTokenState> = MediatorLiveData()
    private val topicFiltersRepo: TopicFiltersRepo = TopicFiltersRepo()
    private val saveUserRepo: SaveUserRepo = SaveUserRepo()

    val typeFiltersResponse: LiveData<FiltersState>
        get() = filtersStateMediatorLiveData
    val topicFiltersResponse: LiveData<FiltersState>
        get() = stateMediatorLiveData
    val updateTokenResponse: LiveData<UpdateTokenState>
        get() = updateTokenStateMediatorLiveData

    fun getTypeFilters() {
        val filtersStateLiveData = typeFiltersRepo.filters
        filtersStateMediatorLiveData.addSource(filtersStateLiveData
        ) { filtersStateMediatorLiveData ->
            if (this.filtersStateMediatorLiveData.hasActiveObservers()) {
                this.filtersStateMediatorLiveData.removeSource(filtersStateLiveData)
            }
            this.filtersStateMediatorLiveData.setValue(filtersStateMediatorLiveData)
        }
    }

    fun getTopicFilters() {
        val filtersStateLiveData = topicFiltersRepo.filters
        stateMediatorLiveData.addSource(filtersStateLiveData
        ) { stateMediatorLiveData ->
            if (this.stateMediatorLiveData.hasActiveObservers()) {
                this.stateMediatorLiveData.removeSource(filtersStateLiveData)
            }
            this.stateMediatorLiveData.setValue(stateMediatorLiveData)
        }

    }
    fun saveUser(user : UserModel) {
        val updateTokenStateLiveData = saveUserRepo.saveUser(user)
        updateTokenStateMediatorLiveData.addSource(updateTokenStateLiveData
        ) { updateTokenStateMediatorLiveData ->
            if (this.updateTokenStateMediatorLiveData.hasActiveObservers()) {
                this.updateTokenStateMediatorLiveData.removeSource(updateTokenStateLiveData)
            }
            this.updateTokenStateMediatorLiveData.setValue(updateTokenStateMediatorLiveData)
        }
    }
}
