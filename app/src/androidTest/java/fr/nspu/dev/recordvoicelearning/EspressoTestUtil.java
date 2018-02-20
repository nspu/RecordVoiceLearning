package fr.nspu.dev.recordvoicelearning;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Utility methods for espresso tests.
 */
public class EspressoTestUtil {
    /**
     * Disables progress bar animations for the views of the given activity rule
     *
     * @param activityTestRule The activity rule whose views will be checked
     */
    public static void disableAnimations(
            ActivityTestRule<? extends FragmentActivity> activityTestRule) {
        activityTestRule.getActivity().getSupportFragmentManager()
                .registerFragmentLifecycleCallbacks(
                        new FragmentManager.FragmentLifecycleCallbacks() {
                            @Override
                            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v,
                                                              Bundle savedInstanceState) {
                                // traverse all views, if any is a progress bar, replace its animation
                                traverseViews(v);
                            }
                        }, true);
    }

    private static void traverseViews(View view) {
        if (view instanceof ViewGroup) {
            traverseViewGroup((ViewGroup) view);
        } else {
            if (view instanceof ProgressBar) {
                disableProgressBarAnimation((ProgressBar) view);
            }
        }
    }

    private static void traverseViewGroup(ViewGroup view) {
        if (view instanceof RecyclerView) {
            disableRecyclerViewAnimations((RecyclerView) view);
        } else {
            final int count = view.getChildCount();
            for (int i = 0; i < count; i++) {
                traverseViews(view.getChildAt(i));
            }
        }
    }

    private static void disableRecyclerViewAnimations(RecyclerView view) {
        view.setItemAnimator(null);
    }

    /**
     * necessary to run tests on older API levels where progress bar uses handler loop to animate.
     *
     * @param progressBar The progress bar whose animation will be swapped with a drawable
     */
    private static void disableProgressBarAnimation(ProgressBar progressBar) {
        progressBar.setIndeterminateDrawable(new ColorDrawable(Color.BLUE));
    }

    public static Matcher<View> atPositionOnView(final int position, final Matcher<View> itemMatcher,
                                                 final int targetViewId) {

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + itemMatcher + " at position " + position);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return itemMatcher.matches(targetView);
            }
        };
    }
}