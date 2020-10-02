package droiddevelopers254.devfestnairobi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.datastates.RoomState
import droiddevelopers254.devfestnairobi.datastates.SessionDataState
import droiddevelopers254.devfestnairobi.datastates.SpeakersState
import droiddevelopers254.devfestnairobi.datastates.StarSessionState
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.models.StarredSessionModel
import droiddevelopers254.devfestnairobi.repository.RoomRepo
import droiddevelopers254.devfestnairobi.repository.SessionDataRepo
import droiddevelopers254.devfestnairobi.repository.SpeakersRepo
import droiddevelopers254.devfestnairobi.repository.FirebaseStarSessionRepo
import droiddevelopers254.devfestnairobi.repository.RoomStarrSessionRepo

class SessionDataViewModel : ViewModel() {
    private val sessionDataStateMediatorLiveData: MediatorLiveData<SessionDataState> = MediatorLiveData()
    private val sessionDataRepo: SessionDataRepo = SessionDataRepo()
    private val speakersStateMediatorLiveData: MediatorLiveData<SpeakersState> = MediatorLiveData()
    private val speakersRepo: SpeakersRepo = SpeakersRepo()
    private val roomStateMediatorLiveData: MediatorLiveData<RoomState> = MediatorLiveData()
    private val roomRepo: RoomRepo = RoomRepo()
    private val starMediatorLiveData: MediatorLiveData<StarSessionState> = MediatorLiveData()
    private val unStarMediatorLiveData: MediatorLiveData<StarSessionState> = MediatorLiveData()
    private val starStatusMediatorLiveData: MediatorLiveData<StarSessionState> = MediatorLiveData()
    private val firebaseStarSessionRepo: FirebaseStarSessionRepo = FirebaseStarSessionRepo()
    private val sessionsModelMediatorLiveData: MediatorLiveData<SessionsModel> = MediatorLiveData()
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()
    private val booleanMediatorLiveData: MediatorLiveData<Int> = MediatorLiveData()

    val session: LiveData<SessionsModel>
        get() = sessionsModelMediatorLiveData

    val sessionData: LiveData<SessionDataState>
        get() = sessionDataStateMediatorLiveData

    val speakerInfo: LiveData<SpeakersState>
        get() = speakersStateMediatorLiveData

    val roomInfo: LiveData<RoomState>
        get() = roomStateMediatorLiveData

    val dbStarStatus: LiveData<Int>
        get() = booleanMediatorLiveData

    fun starSessionResponse(): LiveData<StarSessionState> {
        return starMediatorLiveData
    }

    fun unstarSessionResponse(): LiveData<StarSessionState> {
        return unStarMediatorLiveData
    }


    fun fetchSpeakerDetails(speakerId: Int) {
        val speakersStateLiveData = speakersRepo.getSpeakersInfo(speakerId)
        speakersStateMediatorLiveData.addSource(speakersStateLiveData
        ) { speakersStateMediatorLiveData ->
            if (this.speakersStateMediatorLiveData.hasActiveObservers()) {
                this.speakersStateMediatorLiveData.removeSource(speakersStateLiveData)
            }
            this.speakersStateMediatorLiveData.setValue(speakersStateMediatorLiveData)
        }
    }

    fun fetchRoomDetails(roomId: Int) {
        val roomStateLiveData = roomRepo.getRoomDetails(roomId)
        roomStateMediatorLiveData.addSource(roomStateLiveData
        ) { roomStateMediatorLiveData ->
            if (this.roomStateMediatorLiveData.hasActiveObservers()) {
                this.roomStateMediatorLiveData.removeSource(roomStateLiveData)
            }
            this.roomStateMediatorLiveData.setValue(roomStateMediatorLiveData)
        }
    }

    fun getStarStatus(sessionId: String, userId: String) {
        val starSessionStateLiveData = firebaseStarSessionRepo.checkStarStatus(sessionId, userId)
        starStatusMediatorLiveData.addSource(starSessionStateLiveData
        ) { starStatusMediatorLiveData ->
            if (this.starStatusMediatorLiveData.hasActiveObservers()) {
                this.starStatusMediatorLiveData.removeSource(starSessionStateLiveData)
            }
            this.starStatusMediatorLiveData.setValue(starStatusMediatorLiveData)
        }
    }

    fun starSession(starredSessionModel: StarredSessionModel, userId: String) {
        val starSessionStateLiveData = firebaseStarSessionRepo.starSession(starredSessionModel, userId)
        starMediatorLiveData.addSource(starSessionStateLiveData
        ) { starMediatorLiveData ->
            if (this.starMediatorLiveData.hasActiveObservers()) {
                this.starMediatorLiveData.removeSource(starSessionStateLiveData)
            }
            this.starMediatorLiveData.setValue(starMediatorLiveData)
        }
    }

    fun unStarSession(sessionId: String, userId: String, starred: Boolean) {
        val starSessionStateLiveData = firebaseStarSessionRepo.unStarSession(sessionId, userId, starred)
        unStarMediatorLiveData.addSource(starSessionStateLiveData
        ) { unStarMediatorLiveData ->
            if (this.unStarMediatorLiveData.hasActiveObservers()) {
                this.unStarMediatorLiveData.removeSource(starSessionStateLiveData)
            }
            this.unStarMediatorLiveData.setValue(unStarMediatorLiveData)
        }
    }

    fun getSessionDetails(sessionId: Int) {
        val sessionsModelLiveData = sessionDataRepo.getSessionData(sessionId)
        sessionDataStateMediatorLiveData.addSource(sessionsModelLiveData
        ) { sessionDataStateMediatorLiveData ->
            if (this.sessionDataStateMediatorLiveData.hasActiveObservers()) {
                this.sessionDataStateMediatorLiveData.removeSource(sessionsModelLiveData)

            }
            this.sessionDataStateMediatorLiveData.setValue(sessionDataStateMediatorLiveData)
        }
    }

    fun starrSessionInDb(sessionId: Int, isStarred: String, dayNumber: String) {
        roomStarrSessionRepo.starrSession(sessionId, isStarred, dayNumber)
    }

    fun isSessionStarredInDb(sessionId: Int, dayNumber: String) {
        val booleanLiveData = roomStarrSessionRepo.isSessionStarred(sessionId, dayNumber)
        booleanMediatorLiveData.addSource(booleanLiveData
        ) { booleanMediatorLiveData ->
            if (this.booleanMediatorLiveData.hasActiveObservers()) {
                this.booleanMediatorLiveData.removeSource(booleanLiveData)
            }
            this.booleanMediatorLiveData.setValue(booleanMediatorLiveData)
        }
    }

    fun unstarrSessionInDb(sessionId: Int, isStarred: String, dayNumber: String) {
        roomStarrSessionRepo.unStarrSession(sessionId, isStarred, dayNumber)
    }
}
