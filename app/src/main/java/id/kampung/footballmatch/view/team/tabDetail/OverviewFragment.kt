package id.kampung.footballmatch.view.team.tabDetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.kampung.footballmatch.data.model.TeamModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView


class OverviewFragment : Fragment() {

    private lateinit var textViewDesc: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = OverviewFragmentUI().createView(AnkoContext.create(requireContext(), this))
        val data = arguments?.get("data") as TeamModel
        textViewDesc = view.findViewById(OverviewFragmentUI.textID)
        textViewDesc.text = data.strDescriptionEN
        return view
    }


    companion object {

        fun newInsance(teamModel: TeamModel) =
                OverviewFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("data", teamModel)
                    }
                }
    }

    class OverviewFragmentUI : AnkoComponent<OverviewFragment> {
        companion object {
            const val textID = 155
        }

        override fun createView(ui: AnkoContext<OverviewFragment>) = with(ui) {
            nestedScrollView {
                verticalLayout {
                    padding = dip(16)

                    textView {
                        id = textID
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams(matchParent, matchParent)
            }
        }

    }

}
