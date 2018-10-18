package id.kampung.footballmatch.view.team


import android.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import id.kampung.footballmatch.adapter.AdapterTeam
import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.PlayerModel
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.helper.EventBusHelper
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import  id.kampung.footballmatch.repository.RemoteRepository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.toast

class TeamMainFragment : Fragment(),TeamContract {



    private var listItem: MutableList<TeamModel> = mutableListOf()
    private lateinit var adapterList: AdapterTeam
    private lateinit var presenter: TeamPresenter
    private lateinit var spinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = TeamMainFragmentUI().createView(AnkoContext.create(requireContext(), this))
        adapterList = AdapterTeam( requireContext(),listItem)
        with(view.findViewById<RecyclerView>(TeamMainFragmentUI.recyclerViewId)) {
            adapter = adapterList
        }

        presenter = TeamPresenter(this,RemoteRepository())



        spinner = view.findViewById(TeamMainFragmentUI.spinerIDLast)
        presenter.getLeagues()
        return view
    }


    override fun onLeaguesGet(leaguesList: LeaguesList?) {
        val stringArray = leaguesList!!.leagues.map {
            it.strLeague
        }

        val idArray  = leaguesList.leagues.map {
            it.idLeague
        }

        spinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item,stringArray)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getAllTeamLeaguen(idArray[position])
            }

        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun search(eventBusHelper: EventBusHelper) {
        if (eventBusHelper.type == 2) {

//            toast("hallo"+eventBusHelper.type+eventBusHelper.message)
            adapterList.filter.filter(eventBusHelper.message)
        }
    }

    override fun onFailed(message: String?) {
        toast(message.toString())
    }

    override fun onSuccess(eventsList: List<TeamModel>?) {
        listItem.clear()
        eventsList?.let { listItem.addAll(it) }
        adapterList.notifyDataSetChanged()
    }
    override fun onSuccesFavorite(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesUnfavorite(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesPlayer(playerModel: List<PlayerModel>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    companion object {
        fun newInsance(): TeamMainFragment {
            return TeamMainFragment()
        }
    }

    class TeamMainFragmentUI : AnkoComponent<TeamMainFragment>{

        companion object {
            const val recyclerViewId = 33
            const val spinerIDLast = 254
        }
        override fun createView(ui: AnkoContext<TeamMainFragment>)= with(ui){
            verticalLayout {

                    verticalLayout {
                        spinner {
                            id = spinerIDLast
                        }.lparams(matchParent, wrapContent)
                        recyclerView {
                            id = recyclerViewId
                            layoutManager = LinearLayoutManager(context)
                        }
                    }

            }
        }

    }
}
