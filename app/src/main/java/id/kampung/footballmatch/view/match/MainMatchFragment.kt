package id.kampung.footballmatch.view.match


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager
import id.kampung.footballmatch.adapter.TabAdapter
import id.kampung.footballmatch.R

class MainMatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = MainMatchUI().createView(AnkoContext.create(requireContext(), this))
        val fragmentAdapter = TabAdapter(fm = childFragmentManager)

        val viewPager: ViewPager

        viewPager = view.findViewById(MainMatchFragment.MainMatchUI.viewPagerID)
        viewPager.adapter = fragmentAdapter

        with(view.findViewById<TabLayout>(MainMatchUI.tabID)) {
            setupWithViewPager(viewPager)
        }

        return view
    }


    companion object {
        fun newInsance(): MainMatchFragment {
            return MainMatchFragment()
        }
    }

    @Suppress("DEPRECATION")
    class MainMatchUI : AnkoComponent<MainMatchFragment> {
        companion object {
            const val tabID = 1
            const val viewPagerID = 5
        }

        override fun createView(ui: AnkoContext<MainMatchFragment>) = with(ui) {
            verticalLayout {
                tabLayout {
                    id = MainMatchUI.tabID
                    background = ContextCompat.getDrawable(context, R.color.colorWhite)
                }.lparams(matchParent, wrapContent)
                viewPager {
                    id = MainMatchUI.viewPagerID
                }.lparams(matchParent, matchParent) { bottomMargin = dip(8) }

            }
        }

    }
}
