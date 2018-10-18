package id.kampung.footballmatch.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.view.team.tabDetail.OverviewFragment
import id.kampung.footballmatch.view.team.tabDetail.PlayerFragment

class TabTeamAdapter(fm: FragmentManager, private val teamModel: TeamModel) : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                OverviewFragment.newInsance(teamModel)
            }
            else -> {
                PlayerFragment.newInsance(teamModel)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Overview"
            else -> {
                return "Player"
            }
        }
    }
}