package droiddevelopers254.devfestnairobi.views.fragments


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import droiddevelopers254.devfestnairobi.BuildConfig
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.adapters.EventTypeAdapter
import droiddevelopers254.devfestnairobi.models.EventTypeModel
import droiddevelopers254.devfestnairobi.models.WifiDetailsModel
import droiddevelopers254.devfestnairobi.viewmodels.EventTypeViewModel
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {
    lateinit var eventTypeViewModel: EventTypeViewModel
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        eventTypeViewModel = ViewModelProviders.of(this).get(EventTypeViewModel::class.java)
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val wifiSsidText = view.wifiSsidText
        val wifiPasswordText = view.wifiPasswordText
        val eventTypesRv= view.eventTypesRv

        // [START enable_dev_mode]
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig.setConfigSettings(configSettings)

        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)

        //observe live data emitted by view model
        eventTypeViewModel.sessions.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchEventsResponse(it?.eventTypeModelList,eventTypesRv)
            }
        })
        //fetch data from firebase
        eventTypeViewModel.fetchSessions()

        //get remote config values
        getRemoteConfigValues(wifiSsidText,wifiPasswordText)

        return view
    }

    private fun getRemoteConfigValues(wifiSsidText: TextView, wifiPasswordText: TextView) {
        var cacheExpiration: Long = 3600

        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity!!) {
                    if (it.isSuccessful) {
                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig.activateFetched()
                    } else {

                    }
                    val wifiDetailsModel = WifiDetailsModel(firebaseRemoteConfig.getString("wifi_ssid"), firebaseRemoteConfig.getString("wifi_password"))
                    updateViews(wifiDetailsModel,wifiSsidText,wifiPasswordText)

                }
    }

    private fun updateViews(wifiDetailsModel: WifiDetailsModel, wifiSsidText: TextView, wifiPasswordText: TextView) {
        wifiSsidText.text = wifiDetailsModel.wifiSsid
        wifiPasswordText.text = wifiDetailsModel.wifiPassword
    }

    private fun handleFetchEventsResponse(eventTypeModelList: List<EventTypeModel>?, eventTypesRv: RecyclerView) {
        if (eventTypeModelList != null) {
            initView(eventTypeModelList,eventTypesRv)
        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(eventTypeModelList: List<EventTypeModel>,eventTypesRv : RecyclerView) {
        val layoutManager = LinearLayoutManager(activity)
        eventTypesRv.layoutManager = layoutManager
        eventTypesRv.isNestedScrollingEnabled = false
        eventTypesRv.itemAnimator = DefaultItemAnimator()
        eventTypesRv.adapter = EventTypeAdapter(eventTypeModelList,activity!!)

    }

}
