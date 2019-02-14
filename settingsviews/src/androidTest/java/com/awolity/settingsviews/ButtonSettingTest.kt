package com.awolity.settingsviews

import android.content.Intent
import android.content.res.Resources
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import com.awolity.settingsviews.TextColorMatcher.withTextColor
import org.hamcrest.Description
import org.hamcrest.Matcher


@RunWith(AndroidJUnit4::class)
class ButtonSettingTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock
    }

    @Test
    fun testTextsSetFromAttributes() {
        restartActivity()
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.test_title)))
        onView(withId(R.id.tv_desc)).check(matches(withText(R.string.test_description)))
    }

    @Test
    fun testColorsSetFromAttributes() {
        restartActivity()
        onView(withId(R.id.tv_title)).check(matches(withTextColor(
            R.color.text_dark, activityRule.activity.resources)))
        onView(withId(R.id.tv_desc)).check(matches(withTextColor(
            R.color.text, activityRule.activity.resources)))
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}