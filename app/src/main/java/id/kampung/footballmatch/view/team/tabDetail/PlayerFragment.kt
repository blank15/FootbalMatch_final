package id.kampung.footballmatch.view.team.tabDetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kampung.footballmatch.adapter.AdapterPlayer
import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.data.model.PlayerModel
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.view.team.TeamContract
import id.kampung.footballmatch.view.team.TeamPresenter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.toast
import id.kampung.footballmatch.repository.RemoteRepository


class PlayerFragment : Fragment(), TeamContract {


    private var listItem: MutableList<PlayerModel> = mutableListOf()
    private lateinit var adapterList: AdapterPlayer
    private lateinit var presenter: TeamPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = PlayerFragmentUI().createView(AnkoContext.create(requireContext(), this))
        val data = arguments?.get("data") as TeamModel

        adapterList = AdapterPlayer(requireContext(), listItem)
        with(view.findViewById<RecyclerView>(PlayerFragmentUI.recyclerViewId)) {
            adapter = adapterList
        }


        presenter = TeamPresenter(this, RemoteRepository())

        presenter.getAllPlayer(data.idTeam)
        return view
    }

    companion object {
        fun newInsance(teamModel: TeamModel) = PlayerFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable("data", teamModel)
                    }
                }
    }


    override fun onFailed(message: String?) {
        toast(message.toString())
    }

    override fun onSuccess(eventsList: List<TeamModel>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesFavorite(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesUnfavorite(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccesPlayer(playerModel: List<PlayerModel>?) {
        listItem.clear()
        playerModel?.let { listItem.addAll(it) }
        adapterList.notifyDataSetChanged()
    }

    override fun onLeaguesGet(leaguesList: LeaguesList?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class PlayerFragmentUI : AnkoComponent<PlayerFragment> {

        companion object {
            const val recyclerViewId = 333
        }

        override fun createView(ui: AnkoContext<PlayerFragment>) = with(ui) {
            verticalLayout {

                verticalLayout {
                    recyclerView {
                        id = recyclerViewId
                        layoutManager = LinearLayoutManager(context)
                    }
                }

            }
        }

    }

}
