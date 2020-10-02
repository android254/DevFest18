package droiddevelopers254.devfestnairobi.views.activities

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Toast

import com.google.firebase.database.DatabaseError

import java.util.ArrayList

import droiddevelopers254.devfestnairobi.R
import droiddevelopers254.devfestnairobi.adapters.AboutDetailsAdapter
import droiddevelopers254.devfestnairobi.models.AboutDetailsModel
import droiddevelopers254.devfestnairobi.viewmodels.AboutDetailsViewModel
import kotlinx.android.synthetic.main.content_about_details.*

class AboutDetailsActivity : AppCompatActivity() {
    private var aboutDetailsList: List<AboutDetailsModel> = ArrayList()
    lateinit var aboutDetailsViewModel: AboutDetailsViewModel
    private var aboutType: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutDetailsViewModel = ViewModelProviders.of(this).get(AboutDetailsViewModel::class.java)

        //get intent extras
        val extraIntent = intent
        aboutType = extraIntent.getStringExtra("aboutType")

        if (aboutType == "about_devfest") {
            supportActionBar?.title = getString(R.string.about_devfestnairobi)
        }
        if (aboutType == "organizers") {
            supportActionBar?.title = getString(R.string.organizers)
        }
        if (aboutType == "sponsors") {
            supportActionBar?.title = getString(R.string.sponsors)
        }
        //fetch about details
        aboutType?.let { fetchAboutDetails(it) }

        //observe live data emitted by view model
        aboutDetailsViewModel.aboutDetails.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchAboutDetails(it?.aboutDetailsModelList)
            }
        })
    }
    private fun fetchAboutDetails(aboutType: String) {
        aboutDetailsViewModel.fetchAboutDetails(aboutType)
    }

    private fun handleFetchAboutDetails(aboutDetailsModelList: List<AboutDetailsModel>?) {
        if (aboutDetailsModelList != null) {
            aboutDetailsList = aboutDetailsModelList
            initView()
        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        val aboutDetailsAdapter = AboutDetailsAdapter(aboutDetailsList, applicationContext){
           //handle on click
        }
        val layoutManager = LinearLayoutManager(this)
        aboutDetailsRv.layoutManager = layoutManager
        aboutDetailsRv.itemAnimator = DefaultItemAnimator()
        aboutDetailsRv.adapter = aboutDetailsAdapter
    }



}
