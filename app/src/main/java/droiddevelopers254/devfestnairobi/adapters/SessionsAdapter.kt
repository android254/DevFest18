package droiddevelopers254.devfestnairobi.adapters

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.models.SessionsModel
import kotlinx.android.synthetic.main.session_details.view.*

class SessionsAdapter(private val context: Context, private val sessionsModelList: List<SessionsModel>, private val dayNumber: String) : RecyclerView.Adapter<SessionsAdapter.MyViewHolder>() {

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       fun bindSession(sessionsModel: SessionsModel){
           with(sessionsModel){
               itemView.sessionTitleText.text = title
               itemView.sessionLabelText.setBackgroundColor(Color.parseColor(session_color))
               itemView.sessionCategoryText.text = topic
               itemView.sessionRoomText.text= "$duration / $room / $time"
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.session_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bindSession(sessionsModelList[position])
    }

    override fun getItemCount(): Int {
        return sessionsModelList.size
    }


}
