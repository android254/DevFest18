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
import droiddevelopers254.devfestnairobi.models.AboutDetailsModel
import kotlinx.android.synthetic.main.about_details.view.*

class AboutDetailsAdapter(private val aboutDetailsModelList: List<AboutDetailsModel>, private val context: Context, private val itemClick: (AboutDetailsModel) -> Unit) : RecyclerView.Adapter<AboutDetailsAdapter.MyViewHolder>() {

     class MyViewHolder(itemView: View, private val itemClick: (AboutDetailsModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
         fun bindAboutDetails(aboutDetailsModel: AboutDetailsModel){
             with(aboutDetailsModel){
                 Glide.with(itemView.context).load(logoUrl)
                         .thumbnail(Glide.with(itemView.context).load(logoUrl))
                         .apply(RequestOptions()
                                 .centerCrop()
                                 .diskCacheStrategy(DiskCacheStrategy.ALL))
                         .transition(DrawableTransitionOptions()
                                 .crossFade())
                         .into(itemView.aboutDetailsImg)

                 itemView.aboutDetailsDescText.text = bio
                 itemView.aboutDetailsTitleText.text = name

                 itemView.setOnClickListener {
                     itemClick(this)
             }
             }
         }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.about_details, parent, false)
        return MyViewHolder(itemView,itemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAboutDetails(aboutDetailsModelList[position])
    }
    override fun getItemCount(): Int {
        return aboutDetailsModelList.size
    }

}
