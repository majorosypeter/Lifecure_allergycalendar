package com.peter_majorosy.lifecure_allergycalendar


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegAndLoginTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun regAndLoginTest() {
        val appCompatTextView = onView(
            allOf(withId(R.id.notreg), withText("Not registered yet? Create an account!"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        appCompatTextView.perform(click())

        val appCompatEditText = onView(
            allOf(withId(R.id.regname),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    0),
                isDisplayed()))
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(withId(R.id.regname),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    0),
                isDisplayed()))
        appCompatEditText2.perform(replaceText("Teszt Elek"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(withId(R.id.regemail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        appCompatEditText3.perform(replaceText("tesztelek@hotmail.com"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(withId(R.id.regemail), withText("tesztelek@hotmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        appCompatEditText4.perform(click())

        val appCompatEditText5 = onView(
            allOf(withId(R.id.regemail), withText("tesztelek@hotmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        appCompatEditText5.perform(replaceText("tesztelek@gmail.com"))

        val appCompatEditText6 = onView(
            allOf(withId(R.id.regemail), withText("tesztelek@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        appCompatEditText6.perform(closeSoftKeyboard())

        val appCompatEditText7 = onView(
            allOf(withId(R.id.regpw1),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    4),
                isDisplayed()))
        appCompatEditText7.perform(replaceText("tesztelek11"), closeSoftKeyboard())

        val appCompatEditText8 = onView(
            allOf(withId(R.id.regpw1), withText("tesztelek11"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    4),
                isDisplayed()))
        appCompatEditText8.perform(pressImeActionButton())

        val appCompatEditText9 = onView(
            allOf(withId(R.id.regpw2),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    5),
                isDisplayed()))
        appCompatEditText9.perform(replaceText("tesztelek11"), closeSoftKeyboard())

        val appCompatEditText10 = onView(
            allOf(withId(R.id.regpw2), withText("tesztelek11"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    5),
                isDisplayed()))
        appCompatEditText10.perform(pressImeActionButton())

        val appCompatButton = onView(
            allOf(withId(R.id.regbutton), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    6),
                isDisplayed()))
        appCompatButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView = onView(
            allOf(withId(R.id.profile), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0),
                    4),
                isDisplayed()))
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(withId(R.id.tv_email), withText("tesztelek@gmail.com"),
                withParent(withParent(withId(R.id.fl_wrapper))),
                isDisplayed()))
        textView.check(matches(withText("tesztelek@gmail.com")))

        val appCompatImageView = onView(
            allOf(withId(R.id.btn_logout),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fl_wrapper),
                        0),
                    0),
                isDisplayed()))
        appCompatImageView.perform(click())

        val appCompatEditText11 = onView(
            allOf(withId(R.id.logemail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    1),
                isDisplayed()))
        appCompatEditText11.perform(click())

        val appCompatEditText12 = onView(
            allOf(withId(R.id.logemail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    1),
                isDisplayed()))
        appCompatEditText12.perform(replaceText("tesztelek@gmail.com"), closeSoftKeyboard())

        val appCompatEditText13 = onView(
            allOf(withId(R.id.logemail), withText("tesztelek@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    1),
                isDisplayed()))
        appCompatEditText13.perform(pressImeActionButton())

        val appCompatEditText14 = onView(
            allOf(withId(R.id.logpw),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()))
        appCompatEditText14.perform(replaceText("tesztelek11"), closeSoftKeyboard())

        val appCompatEditText15 = onView(
            allOf(withId(R.id.logpw), withText("tesztelek11"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()))
        appCompatEditText15.perform(pressImeActionButton())

        val appCompatButton2 = onView(
            allOf(withId(R.id.logbutton), withText("Sign in"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    4),
                isDisplayed()))
        appCompatButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val bottomNavigationItemView2 = onView(
            allOf(withId(R.id.gallery), withContentDescription("Gallery"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0),
                    0),
                isDisplayed()))
        bottomNavigationItemView2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(withId(R.id.calendar), withContentDescription("Calendar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0),
                    1),
                isDisplayed()))
        bottomNavigationItemView3.perform(click())

        val bottomNavigationItemView4 = onView(
            allOf(withId(R.id.database), withContentDescription("Database"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0),
                    3),
                isDisplayed()))
        bottomNavigationItemView4.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
