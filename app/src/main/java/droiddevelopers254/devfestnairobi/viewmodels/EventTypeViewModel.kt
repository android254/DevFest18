package droiddevelopers254.devfestnairobi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.devfestnairobi.datastates.EventTypeState
import droiddevelopers254.devfestnairobi.models.WifiDetailsModel
import droiddevelopers254.devfestnairobi.repository.EventTypeRepo
import droiddevelopers254.devfestnairobi.repository.WifiDetailsRepo

class EventTypeViewModel : ViewModel() {
    private val eventTypeModelMediatorLiveData: MediatorLiveData<EventTypeState> = MediatorLiveData()
    private val eventTypeRepo: EventTypeRepo = EventTypeRepo()
    private val wifiDetailsModelMediatorLiveData: MediatorLiveData<WifiDetailsModel> = MediatorLiveData()
    private val wifiDetailsRepo: WifiDetailsRepo = WifiDetailsRepo()
    val sessions: LiveData<EventTypeState>
        get() = eventTypeModelMediatorLiveData

    val wifiDetails: LiveData<WifiDetailsModel>
        get() = wifiDetailsModelMediatorLiveData

    fun fetchSessions() {
        val eventTypeModelLiveData = eventTypeRepo.sessionData
        eventTypeModelMediatorLiveData.addSource(eventTypeModelLiveData
        ) { sessionsModelMediatorLiveData ->
            if (this.eventTypeModelMediatorLiveData.hasActiveObservers()) {
                this.eventTypeModelMediatorLiveData.removeSource(eventTypeModelLiveData)
            }
            this.eventTypeModelMediatorLiveData.setValue(sessionsModelMediatorLiveData)
        }
    }

    fun fetchRemoteConfigValues() {
        val wifiDetailsModelLiveData = wifiDetailsRepo.wifiDetails
        wifiDetailsModelMediatorLiveData.addSource(wifiDetailsModelLiveData
        ) { wifiDetailsModelMediatorLiveData ->
            if (this.wifiDetailsModelMediatorLiveData.hasActiveObservers()) {
                this.wifiDetailsModelMediatorLiveData.removeSource(wifiDetailsModelLiveData)
            }
            this.wifiDetailsModelMediatorLiveData.setValue(wifiDetailsModelMediatorLiveData)
        }
    }
}
