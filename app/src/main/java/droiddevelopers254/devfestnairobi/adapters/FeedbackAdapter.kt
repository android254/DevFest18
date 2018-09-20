package droiddevelopers254.devfestnairobi.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.devfestnairobi.R

class FeedbackAdapter : RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_type_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedbackAdapter.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

}
