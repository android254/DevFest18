package droiddevelopers254.devfestnairobi.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.models.TravelInfoModel
import droiddevelopers254.devfestnairobi.repository.TravelInfoRepo

class TravelInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val infoViewModelMediatorLiveData: MediatorLiveData<TravelInfoModel> = MediatorLiveData()
    private val travelInfoRepo: TravelInfoRepo = TravelInfoRepo(application)

    val travelInfo: LiveData<TravelInfoModel>
        get() = infoViewModelMediatorLiveData


    fun fetchRemoteConfigValues() {
        val travelInfoModelLiveData = travelInfoRepo.travelInfo
        infoViewModelMediatorLiveData.addSource(travelInfoModelLiveData
        ) { infoViewModelMediatorLiveData ->
            if (this.infoViewModelMediatorLiveData.hasActiveObservers()) {
                this.infoViewModelMediatorLiveData.removeSource(travelInfoModelLiveData)
            }
            this.infoViewModelMediatorLiveData.setValue(infoViewModelMediatorLiveData)
        }
    }
}
