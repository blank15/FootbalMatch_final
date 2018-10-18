package id.kampung.footballmatch.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.kampung.footballmatch.view.favorite.FavoriteFragment
import id.kampung.footballmatch.view.match.LastMatch
import id.kampung.footballmatch.view.match.NextMatch

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                LastMatch.newInsance()
            }
            else -> {
                NextMatch.newInsance()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Last Match"
            else -> {
                return "Next Match"
            }
        }
    }
}