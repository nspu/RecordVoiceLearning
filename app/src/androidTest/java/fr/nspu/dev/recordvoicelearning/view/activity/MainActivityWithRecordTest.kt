package fr.nspu.dev.recordvoicelearning.view.activity


import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import fr.nspu.dev.recordvoicelearning.R

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`

@RunWith(AndroidJUnit4::class)
class MainActivityWithRecordTest {
    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun mainActivityRecordTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val floatingActionButton = onView(
                allOf(withId(R.id.fab_add_folder),
                        childAtPosition(
                                allOf(withId(R.id.cl_list_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                2),
                        isDisplayed()))
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.add_name_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                0),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("E"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.add_type_question_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("T"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.add_type_answer_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText("S"), closeSoftKeyboard())

        val actionMenuItemView = onView(
                allOf(withId(R.id.action_validate_add_folder), withContentDescription("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val textView = onView(
                allOf(withId(R.id.name), withText("E"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("E")))

        val textView2 = onView(
                allOf(withId(R.id.type_question), withText("T"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("T")))

        val textView3 = onView(
                allOf(withId(R.id.type_answer), withText("S"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0),
                                2),
                        isDisplayed()))
        textView3.check(matches(withText("S")))

        val recyclerView = onView(
                allOf(withId(R.id.folders_list), withContentDescription("Folders list"),
                        childAtPosition(
                                withId(R.id.cl_list_folder),
                                1)))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val actionMenuItemView2 = onView(
                allOf(withId(R.id.action_add_peer), withContentDescription("Ajouter folder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val appCompatButton = onView(
                allOf(withId(R.id.record_question_btn), withText("Enregistrer question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()))
        appCompatButton.perform(click())

        val appCompatButton2 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton2.perform(click())

        val appCompatButton3 = onView(
                allOf(withId(R.id.record_answer_btn), withText("Enregistrer réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        1),
                                0),
                        isDisplayed()))
        appCompatButton3.perform(click())

        val appCompatButton4 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton4.perform(click())

        val appCompatButton5 = onView(
                allOf(withId(R.id.listen_question_btn), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatButton5.perform(click())

        val appCompatButton6 = onView(
                allOf(withId(R.id.listen_answer_btn), withText("écouter réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        1),
                                1),
                        isDisplayed()))
        appCompatButton6.perform(click())

        val appCompatButton7 = onView(
                allOf(withId(R.id.record_another_btn), withText("Enregistrer et créer une autre paire"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        2),
                                2),
                        isDisplayed()))
        appCompatButton7.perform(click())

        val appCompatButton8 = onView(
                allOf(withId(R.id.record_question_btn), withText("Enregistrer question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()))
        appCompatButton8.perform(click())

        val appCompatButton9 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton9.perform(click())

        val appCompatButton10 = onView(
                allOf(withId(R.id.listen_question_btn), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatButton10.perform(click())

        val appCompatButton11 = onView(
                allOf(withId(R.id.record_answer_btn), withText("Enregistrer réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        1),
                                0),
                        isDisplayed()))
        appCompatButton11.perform(click())

        val appCompatButton12 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton12.perform(click())

        val appCompatButton13 = onView(
                allOf(withId(R.id.listen_answer_btn), withText("écouter réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        1),
                                1),
                        isDisplayed()))
        appCompatButton13.perform(click())

        val appCompatButton14 = onView(
                allOf(withId(R.id.exit_btn), withText("Enregistrer et quitter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()))
        appCompatButton14.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val appCompatButton15 = onView(
                allOf(withId(R.id.btn_play_peers), withText("Play"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()))
        appCompatButton15.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val appCompatButton16 = onView(
                allOf(withId(R.id.btn_listen_question), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                3),
                        isDisplayed()))
        appCompatButton16.perform(click())

        val appCompatButton17 = onView(
                allOf(withId(R.id.btn_listen_answer), withText("écouter réponse"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                4),
                        isDisplayed()))
        appCompatButton17.perform(click())

        val appCompatButton18 = onView(
                allOf(withId(R.id.btn_good), withText("Bon"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                6),
                        isDisplayed()))
        appCompatButton18.perform(click())

        val appCompatButton19 = onView(
                allOf(withId(R.id.btn_listen_question), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                3),
                        isDisplayed()))
        appCompatButton19.perform(click())

        val appCompatButton20 = onView(
                allOf(withId(R.id.btn_listen_answer), withText("écouter réponse"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                4),
                        isDisplayed()))
        appCompatButton20.perform(click())

        val appCompatButton21 = onView(
                allOf(withId(R.id.btn_wrong), withText("Faux"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                5),
                        isDisplayed()))
        appCompatButton21.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        val toggleButton = onView(
                allOf(withId(R.id.btn_order), withText("ASC"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        2),
                                2),
                        isDisplayed()))
        toggleButton.perform(click())

        val textView6 = onView(
                allOf(withId(R.id.knowledge_tv), withText("1"), withContentDescription("Knowledge"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView6.check(matches(withText("1")))

        val textView7 = onView(
                allOf(withId(R.id.knowledge_tv), withText("0"), withContentDescription("Knowledge"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView7.check(matches(withText("0")))

        val toggleButton2 = onView(
                allOf(withId(R.id.btn_question_to_answer), withText("Q -> A"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()))
        toggleButton2.perform(click())

        pressBack()

        val recyclerView2 = onView(
                allOf(withId(R.id.folders_list), withContentDescription("Folders list"),
                        childAtPosition(
                                withId(R.id.cl_list_folder),
                                1)))
        recyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, longClick()))


    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}
