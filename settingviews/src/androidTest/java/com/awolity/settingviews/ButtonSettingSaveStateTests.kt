package com.awolity.settingviews

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.awolity.settingviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_defaults_bs.*
import org.hamcrest.CoreMatchers.not
import java.lang.IllegalStateException

@RunWith(AndroidJUnit4::class)
class ButtonSettingSaveStateTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_bs
        restartActivity()
    }

    @Test
    fun setDisabled_Rotate_getDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isEnabled = false
            rotate()
            assert(!activityRule.activity.bs.isEnabled)
        }
    }

    @Test
    fun setTitleColor_Rotate_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setTitleTextColor(activityRule.activity.getColor(R.color.test_text_title))
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
            activityRule.activity.bs.setTitle(title)
            rotate()
        }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescriptionColor_Rotate_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setDescriptionTextColor(activityRule.activity.getColor(R.color.test_text_description))
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
            activityRule.activity.bs.setDescription(description)
            rotate()
        }
        onView(withId(R.id.tv_desc)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_Rotate_lookIcon() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setIcon(R.drawable.test_ic_android)
            rotate()
        }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))

    }

    @Test
    fun setCheckable_Rotate_getChekable() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            rotate()
            assert(activityRule.activity.bs.isCheckable)
        }
    }

    @Test
    fun setChecked_Rotate_getCheked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
            rotate()
            assert(activityRule.activity.bs.checked)
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