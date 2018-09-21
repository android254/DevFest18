package droiddevelopers254.devfestnairobi.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.models.AgendaModel
import kotlinx.android.synthetic.main.agenda_details.view.*

class AgendaAdapter(private val agendaModelList: List<AgendaModel>, private val context: Context) : RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val agendaTitleText = itemView.agendaTitleText
        private val agendaTimelineText = itemView.agendaTimelineText
        private val agendaImg =itemView.agendaImg
        private val agendaLinear = itemView.agendaLinear

        fun bindAgendas(agendaModel: AgendaModel){
            with(agendaModel){
                //TODO add logic for changing agenda icon
                itemView.agendaTitleText.text = title
                itemView.agendaTimelineText.text = time
                itemView.agendaLinear.setBackgroundColor(Color.parseColor(background_color))

                when(event_type){
                    "1" -> itemView.agendaImg.setImageResource(R.drawable.ic_star_black_24dp)
                    "2" -> itemView.agendaImg.setImageResource(R.drawable.ic_code_black_24dp)
                    "3" -> itemView.agendaImg.setImageResource(R.drawable.ic_restaurant_black_24dp)
                    "4" -> itemView.agendaImg.setImageResource(R.drawable.ic_music_note_black_24dp)
                    "5" -> itemView.agendaImg.setImageResource(R.drawable.ic_supervisor_account_black_24dp)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.agenda_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bindAgendas(agendaModelList[position])
    }
    override fun getItemCount(): Int {
        return agendaModelList.size
    }


}
