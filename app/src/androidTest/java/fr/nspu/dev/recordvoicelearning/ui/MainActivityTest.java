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

package fr.nspu.dev.recordvoicelearning.ui;


import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.nspu.dev.recordvoicelearning.AppExecutors;
import fr.nspu.dev.recordvoicelearning.EspressoTestUtil;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.RecyclerViewItemCountAssertion;
import fr.nspu.dev.recordvoicelearning.controller.AppDatabase;
import fr.nspu.dev.recordvoicelearning.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTest {

    private final String NAME = "English - French";
    private final String TYPE_QUESTION = "English";
    private final String TYPE_ANSWER = "French";

    private final String NAME2 = "Important event";
    private final String TYPE_QUESTION2 = "Date";
    private final String TYPE_ANSWER2 = "Event";

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Rule
    public final CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();

    private final AppExecutors mExecutor = new AppExecutors();

    public MainActivityTest() {
        // delete the database
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);

    }

    @Before
    public void disableRecyclerViewAnimations() {
        // Disable RecyclerView animations
        EspressoTestUtil.disableAnimations(mActivityRule);
    }

    @Before
    public void waitForDbCreation() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final LiveData<Boolean> databaseCreated = AppDatabase.getInstance(
                InstrumentationRegistry.getTargetContext(), mExecutor)
                .getDatabaseCreated();
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                databaseCreated.observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (Boolean.TRUE.equals(aBoolean)) {
                            databaseCreated.removeObserver(this);
                            latch.countDown();
                        }
                    }
                });
            }
        });
        MatcherAssert.assertThat("database should've initialized",
                latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));
    }



    @Test
    public void AllTest() throws Throwable{
        test1AddFolders();
        test3CheckFirstElementOnFolders();
        test4clickOnFirstItemForOpenFolder();
        test5CheckSeventhElementOnFolders();
        test6ClickOnSeventhItemForOpenFolder();
        test7DeleteAllElements();
    }

//    @Test
private void test1AddFolders() throws Throwable{
        for(int i = 0; i < 2 ; i++){
            addFolder(NAME, TYPE_QUESTION, TYPE_ANSWER);
            addFolder(NAME2, TYPE_QUESTION2, TYPE_ANSWER2);
        }
    }


    private void addFolder(String name, String typeQuestion, String typeAnswer) throws Throwable{
        drain();
        onView(withId(R.id.action_add_folder)).perform(click());
        drain();
        onView(withId(R.id.add_name_et)).perform(typeText(name));
        drain();
        onView(withId(R.id.add_type_question_et)).perform(typeText(typeQuestion));
        drain();
        onView(withId(R.id.add_type_answer_et)).perform(typeText(typeAnswer));
        drain();
        onView(withId(R.id.action_validate_add_folder)).perform(click());
        drain();
    }


//    @Test
private void test3CheckFirstElementOnFolders() throws Throwable{
        drain();

        onView(withContentDescription(R.string.cd_folders_list))
                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(NAME), R.id.name)))

                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(TYPE_QUESTION), R.id.type_question)))

                .check(matches(EspressoTestUtil.atPositionOnView(0, withText(TYPE_ANSWER), R.id.type_answer)));
    }

//    @Test
private void test4clickOnFirstItemForOpenFolder() throws Throwable {

        drain();
        // When clicking on the first product
        onView(ViewMatchers.withContentDescription(R.string.cd_folders_list))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        drain();
        // Then the second screen with the comments should appear.
        onView(withId(R.id.name)).check(matches(withText(NAME)));
        onView(withId(R.id.type_question)).check(matches(withText(TYPE_QUESTION)));
        onView(withId(R.id.type_answer)).check(matches(withText(TYPE_ANSWER)));

        onView(isRoot()).perform(ViewActions.pressBack());
    }

//    @Test
private void test5CheckSeventhElementOnFolders() throws Throwable{
        drain();

        onView(withContentDescription(R.string.cd_folders_list))
                .perform(RecyclerViewActions.scrollToPosition(3))
                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(NAME2), R.id.name)))

                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(TYPE_QUESTION2), R.id.type_question)))

                .check(matches(EspressoTestUtil.atPositionOnView(3, withText(TYPE_ANSWER2), R.id.type_answer)));
    }

//    @Test
private void test6ClickOnSeventhItemForOpenFolder() throws Throwable {

        drain();
        // When clicking on the first product
        onView(ViewMatchers.withContentDescription(R.string.cd_folders_list))
                .perform(RecyclerViewActions.scrollToPosition(3))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        drain();
        // Then the second screen with the comments should appear.
        onView(withId(R.id.name)).check(matches(withText(NAME2)));
        onView(withId(R.id.type_question)).check(matches(withText(TYPE_QUESTION2)));
        onView(withId(R.id.type_answer)).check(matches(withText(TYPE_ANSWER2)));

        onView(isRoot()).perform(ViewActions.pressBack());



    }

//    @Test
private void test7DeleteAllElements() throws Throwable{

        for(int i = 3; i>-1; i--){

            drain();

            onView(ViewMatchers.withContentDescription(R.string.cd_folders_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, longClick()));

            drain();

            onView(ViewMatchers.withContentDescription(R.string.cd_folders_list))
                    .check(new RecyclerViewItemCountAssertion(i));
        }
    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}