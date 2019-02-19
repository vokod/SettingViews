package com.awolity.settingsviews

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.awolity.settingsviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_defaults_bs.*
import kotlinx.android.synthetic.main.activity_mock_defaults_rs.*
import kotlinx.android.synthetic.main.activity_mock_defaults_ses.*
import kotlinx.android.synthetic.main.activity_mock_defaults_ss.*
import org.hamcrest.CoreMatchers.not
import java.lang.IllegalStateException

@RunWith(AndroidJUnit4::class)
class SwitchSettingSaveStateTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_ss
        restartActivity()
    }

    @Test
    fun setDisabled_Rotate_getDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.isEnabled = false
            rotate()
            assert(!activityRule.activity.ss.isEnabled)
        }
    }

    @Test
    fun setTitleColor_Rotate_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setTitleTextColor(activityRule.activity.getColor(R.color.test_text_title))
            rotate()
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.test_text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setTitleText_Rotate_lookTitleText() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setTitle(title)
            rotate()
        }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescriptionColor_Rotate_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setDescriptionTextColor(activityRule.activity.getColor(R.color.test_text_description))
            rotate()
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.test_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDescriptionText_Rotate_lookDescriptionText() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setDescription(description)
            rotate()
        }
        onView(withId(R.id.tv_desc)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_Rotate_lookIcon() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setIcon(R.drawable.test_ic_android)
            rotate()
        }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun setCecked_Rotate_getChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.checked = true
            rotate()
            assert(activityRule.activity.ss.checked)
        }
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }

    private fun rotate() {
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    companion object {
        const val title = "Title"
        const val description = "Description"
    }
}