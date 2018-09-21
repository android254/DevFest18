package droiddevelopers254.devfestnairobi.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.datastates.SessionsState
import droiddevelopers254.devfestnairobi.repository.DayTwoRepo
import droiddevelopers254.devfestnairobi.repository.RoomStarrSessionRepo

class DayTwoViewModel : ViewModel() {
    private val sessionsStateMediatorLiveData: MediatorLiveData<SessionsState> = MediatorLiveData()
    private val dayTwoRepo: DayTwoRepo = DayTwoRepo()
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

    val sessions: LiveData<SessionsState>
        get() = sessionsStateMediatorLiveData

    fun getDayTwoSessions() {
        val sessionsStateLiveData = dayTwoRepo.dayTwoSessions
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData
        ) { sessionsStateMediatorLiveData ->
            if (this.sessionsStateMediatorLiveData.hasActiveObservers()) {
                this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData)
            }
            this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData)
        }
    }

}
