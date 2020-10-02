package droiddevelopers254.devfestnairobi.views.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.adapters.SessionsAdapter
import droiddevelopers254.devfestnairobi.models.SessionTimeModel
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.utils.ItemClickListener
import droiddevelopers254.devfestnairobi.viewmodels.DayOneViewModel
import droiddevelopers254.devfestnairobi.views.activities.SessionViewActivity
import kotlinx.android.synthetic.main.fragment_day_one.view.*
import java.util.*

class DayOneFragment : Fragment() {

    internal var sessionsModelList: List<SessionsModel> = ArrayList()
    lateinit var dayOneViewModel: DayOneViewModel
    internal var isStarred: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_one, container, false)

        dayOneViewModel = ViewModelProviders.of(this).get(DayOneViewModel::class.java)
        if (sessionsModelList.isEmpty()){
            view.swipeRefresh.isRefreshing = true
        }
        view.swipeRefresh.setOnRefreshListener {
            dayOneViewModel.getDayOneSessions()
        }
        val sessionsRv = view.sessionsRv

        dayOneViewModel.getDayOneSessions()
        //observe live data emitted by view model
        dayOneViewModel.sessions.observe(this, Observer{
            view.swipeRefresh.isRefreshing = false
            if (it?.sessionsModelList != null) {
                sessionsModelList = it.sessionsModelList
                initView(sessionsRv)
            } else {
                handleError(it?.databaseError)
            }
        })

        return view
    }
    private fun handleError(databaseError: String?) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }
    private fun initView(sessionsRv: RecyclerView) {
        val sessionsAdapter = SessionsAdapter(activity!!, sessionsModelList, "day_one")
        val layoutManager = LinearLayoutManager(activity)
        sessionsRv.layoutManager = layoutManager
        sessionsRv.itemAnimator = DefaultItemAnimator()
        sessionsRv.adapter = sessionsAdapter
        sessionsRv.addOnItemTouchListener(ItemClickListener(context,sessionsRv, object : ItemClickListener.ClickListener {
           override fun onClick(view: View, position: Int) {
                val intent = Intent(context, SessionViewActivity::class.java)
                intent.putExtra("sessionId", sessionsModelList[position].id)
                intent.putExtra("dayNumber", "day_one")
                intent.putExtra("sessionName", sessionsModelList[position].title)
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList[position].speaker_id)
                intent.putExtra("roomId", sessionsModelList[position].room_id)
                startActivity(intent)
            }
            override fun onLongClick(view: View, position: Int) {
            }
        }))

    }

}
