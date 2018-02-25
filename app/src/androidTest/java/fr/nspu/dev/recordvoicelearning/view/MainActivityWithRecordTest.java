package fr.nspu.dev.recordvoicelearning.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.runner.RunWith;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityWithRecordTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @org.junit.jupiter.api.Test
    public void mainActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_folder),
                        childAtPosition(
                                allOf(withId(R.id.cl_list_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.add_name_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("J"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.add_type_question_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("U"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.add_type_answer_et),
                        childAtPosition(
                                allOf(withId(R.id.cl_add_folder),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("U"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_validate_add_folder), withContentDescription("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.folders_list), withContentDescription("Folders list")),
                                0),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.name), withText("J"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("J")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.type_question), withText("U"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("U")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.type_answer), withText("U"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("U")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.folders_list), withContentDescription("Folders list"),
                        childAtPosition(
                                withId(R.id.cl_list_folder),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.name), withText("J"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.include_item_folder),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("J")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.type_question), withText("U"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.include_item_folder),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("U")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.type_answer), withText("U"), withContentDescription("Name of the folder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.include_item_folder),
                                        0),
                                2),
                        isDisplayed()));
        textView6.check(matches(withText("U")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.tv_count_peers), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("0")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.tv_count_peers), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("0")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_add_peer), withContentDescription("Ajouter folder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.record_question_btn), withText("Enregistrer question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.record_answer_btn), withText("Enregistrer réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.record_another_btn), withText("Enregistrer et créer une autre paire"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        2),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.record_question_btn), withText("Enregistrer question"),
                        childAtPosition(
                                allOf(withId(R.id.ll_record),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.record_answer_btn), withText("Enregistrer réponse"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.stop_btn), withText("Stop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.exit_btn), withText("Enregistrer et quitter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_play_peers), withText("Play"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatButton11.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btn_listen_question), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                3),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btn_listen_answer), withText("écouter réponse"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                4),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btn_good), withText("Bon"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                6),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_listen_question), withText("ecouter question"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                3),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.btn_listen_answer), withText("écouter réponse"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                4),
                        isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btn_wrong), withText("Faux"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.container))),
                                5),
                        isDisplayed()));
        appCompatButton17.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.name_tv), withText("Pair : 2"), withContentDescription("peer"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("Pair : 2")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.name_tv), withText("Pair : 1"), withContentDescription("peer"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("Pair : 1")));

        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.peers_list), withContentDescription("Peers list")),
                                1),
                        0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction viewGroup2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.peers_list), withContentDescription("Peers list")),
                                0),
                        0),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));

        ViewInteraction viewGroup3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.peers_list), withContentDescription("Peers list")),
                                1),
                        0),
                        isDisplayed()));
        viewGroup3.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.name_tv), withText("Pair : 1"), withContentDescription("peer"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("Pair : 1")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.name_tv), withText("Pair : 1"), withContentDescription("peer"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("Pair : 1")));

        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.btn_order), withText("ASC"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                2),
                        isDisplayed()));
        toggleButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Revenir en haut de la page"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.folders_list), withContentDescription("Folders list"),
                        childAtPosition(
                                withId(R.id.cl_list_folder),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, longClick()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
