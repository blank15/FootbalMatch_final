package id.kampung.footballmatch

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import id.kampung.footballmatch.helper.EventBusHelper
import id.kampung.footballmatch.view.match.MainMatchFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import id.kampung.footballmatch.view.team.TeamMainFragment
import  id.kampung.footballmatch.view.favorite.FavoriteMainFragment
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragment: Fragment

    private val matchmenuID = 100
    private val teammenuID = 101
    private val favoritemenuID = 102
    private var selected: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
        init()
    }

    private fun init() {
        supportFragmentManager.beginTransaction()
                .add(MainActivityUI.contentFrame, MainMatchFragment.newInsance())
                .commit()
        with(findViewById<BottomNavigationView>(MainActivityUI.navigationView)) {
            bottomNavigationView = this
            menu.add(Menu.NONE, matchmenuID, Menu.NONE, R.string.match).setIcon(R.drawable.ic_schedule_black_24dp)
            menu.add(Menu.NONE, teammenuID, Menu.NONE, R.string.team).setIcon(R.drawable.ic_assignment_ind_black_24dp)
            menu.add(Menu.NONE, favoritemenuID, Menu.NONE, R.string.favorites).setIcon(R.drawable.ic_stars_black_24dp)

            setOnNavigationItemSelectedListener {

                when (it.itemId) {
                    matchmenuID -> {
                        fragment = MainMatchFragment.newInsance()
                        selected = 1

                        searchView.visibility = View.VISIBLE

                    }
                    teammenuID -> {
                        fragment = TeamMainFragment.newInsance()
                        selected = 2

                        searchView.visibility = View.VISIBLE

                    }
                    favoritemenuID -> {
                        fragment =FavoriteMainFragment.newInsance()
                        selected = 3
                        searchView.visibility = View.GONE

                    }
                    else -> {
                        fragment = MainMatchFragment.newInsance()
                        selected = 1
                    }
                }

                setFragment(fragment)
                return@setOnNavigationItemSelectedListener true
            }

        }


    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(MainActivityUI.contentFrame, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        setSearch()
        return true
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    private fun setSearch() {
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                EventBus.getDefault().post(EventBusHelper(p0, selected))
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                EventBus.getDefault().post(EventBusHelper(p0, selected))
                return true
            }
        })
    }

    @Suppress("DEPRECATION")
    class MainActivityUI : AnkoComponent<MainActivity> {
        companion object {
            const val contentFrame = 1
            const val navigationView = 52

        }

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            verticalLayout {

                frameLayout {
                    id = contentFrame
                }.lparams(matchParent, height = 0, weight = 1F)
                bottomNavigationView {
                    id = navigationView
                    backgroundColorResource = R.color.colorPrimary

                    val states = arrayOf(
                            intArrayOf(android.R.attr.state_selected),
                            intArrayOf(android.R.attr.state_enabled)
                    )

                    val colors = intArrayOf(Color.WHITE, Color.DKGRAY)

                    val colorStateList = ColorStateList(states, colors)
                    itemTextColor = colorStateList
                    itemIconTintList = colorStateList

                }.lparams(width = LinearLayout.LayoutParams.MATCH_PARENT)

            }
        }

    }
}
