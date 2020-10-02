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
import droiddevelopers254.devfestnairobi.models.SessionsModel
import droiddevelopers254.devfestnairobi.utils.ItemClickListener
import droiddevelopers254.devfestnairobi.viewmodels.DayTwoViewModel
import droiddevelopers254.devfestnairobi.views.activities.SessionViewActivity
import kotlinx.android.synthetic.main.fragment_day_two.view.*

class DayTwoFragment : Fragment() {
    lateinit var dayTwoViewModel: DayTwoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_two, container, false)

        dayTwoViewModel = ViewModelProviders.of(this).get(DayTwoViewModel::class.java)

        val sessionsRv =view.sessionsRv

        dayTwoViewModel.getDayTwoSessions()
        //observe live data emitted by view model
        dayTwoViewModel.sessions.observe(this, Observer{
            if (it?.sessionsModelList != null) {
                val sessionsModelList = it.sessionsModelList
                initView(sessionsModelList,sessionsRv)
            } else {
                handleError(it?.databaseError)
            }
        })
        return view
    }

    private fun handleError(databaseError: String?) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(sessionsModelList: List<SessionsModel>, sessionsRv: RecyclerView) {
        val sessionsAdapter = SessionsAdapter(activity!!, sessionsModelList, "day_two")
        val layoutManager = LinearLayoutManager(activity)
        sessionsRv.layoutManager = layoutManager
        sessionsRv.itemAnimator = DefaultItemAnimator()
        sessionsRv.adapter = sessionsAdapter
        sessionsRv.addOnItemTouchListener(ItemClickListener(activity, sessionsRv, object : ItemClickListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, SessionViewActivity::class.java)
                intent.putExtra("sessionId", sessionsModelList[position].id)
                intent.putExtra("dayNumber", "day_two")
                intent.putExtra("starred", sessionsModelList[position].starred)
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList[position].speaker_id)
                intent.putExtra("roomId", sessionsModelList[position].room_id)
                startActivity(intent)
            }
            override fun onLongClick(view: View, position: Int) {

            }
        }))
    }

}
