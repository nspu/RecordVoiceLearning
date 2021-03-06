/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.nspu.dev.recordvoicelearning.view.activity


import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.lifecycle.Observer
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import fr.nspu.dev.recordvoicelearning.AppExecutors
import fr.nspu.dev.recordvoicelearning.EspressoTestUtil
import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.RecyclerViewItemCountAssertion
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import com.squareup.spoon.SpoonRule




@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val NAME = "English - French"
    private val TYPE_QUESTION = "English"
    private val TYPE_ANSWER = "French"

    private val NAME2 = "Important event"
    private val TYPE_QUESTION2 = "Date"
    private val TYPE_ANSWER2 = "Event"

    @get:Rule
    val activityRule = ActivityTestRule(
            MainActivity::class.java)

    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    val spoon = SpoonRule()

    @get:Rule
    val runtimePermissionRule : GrantPermissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


    private val mExecutor = AppExecutors()

    init {
        // delete the database
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME)
    }

    @Before
    fun disableRecyclerViewAnimations() {
        // Disable RecyclerView animations
        EspressoTestUtil.disableAnimations(activityRule)
    }

    @Before
    @Throws(Throwable::class)
    fun waitForDbCreation() {
        val latch = CountDownLatch(1)
        val databaseCreated = AppDatabase.getInstance(
                InstrumentationRegistry.getTargetContext(), mExecutor)!!
                .databaseCreated
        activityRule.runOnUiThread {
            databaseCreated.observeForever(object : Observer<Boolean> {
                override fun onChanged(aBoolean: Boolean?) {
                    if (java.lang.Boolean.TRUE == aBoolean) {
                        databaseCreated.removeObserver(this)
                        latch.countDown()
                    }
                }
            })
        }
        MatcherAssert.assertThat("database should've initialized",
                latch.await(1, TimeUnit.MINUTES), CoreMatchers.`is`(true))
    }

    @Before
    fun unlockScreen() {
        val activity = activityRule.activity
        val wakeUpDevice = Runnable {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
    }


    @Test
    @Throws(Throwable::class)
    fun AllTest() {
        test1AddFolders()
        spoon.screenshot(activityRule.activity, "add_folder");
        test3CheckFirstElementOnFolders()
        spoon.screenshot(activityRule.activity, "First_element");
        test4clickOnFirstItemForOpenFolder()
        spoon.screenshot(activityRule.activity, "add_Open_folder");
        test5CheckSeventhElementOnFolders()
        spoon.screenshot(activityRule.activity, "add_seventh_element");
        test6ClickOnSeventhItemForOpenFolder()
        spoon.screenshot(activityRule.activity, "click");
        test7DeleteAllElements()
        spoon.screenshot(activityRule.activity, "deleted");
    }

    //    @Test
    @Throws(Throwable::class)
    private fun test1AddFolders() {
        for (i in 0..1) {
            addFolder(NAME, TYPE_QUESTION, TYPE_ANSWER)
            addFolder(NAME2, TYPE_QUESTION2, TYPE_ANSWER2)
        }
    }


    @Throws(Throwable::class)
    private fun addFolder(name: String, typeQuestion: String, typeAnswer: String) {
        drain()
        onView(withId(R.id.fab_add_folder)).perform(click())
        drain()
        onView(withId(R.id.add_name_et)).perform(typeText(name))
        drain()
        onView(withId(R.id.add_type_question_et)).perform(typeText(typeQuestion))
        drain()
        onView(withId(R.id.add_type_answer_et)).perform(typeText(typeAnswer))
        drain()
        onView(withId(R.id.action_validate_add_folder)).perform(click())
        drain()
    }


    //    @Test
    @Throws(Throwable::class)
    private fun test3CheckFirstElementOnFolders() {
        Thread.sleep(1000);
        onView(withId(R.id.folders_list))
                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(NAME), R.id.name)))

                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(TYPE_QUESTION), R.id.type_question)))

                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(TYPE_ANSWER), R.id.type_answer)))
    }

    //    @Test
    @Throws(Throwable::class)
    private fun test4clickOnFirstItemForOpenFolder() {

        drain()
        // When clicking on the first product
        onView(ViewMatchers.withId(R.id.folders_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        drain()
        // Then the second screen with the comments should appear.
        onView(withId(R.id.name)).check(matches(withText(NAME)))
        drain()
        onView(withId(R.id.type_question)).check(matches(withText(TYPE_QUESTION)))
        drain()
        onView(withId(R.id.type_answer)).check(matches(withText(TYPE_ANSWER)))
        drain()
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    //    @Test
    @Throws(Throwable::class)
    private fun test5CheckSeventhElementOnFolders() {
        drain()

        onView(withId(R.id.folders_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(NAME2), R.id.name)))

                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(TYPE_QUESTION2), R.id.type_question)))

                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(TYPE_ANSWER2), R.id.type_answer)))
    }

    //    @Test
    @Throws(Throwable::class)
    private fun test6ClickOnSeventhItemForOpenFolder() {

        drain()
        // When clicking on the first product
        onView(ViewMatchers.withId(R.id.folders_list))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        drain()
        // Then the second screen with the comments should appear.
        onView(withId(R.id.name)).check(matches(withText(NAME2)))
        drain()
        onView(withId(R.id.type_question)).check(matches(withText(TYPE_QUESTION2)))
        drain()
        onView(withId(R.id.type_answer)).check(matches(withText(TYPE_ANSWER2)))
        drain()
        onView(isRoot()).perform(ViewActions.pressBack())


    }

    //    @Test
    @Throws(Throwable::class)
    private fun test7DeleteAllElements() {

        for (i in 3 downTo -1 + 1) {

            drain()

            onView(ViewMatchers.withId(R.id.folders_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, longClick()))

            drain()

            onView(ViewMatchers.withId(R.id.folders_list))
                    .check(RecyclerViewItemCountAssertion(i))
        }
    }

    @Throws(TimeoutException::class, InterruptedException::class)
    private fun drain() {
        countingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES)
    }
}