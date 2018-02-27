package fr.nspu.dev.recordvoicelearning

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Utility methods for espresso tests.
 */
object EspressoTestUtil {
    /**
     * Disables progress bar animations for the views of the given activity rule
     *
     * @param activityTestRule The activity rule whose views will be checked
     */
    fun disableAnimations(
            activityTestRule: ActivityTestRule<out FragmentActivity>) {
        activityTestRule.activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment?, v: View?,
                                                               savedInstanceState: Bundle?) {
                                // traverse all views, if any is a progress bar, replace its animation
                                traverseViews(v)
                            }
                        }, true)
    }

    private fun traverseViews(view: View?) {
        if (view is ViewGroup) {
            traverseViewGroup((view as ViewGroup?)!!)
        } else {
            if (view is ProgressBar) {
                disableProgressBarAnimation((view as ProgressBar?)!!)
            }
        }
    }

    private fun traverseViewGroup(view: ViewGroup) {
        if (view is RecyclerView) {
            disableRecyclerViewAnimations(view)
        } else {
            val count = view.childCount
            for (i in 0 until count) {
                traverseViews(view.getChildAt(i))
            }
        }
    }

    private fun disableRecyclerViewAnimations(view: RecyclerView) {
        view.itemAnimator = null
    }

    /**
     * necessary to run tests on older API levels where progress bar uses handler loop to animate.
     *
     * @param progressBar The progress bar whose animation will be swapped with a drawable
     */
    private fun disableProgressBarAnimation(progressBar: ProgressBar) {
        progressBar.indeterminateDrawable = ColorDrawable(Color.BLUE)
    }

    fun atPositionOnView(position: Int, itemMatcher: Matcher<View>,
                         targetViewId: Int): Matcher<View> {

        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position $position")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
                return itemMatcher.matches(targetView)
            }
        }
    }
}