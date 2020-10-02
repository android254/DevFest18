package droiddevelopers254.devfestnairobi.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.models.SpeakersModel
import kotlinx.android.synthetic.main.speaker_details.view.*

class SpeakersAdapter(private val speakersList: List<SpeakersModel>, private val context: Context) : RecyclerView.Adapter<SpeakersAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       fun bindSpeakerDetails(speakersModel: SpeakersModel){
           with(speakersModel){
               Glide.with(itemView.context).load(photoUrl)
                       .thumbnail(Glide.with(itemView.context).load(photoUrl))
                       .apply(RequestOptions()
                               .centerCrop()
                               .diskCacheStrategy(DiskCacheStrategy.ALL)
                               .placeholder(R.drawable.profile))
                       .transition(DrawableTransitionOptions()
                               .crossFade())
                       .into(itemView.speakerImg)

               itemView.speakerNameText.text = name
               itemView.speakerCompanyText.text = company

           }
       }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakersAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.speaker_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpeakersAdapter.MyViewHolder, position: Int) {
     holder.bindSpeakerDetails(speakersList[position])
    }

    override fun getItemCount(): Int {
        return speakersList.size
    }

}
