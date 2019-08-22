package com.awolity.settingviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.sv_activity_mock_defaults_ss.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwitchSettingDefaultValuesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.sv_activity_mock_defaults_ss
        restartActivity()
    }

    @Test
    fun test_DefaultTitleText() {
        onView(withId(R.id.tv_title)).check(matches(withText("")))
    }

    @Test
    fun test_DefaultDescriptionText() {
        onView(withId(R.id.tv_desc)).check(matches(withText("")))
    }

    @Test
    fun test_DefaultTitleColor() {
        onView(withId(R.id.tv_title)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.sv_color_text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_DefaultDescriptionColor() {
        onView(withId(R.id.tv_desc)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.sv_color_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_DefaultIcon() {
        onView(withId(R.id.iv_icon))
            .check(matches(DrawableMatcher.withDrawable(R.drawable.sv_ic_android)))
    }

    @Test
    fun test_DefaultDisabledTitleColor() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
        onView(withId(R.id.tv_title)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.sv_color_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_DefaultDisabledDescriptionColor() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.sv_color_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_Switch_Default_Not_Checked() {
        activityRule.runOnUiThread {
            assert(!(activityRule.activity.ss.checked))
        }
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}