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
import id.kampung.footballmatch.adapter.AdapterMatch
import id.kampung.footballmatch.data.model.EventModel
import id.kampung.footballmatch.view.detailMatch.DetailMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.kampung.footballmatch.R
import id.kampung.footballmatch.data.model.TeamModel

class FavoriteFragment : Fragment(), FavoriteContract, AdapterMatch.OnClickListener {


    private var listItem: MutableList<EventModel> = mutableListOf()
    private lateinit var adapterListLast: AdapterMatch
    private lateinit var presenter: FavoritePresenter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var info: TextView

    companion object {
        fun newInsance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = FavoriteFragmentUI().createView(AnkoContext.create(requireContext(), this))


        adapterListLast = AdapterMatch(listItem, requireContext(), this, false)
        with(view.findViewById<RecyclerView>(FavoriteFragmentUI.recyclerViewId)) {
            adapter = adapterListLast
        }
        swipeLayout = view.findViewById(FavoriteFragmentUI.swipeRefreshId)
        info = view.findViewById(FavoriteFragmentUI.infoId)
        swipeLayout.setOnRefreshListener { presenter.getFavoriteData(requireContext()) }

        presenter = FavoritePresenter(this)
        presenter.getFavoriteData(requireContext())
        return view
    }

    override fun onClick(item: EventModel) {
        context?.startActivity<DetailMatch>("data" to item)
    }

    override fun onFailed(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(eventsList: List<EventModel>) {
        swipeLayout.isRefreshing = false
        if (eventsList.isNotEmpty()) {
            info.visibility = View.GONE
        } else
            info.visibility = View.VISIBLE

        listItem.clear()
        eventsList.let { listItem.addAll(it) }
        adapterListLast.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteData(requireContext())
    }

    override fun onSuccessTeam(teamList: List<TeamModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class FavoriteFragmentUI : AnkoComponent<FavoriteFragment> {
        companion object {
            const val swipeRefreshId = 1
            const val recyclerViewId = 22
            const val infoId = 3
        }

        override fun createView(ui: AnkoContext<FavoriteFragment>) = with(ui) {
            verticalLayout {
                swipeRefreshLayout {
                    id = FavoriteFragmentUI.swipeRefreshId

                    verticalLayout {
                        recyclerView {
                            id = FavoriteFragmentUI.recyclerViewId
                            layoutManager = LinearLayoutManager(context)

                        }
                        textView {
                            id = FavoriteFragmentUI.infoId
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
