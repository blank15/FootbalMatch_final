package id.kampung.footballmatch.view.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import id.kampung.footballmatch.adapter.AdapterMatch
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.LeaguesList
import id.kampung.footballmatch.helper.EventBusHelper
import id.kampung.footballmatch.view.detailMatch.DetailMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.kampung.footballmatch.repository.RemoteRepository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NextMatch : Fragment(), MatchContract, AdapterMatch.OnClickListener {


    private var listItem: MutableList<EventModel> = mutableListOf()
    private lateinit var adapterList: AdapterMatch
    private lateinit var presenter: MatchPresenter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = NextMatchUI().createView(AnkoContext.create(requireContext(), this))

        adapterList = AdapterMatch(listItem, requireContext(), this, true)

        with(view.findViewById<RecyclerView>(NextMatchUI.recyclerViewId)) {
            adapter = adapterList
        }

        presenter = MatchPresenter(this, RemoteRepository())
        presenter.getNextMatch()

        with(view.findViewById<SwipeRefreshLayout>(NextMatchUI.swipeRefreshId)) {
            swipeLayout = this
            setOnRefreshListener { presenter.getNextMatch() }
        }

        with(view.findViewById<Spinner>(NextMatchUI.spinerID)) {
            spinner = this
        }
        presenter.getLeagues()

        return view
    }

    override fun onLeaguesGet(leaguesList: LeaguesList?) {
        val stringArray = leaguesList!!.leagues.map {
            it.strLeague
        }

        val idArray = leaguesList.leagues.map {
            it.idLeague
        }
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, stringArray)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getNextMatchByID(idArray[position])
            }

        }
    }

    override fun onClick(item: EventModel) {
        context?.startActivity<DetailMatch>("data" to item)
    }

    override fun onFailed(message: String?) {
        swipeLayout.isRefreshing = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(eventsList: List<EventModel>?) {
        swipeLayout.isRefreshing = false
        listItem.clear()
        eventsList?.let { listItem.addAll(it) }
        adapterList.notifyDataSetChanged()
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
        if (eventBusHelper.type == 1) {
            adapterList.filter.filter(eventBusHelper.message)
        }
    }

    companion object {
        fun newInsance(): NextMatch {
            return NextMatch()
        }
    }

    class NextMatchUI : AnkoComponent<NextMatch> {

        companion object {
            const val swipeRefreshId = 1
            const val recyclerViewId = 12
            const val spinerID = 15
        }

        override fun createView(ui: AnkoContext<NextMatch>) = with(ui) {
            verticalLayout {
                swipeRefreshLayout {
                    id = swipeRefreshId

                    verticalLayout {
                        spinner {
                            id = spinerID
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

}
