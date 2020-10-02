package droiddevelopers254.devfestnairobi.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.models.SessionTimeModel
import kotlinx.android.synthetic.main.session_time_details.view.*

class SessionTimeAdapter(private val context: Context, private val sessionTimeModelList: List<SessionTimeModel>) : RecyclerView.Adapter<SessionTimeAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindSessionTime(sessionTimeModel: SessionTimeModel){
            with(sessionTimeModel){
                itemView.timeSessionText.text = sessionHour
                itemView.amSessionTimeText.text = amPm
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.session_time_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSessionTime(sessionTimeModelList[position])
    }
    override fun getItemCount(): Int {
        return sessionTimeModelList.size
    }
}
