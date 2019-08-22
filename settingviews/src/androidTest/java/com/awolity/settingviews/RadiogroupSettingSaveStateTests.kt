package com.awolity.settingviews

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
import com.awolity.settingviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.sv_activity_mock_defaults_rs.*

@RunWith(AndroidJUnit4::class)
class RadiogroupSettingSaveStateTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.sv_activity_mock_defaults_rs
        restartActivity()
    }

    @Test
    fun setDisabled_Rotate_getDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            rotate()
            assert(!activityRule.activity.rs.isEnabled)
        }
    }

    @Test
    fun setTitleColor_Rotate_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setTitleTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_title))
            rotate()
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.sv_color_test_text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setTitleText_Rotate_lookTitleText() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setTitle(title)
            rotate()
        }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescriptionColor_Rotate_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setDescriptionTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_description))
            rotate()
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.sv_color_test_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDescriptionText_Rotate_lookDescriptionText() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setDescription(description)
            rotate()
        }
        onView(withId(R.id.tv_desc)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_Rotate_lookIcon() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setIcon(R.drawable.sv_test_ic_android)
            rotate()
        }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.sv_test_ic_android)))
    }

    @Test
    fun setRadiobuttonLabels_Rotate_LookRadiobuttonLabels1() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setRadioButtonsLabel(label1, label2)
            rotate()
        }
        onView(withId(R.id.rb_one)).check(matches(withText(label1)))
        onView(withId(R.id.rb_two)).check(matches(withText(label2)))
    }

    @Test
    fun setRadiobuttonLabels_Rotate_LookRadiobuttonLabels2() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setRadioButtonsLabel(label1, label2)
            rotate()
        }
        onView(withId(R.id.rb_two)).check(matches(withText(label2)))
    }

    @Test
    fun selectSecondRadiobutton_Rotate_getSelectedRadiobutton() {
        onView(withId(R.id.rb_two)).perform(click())
        activityRule.runOnUiThread {
            rotate()
        }
        onView(withId(R.id.rb_two)).check(matches(isChecked()))
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
        const val label1 = "label1"
        const val label2 = "label2"
    }
}