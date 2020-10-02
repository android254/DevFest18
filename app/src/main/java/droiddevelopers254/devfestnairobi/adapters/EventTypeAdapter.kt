package droiddevelopers254.devfestnairobi.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.models.EventTypeModel
import kotlinx.android.synthetic.main.event_type_details.view.*

class EventTypeAdapter(private val eventTypesList: List<EventTypeModel>, private val context: Context) : RecyclerView.Adapter<EventTypeAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindEvents(eventTypeModel: EventTypeModel){
            with(eventTypeModel){
                itemView.eventNameText.text = name
                itemView.eventDescriptionText.text = description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTypeAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_type_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventTypeAdapter.MyViewHolder, position: Int) {
      holder.bindEvents(eventTypesList[position])
    }

    override fun getItemCount(): Int {
        return eventTypesList.size
    }


}
