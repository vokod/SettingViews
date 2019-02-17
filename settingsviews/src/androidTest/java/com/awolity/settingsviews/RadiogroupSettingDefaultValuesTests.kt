package com.awolity.settingsviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_mock_attributes_bs.*
import kotlinx.android.synthetic.main.activity_mock_defaults_rs.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RadiogroupSettingDefaultValuesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_rs
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
    fun test_DefaultRadiobuttonOneLabelText() {
        onView(withId(R.id.rb_one)).check(matches(withText("")))
    }

    @Test
    fun test_DefaultRadiobuttonTwoLabelText() {
        onView(withId(R.id.rb_two)).check(matches(withText("")))
    }

    @Test
    fun test_DefaultTitleColor() {
        onView(withId(R.id.tv_title)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_DefaultRadioButtonLabelColor() {
        onView(withId(R.id.rb_one)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun test_DefaultRadioButton_firstLooksSelected() {
        onView(withId(R.id.rb_one)).check(matches(isChecked()))
    }


    @Test
    fun test_DefaultRadioButton_secondLooksNotSelected() {
        onView(withId(R.id.rb_two)).check(matches(isNotChecked()))
    }

    @Test
    fun test_DefaultRadioButton_getSelected() {
        activityRule.runOnUiThread {
            assert(activityRule.activity.rs.getSelectedRadioButton())
        }
    }

    @Test
    fun test_DefaultDescriptionColor() {
        onView(withId(R.id.tv_desc)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun testDefaultIcon() {
        onView(withId(R.id.iv_icon))
            .check(matches(DrawableMatcher.withDrawable(R.drawable.ic_android)))
    }

    @Test
    fun testDefaultDisabledTitleColor() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.tv_title)).check(
            matches(
                TextColorMatcher.withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun testDefaultDisabledDescriptionColor() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(
            matches(
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