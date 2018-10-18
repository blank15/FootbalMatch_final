package id.kampung.footballmatch.view.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.kampung.footballmatch.R
import id.kampung.footballmatch.adapter.AdapterTeam
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.data.model.TeamModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class FavoriteTeamFragment : Fragment(), FavoriteContract {


    private var listItem: MutableList<TeamModel> = mutableListOf()
    private lateinit var adapterList: AdapterTeam
    private lateinit var presenter: FavoritePresenter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var info: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = FavoriteTeamFragmentUI().createView(AnkoContext.create(requireContext(), this))


        adapterList = AdapterTeam(requireContext(), listItem)
        with(view.findViewById<RecyclerView>(FavoriteTeamFragmentUI.recyclerViewId)) {
            adapter = adapterList
        }
        swipeLayout = view.findViewById(FavoriteTeamFragmentUI.swipeRefreshId)
        info = view.findViewById(FavoriteTeamFragmentUI.infoId)
        swipeLayout.setOnRefreshListener { presenter.getFavoriteTeamData(requireContext()) }

        presenter = FavoritePresenter(this)
        presenter.getFavoriteTeamData(requireContext())
        return view
    }


    override fun onResume() {
        super.onResume()
        presenter.getFavoriteTeamData(requireContext())
    }

    override fun onFailed(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(eventsList: List<EventModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessTeam(teamList: List<TeamModel>) {
        swipeLayout.isRefreshing = false
        if (teamList.isNotEmpty()) {
            info.visibility = View.GONE
        } else
            info.visibility = View.VISIBLE

        listItem.clear()
        teamList.let { listItem.addAll(it) }
        adapterList.notifyDataSetChanged()
    }

    companion object {
        fun newInsance(): FavoriteTeamFragment {
            return FavoriteTeamFragment()
        }
    }

    class FavoriteTeamFragmentUI : AnkoComponent<FavoriteTeamFragment> {
        companion object {
            const val swipeRefreshId = 121
            const val recyclerViewId = 222
            const val infoId = 31
        }

        override fun createView(ui: AnkoContext<FavoriteTeamFragment>) = with(ui) {
            verticalLayout {
                swipeRefreshLayout {
                    id = swipeRefreshId

                    verticalLayout {
                        recyclerView {
                            id = recyclerViewId
                            layoutManager = LinearLayoutManager(context)

                        }
                        textView {
                            id = infoId
                            text = context.getString(R.string.info)
                            textSize = 18f
                            gravity = Gravity.CENTER
                            visibility = View.GONE
                        }.lparams(matchParent, wrapContent) { topMargin = dip(24) }
                    }
                }
            }
        }
    }
}
