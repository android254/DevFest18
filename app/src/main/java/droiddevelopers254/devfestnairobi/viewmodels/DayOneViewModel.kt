package droiddevelopers254.devfestnairobi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.datastates.SessionsState
import droiddevelopers254.devfestnairobi.repository.DayOneRepo
import droiddevelopers254.devfestnairobi.repository.RoomStarrSessionRepo

class DayOneViewModel : ViewModel() {
    private val sessionsStateMediatorLiveData: MediatorLiveData<SessionsState> = MediatorLiveData()
    private val dayOneRepo: DayOneRepo = DayOneRepo()
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

    val sessions: LiveData<SessionsState>
        get() = sessionsStateMediatorLiveData

    fun getDayOneSessions() {
        val sessionsStateLiveData = dayOneRepo.dayOneSessions
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData
        ) { sessionsStateMediatorLiveData ->
            if (this.sessionsStateMediatorLiveData.hasActiveObservers()) {
                this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData)
            }
            this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData)
        }
    }
}
