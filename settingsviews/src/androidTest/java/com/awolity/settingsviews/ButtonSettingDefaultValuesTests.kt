package com.awolity.settingsviews

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_mock_defaults_bs.*
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ButtonSettingDefaultValuesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_bs
        restartActivity()
    }

    @Test
    fun testDefaultTitleText() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_title)).check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun testDefaultDescriptionText() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_desc)).check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun testDefaultTitleColor() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_title)).check(
            ViewAssertions.matches(
                TextColorMatcher.withTextColor(
                    R.color.text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun testDefaultDescriptionColor() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_desc)).check(
            ViewAssertions.matches(
                TextColorMatcher.withTextColor(
                    R.color.text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun testDefaultIcon() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_icon))
            .check(ViewAssertions.matches(DrawableMatcher.withDrawable(R.drawable.ic_android)))
    }

    @Test
    fun testDefaultCheckmarkIcon() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_checkmark))
            .check(ViewAssertions.matches(DrawableMatcher.withDrawable(R.drawable.ic_check_black)))
    }

    @Test
    fun testDefaultCheckmarkInvisible() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_checkmark))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun testDefaultDisabledTitleColor() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        Espresso.onView(ViewMatchers.withId(R.id.tv_title)).check(
            ViewAssertions.matches(
                TextColorMatcher.withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun testDefaultDisabledDescriptionColor() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        Espresso.onView(ViewMatchers.withId(R.id.tv_desc)).check(
            ViewAssertions.matches(
                TextColorMatcher.withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}