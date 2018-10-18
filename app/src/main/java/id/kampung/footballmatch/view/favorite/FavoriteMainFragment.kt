package id.kampung.footballmatch.view.favorite


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kampung.footballmatch.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager
import id.kampung.footballmatch.adapter.TabFavoriteAdapter

class FavoriteMainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = FavoriteMainUI().createView(AnkoContext.create(requireContext(), this))
        val fragmentAdapter = TabFavoriteAdapter(fm = childFragmentManager)

        val viewPager: ViewPager

        viewPager = view.findViewById(FavoriteMainUI.viewPagerID)
        viewPager.adapter = fragmentAdapter

        with(view.findViewById<TabLayout>(FavoriteMainUI.tabID)) {
            setupWithViewPager(viewPager)
        }

        return view
    }

    companion object {
        fun newInsance(): FavoriteMainFragment {
            return FavoriteMainFragment()
        }
    }

    @Suppress("DEPRECATION")
    class FavoriteMainUI : AnkoComponent<FavoriteMainFragment> {
        companion object {
            const val tabID = 111
            const val viewPagerID = 51
        }

        override fun createView(ui: AnkoContext<FavoriteMainFragment>) = with(ui) {
            verticalLayout {
                tabLayout {
                    id = tabID
                    background = ContextCompat.getDrawable(context, R.color.colorWhite)
                }.lparams(matchParent, wrapContent)
                viewPager {
                    id = viewPagerID
                }.lparams(matchParent, matchParent) { bottomMargin = dip(8) }

            }
        }

    }

}
