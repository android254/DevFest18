package droiddevelopers254.devfestnairobi.views.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.adapters.SpeakersAdapter
import droiddevelopers254.devfestnairobi.models.RoomModel
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.models.SpeakersModel
import droiddevelopers254.devfestnairobi.utils.SharedPref.PREF_NAME
import droiddevelopers254.devfestnairobi.viewmodels.SessionDataViewModel
import kotlinx.android.synthetic.main.activity_session_view.*
import kotlinx.android.synthetic.main.content_session_view.*
import kotlinx.android.synthetic.main.room_bottom_sheet.*
import java.util.*

class SessionViewActivity : AppCompatActivity() {
    private var sessionId: Int = 0
    private var roomId: Int = 0
    lateinit var sessionDataViewModel: SessionDataViewModel
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var sessionName: String? = null
    private var dayNumber: String? = null
    internal var documentId: String? = null
    lateinit var sessionsModel1: SessionsModel
    private var databaseReference: DatabaseReference? = null
    private var speakersList: List<SpeakersModel> = ArrayList()
    private var speakerId: List<Int> = ArrayList()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var isStarred: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_view)

        databaseReference = FirebaseDatabase.getInstance().reference
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        //get extras
        val extraIntent = intent
        sessionId = extraIntent.getIntExtra("sessionId", 0)
        dayNumber = extraIntent.getStringExtra("dayNumber")
        sessionName = extraIntent.getStringExtra("sessionName")
        speakerId = extraIntent.getIntegerArrayListExtra("speakerId")!!
        roomId = extraIntent.getIntExtra("roomId", 0)

        sessionDataViewModel = ViewModelProvider(this).get(SessionDataViewModel::class.java)

        getSessionData(sessionId)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    fab.animate().scaleX(0f).scaleY(0f).setDuration(200).start()
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fab.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        for (i in speakerId) {
            getSpeakerDetails(i)
        }

        getRoomDetails(roomId)
        //observe live data emitted by view model
        sessionDataViewModel.sessionData.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                this.handleFetchSessionData(it?.sessionsModel)
            }
        })
        sessionDataViewModel.speakerInfo.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchSpeakerDetails(it?.speakerModelList)
            }
        })

        sessionDataViewModel.roomInfo.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchRoomDetails(it?.roomModel)
            }
        })
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar)

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.action_map) {

                if (bottomSheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)

                }
            }
            false
        }
        //share a session
        fab.setOnClickListener {
            val shareSession = Intent()
            shareSession.action = Intent.ACTION_SEND
            shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" + sessionName + "' at " + getString(R.string.devfestnairobi_hashtag) + "\n" + getString(R.string.devfestnairobi_site))
            shareSession.type = "text/plain"
            startActivity(shareSession)
        }
        //collapse bottom bar
        collapseBottomImg?.setOnClickListener {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

    }
    private fun getSessionData(sessionId: Int) {
        sessionDataViewModel.getSessionDetails(sessionId)
    }

    private fun getRoomDetails(roomId: Int) {
        sessionDataViewModel.fetchRoomDetails(roomId)
    }

    private fun handleFetchRoomDetails(roomModel: RoomModel?) {
        roomDetailsText.text = roomModel?.name
    }

    private fun getSpeakerDetails(speakerId: Int) {
        sessionDataViewModel.fetchSpeakerDetails(speakerId)
    }

    private fun handleFetchSpeakerDetails(speakersModel: List<SpeakersModel>?) {
        if (speakersModel != null) {
            speakersList = speakersModel
            initView()
        } else {
            //if there are no speakers for this session hide views
            speakersLinear.visibility = View.GONE
        }
    }

    private fun initView() {
        val speakersAdapter = SpeakersAdapter(speakersList, applicationContext)
        val layoutManager = LinearLayoutManager(this)
        speakersRV.layoutManager = layoutManager
        speakersRV.itemAnimator = DefaultItemAnimator()
        speakersRV.adapter = speakersAdapter
    }

    private fun handleFetchSessionData(sessionsModel: SessionsModel?) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel
            //check star status
            dayNumber?.let { sessionDataViewModel.isSessionStarredInDb(sessionId, it) }

            //set the data on the view
            txtSessionTime.text = sessionsModel.time
            txtSessionRoom.text = sessionsModel.room
            txtSessionDesc.text = sessionsModel.title
            txtSessionCategory.text = sessionsModel.topic
            sessionViewTitleText.text = sessionsModel.title

        }
    }
    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
    }

}
