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
import android.R


class LastMatch : Fragment(), MatchContract, AdapterMatch.OnClickListener {


    private var listItem: MutableList<EventModel> = mutableListOf()
    private lateinit var adapterListLast: AdapterMatch
    private lateinit var presenter: MatchPresenter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = LastMatchUI().createView(AnkoContext.create(requireContext(), this))


        adapterListLast = AdapterMatch(listItem, requireContext(), this, false)
        with(view.findViewById<RecyclerView>(LastMatchUI.recyclerViewId)) {
            adapter = adapterListLast
        }

        presenter = MatchPresenter(this, RemoteRepository())
        presenter.getLastMatch()

        with(view.findViewById<SwipeRefreshLayout>(LastMatchUI.swipeRefreshId)) {
            swipeLayout = this
            setOnRefreshListener { presenter.getLastMatch() }
        }

        spinner = view.findViewById(LastMatchUI.spinerIDLast)
        presenter.getLeagues()

        return view
    }

    override fun onClick(item: EventModel) {
        context?.startActivity<DetailMatch>("data" to item)
    }

    override fun onLeaguesGet(leaguesList: LeaguesList?) {

        val stringArray = leaguesList!!.leagues.map {
            it.strLeague
        }

        val idArray = leaguesList!!.leagues.map {
            it.idLeague
        }

        spinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, stringArray)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getLastMatchByID(idArray[position])
            }

        }
    }

    override fun onFailed(message: String?) {
        swipeLayout.isRefreshing = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(eventsList: List<EventModel>?) {
        swipeLayout.isRefreshing = false
        listItem.clear()
        eventsList?.let { listItem.addAll(it) }
        adapterListLast.notifyDataSetChanged()
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
            adapterListLast.filter.filter(eventBusHelper.message)
        }
    }

    companion object {
        fun newInsance(): LastMatch {
            return LastMatch()
        }
    }

    class LastMatchUI : AnkoComponent<LastMatch> {
        companion object {
            const val swipeRefreshId = 1
            const val recyclerViewId = 3
            const val spinerIDLast = 25
        }

        override fun createView(ui: AnkoContext<LastMatch>) = with(ui) {
            verticalLayout {
                swipeRefreshLayout {
                    id = swipeRefreshId

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
}
