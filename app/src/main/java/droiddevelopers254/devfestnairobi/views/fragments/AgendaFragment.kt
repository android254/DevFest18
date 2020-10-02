package droiddevelopers254.devfestnairobi.views.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import droiddevelopers254.devfestnairobi.adapters.AgendaAdapter
import droiddevelopers254.devfestnairobi.models.AgendaModel
import droiddevelopers254.devfestnairobi.viewmodels.AgendaViewModel
import kotlinx.android.synthetic.main.fragment_agenda.view.*
import java.util.*

class AgendaFragment : Fragment() {
    private var agendaModelList: List<AgendaModel> = ArrayList()
    lateinit var agendaViewModel: AgendaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agenda, container, false)

        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)

        val agendaRv = view.agendaRv
        //fetch agendas
        agendaViewModel.fetchAgendas()

        //observe live data emitted by view model
        agendaViewModel.agendas.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleAgendaResponse(it?.agendaModelList,agendaRv)
            }
        })
        return view
    }

    private fun handleAgendaResponse(agendaList: List<AgendaModel>?, agendaRv: RecyclerView) {
        if (agendaList != null) {
            agendaModelList = agendaList
            initView(agendaRv)
        }
    }
    private fun handleDatabaseError(databaseError: String) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(agendaRv: RecyclerView) {
        val agendaAdapter = AgendaAdapter(agendaModelList, context!!)
        val layoutManager = LinearLayoutManager(activity)
        agendaRv.layoutManager = layoutManager
        agendaRv.itemAnimator = DefaultItemAnimator()
        agendaRv.adapter = agendaAdapter
    }

}
