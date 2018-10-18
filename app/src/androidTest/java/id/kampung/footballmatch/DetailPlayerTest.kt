package id.kampung.footballmatch

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.kampung.footballmatch.R.id.add_to_favorite
import id.kampung.footballmatch.view.match.MainMatchFragment
import id.kampung.footballmatch.view.favorite.FavoriteFragment
import id.kampung.footballmatch.view.match.LastMatch
import id.kampung.footballmatch.view.match.NextMatch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailPlayerTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testAppBehaviour(){
        Espresso.onView(ViewMatchers.withId(LastMatch.LastMatchUI.recyclerViewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(2500)
        onView(withText("Arsenal"))
                .check(matches(isDisplayed()))
        onView(withText("Arsenal")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        pressBack()
        Thread.sleep(1500)
        onView(withText("Arsenal"))
                .check(matches(isDisplayed()))
        onView(withText("Arsenal")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        pressBack()

        onView(withId(MainMatchFragment.MainMatchUI.viewPagerID))
                .check(matches(isDisplayed()))

        onView(withId(MainMatchFragment.MainMatchUI.viewPagerID))
                .perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withId(NextMatch.NextMatchUI.recyclerViewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(2500)

        onView(withId(NextMatch.NextMatchUI.recyclerViewId)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        onView(withId(MainMatchFragment.MainMatchUI.viewPagerID))
                .check(matches(isDisplayed()))

        onView(withId(MainMatchFragment.MainMatchUI.viewPagerID))
                .perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withId(FavoriteFragment.FavoriteFragmentUI.recyclerViewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}