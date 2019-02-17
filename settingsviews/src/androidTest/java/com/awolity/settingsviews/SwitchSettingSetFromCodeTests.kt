package com.awolity.settingsviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingsviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_defaults_ss.*
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// TODO: listener invocation test

@RunWith(AndroidJUnit4::class)
class SwitchSettingSetFromCodeTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_ss
        restartActivity()
    }

    @Test
    fun setDisabled_GetDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.isEnabled = false
            assert(!activityRule.activity.ss.isEnabled)
        }
    }

    @Test
    fun setDisabled_TitleLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabled_DescriptionLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabled_SwitchLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
        onView(withId(R.id.sw)).check(
            matches(not(isEnabled()))
        )
    }

    @Test
    fun setEnabled_GetEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.isEnabled = false
            activityRule.activity.ss.isEnabled = true
            assert(!activityRule.activity.ss.isEnabled)
        }
    }

    @Test
    fun setDisabled_setEnabled_TitleLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.isEnabled = false
            activityRule.activity.ss.isEnabled = true
        }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.text_title, activityRule.activity.resources)))
    }

    @Test
    fun setDisabled_setEnabled_DescriptionLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.isEnabled = false
            activityRule.activity.ss.isEnabled = true
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setTitle_lookTitle() {
        activityRule.runOnUiThread { activityRule.activity.ss.setTitle(title) }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescription_lookDescription() {
        activityRule.runOnUiThread { activityRule.activity.ss.setTitle(description) }
        onView(withId(R.id.tv_title)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_lookIcon() {
        activityRule.runOnUiThread { activityRule.activity.ss.setIcon(R.drawable.test_ic_android) }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun setTitleColor_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setTitleTextColor(activityRule.activity.getColor(R.color.test_text_title))
            activityRule.activity.ss.setTitle(title)
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
    fun setDescriptionColor_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setDescriptionTextColor(activityRule.activity.getColor(R.color.test_text_description))
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
    fun setDisabledColorTitle_lookDisabledColorTitle() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setTitle(title)
            activityRule.activity.ss.isEnabled = false
            activityRule.activity.ss.setDisabledTextColor(activityRule.activity.getColor(R.color.test_text_disabled))
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabledColorDescription_lookDisabledColorDescription() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.setDisabledTextColor(activityRule.activity.getColor(R.color.test_text_disabled))
            activityRule.activity.ss.isEnabled = false
            activityRule.activity.ss.setDescription(description)
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setSwitchedOn_getChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.checked = true
            assert(activityRule.activity.ss.checked)
        }
    }

    @Test
    fun setSWitchedOn_looksSwitchedOn() {
        activityRule.runOnUiThread {
            activityRule.activity.ss.checked = true
        }
        onView(withId(R.id.sw)).check(matches(isChecked()))
    }

    @Test
    fun clicked_getChecked() {
        onView(withId(R.id.sw)).perform(click())
        activityRule.runOnUiThread { assert(activityRule.activity.ss.checked) }
    }


    @Test
    fun clicked_looksSwitchedOn() {
        onView(withId(R.id.sw)).perform(click())
        onView(withId(R.id.sw)).check(matches(isChecked()))
    }

    @Test
    fun clicked_clicked_getChecked() {
        onView(withId(R.id.sw)).perform(click())
        onView(withId(R.id.sw)).perform(click())
        activityRule.runOnUiThread { assert(!activityRule.activity.ss.checked) }
    }

    @Test
    fun clicked_clicked_looksSwitchedOff() {
        onView(withId(R.id.sw)).perform(click())
        onView(withId(R.id.sw)).perform(click())
        onView(withId(R.id.sw)).check(matches(not(isChecked())))
    }

    // TODO: listenerhez teszteket írni

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }

    companion object {
        const val title = "Title"
        const val description = "Description"
    }
}