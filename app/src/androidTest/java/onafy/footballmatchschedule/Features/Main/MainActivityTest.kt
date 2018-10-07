package onafy.footballmatchschedule.Features.Main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Test

import onafy.footballmatchschedule.R.id.*

import onafy.footballmatchschedule.Features.Main.MainActivity
import onafy.footballmatchschedule.Features.Main.MainActivity.Companion.idListEvent
import onafy.footballmatchschedule.Features.Main.MainActivity.Companion.idSpinner
import onafy.footballmatchschedule.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var mainActivityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testAll() {
        //to check that the view is ready enough
        delay()
        //test the recyclerview, scroll until position 12
        onView(withId(idListEvent))
                .check(matches(isDisplayed()))
        onView(withId(idListEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        delay()



        //test Spinner : Past Event
        onView(withId(idSpinner))
                .check(matches(isDisplayed()))
        onView(withId(idSpinner)).perform(click())
        onView(withText("Past Event")).perform(click())
        //Click one item in recyclerview
        onView(withText("Bournemouth"))
                .check(matches(isDisplayed()))
        onView(withText("Bournemouth")).perform(click())
        //checking if the badge is displayed or not
        onView(withId(homeImage)).check(matches(isDisplayed()))
        onView(withId(awayImage)).check(matches(isDisplayed()))
        delay()
        //click add to favorite
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()



        //test Spinner : Favorite
        onView(withId(idSpinner))
                .check(matches(isDisplayed()))
        onView(withId(idSpinner)).perform(click())
        onView(withText("Favorites")).perform(click())
        onView(withId(idListEvent))
                .check(matches(isDisplayed()))
        onView(withId(idListEvent)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delay()
        //check the swiperefresh
        onView(withId(swipeRefresh)).perform(swipeDown())

    }

    private fun delay(){
        try {
            Thread.sleep(2500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}