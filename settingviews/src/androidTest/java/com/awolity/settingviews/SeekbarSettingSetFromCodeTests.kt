package com.awolity.settingviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.sv_activity_mock_defaults_ses.*
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// TODO: listener invocation test

@RunWith(AndroidJUnit4::class)
class SeekbarSettingSetFromCodeTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.sv_activity_mock_defaults_ses
        restartActivity()
    }

    @Test
    fun setDisabled_GetDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.isEnabled = false
            assert(!activityRule.activity.ses.isEnabled)
        }
    }

    @Test
    fun setDisabled_TitleLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ses.isEnabled = false }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.sv_color_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabled_DescriptionLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ses.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.sv_color_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabled_SeekbarLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.ses.isEnabled = false }
        onView(withId(R.id.seekbar)).check(
            matches(not(isEnabled()))
        )
    }

    @Test
    fun setEnabled_GetEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.isEnabled = false
            activityRule.activity.ses.isEnabled = true
            assert(!activityRule.activity.ses.isEnabled)
        }
    }

    @Test
    fun setDisabled_setEnabled_TitleLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.isEnabled = false
            activityRule.activity.ses.isEnabled = true
        }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.sv_color_text_title, activityRule.activity.resources)))
    }

    @Test
    fun setDisabled_setEnabled_DescriptionLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.isEnabled = false
            activityRule.activity.ses.isEnabled = true
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.sv_color_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setTitle_lookTitle() {
        activityRule.runOnUiThread { activityRule.activity.ses.setTitle(title) }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescription_lookDescription() {
        activityRule.runOnUiThread { activityRule.activity.ses.setTitle(description) }
        onView(withId(R.id.tv_title)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_lookIcon() {
        activityRule.runOnUiThread { activityRule.activity.ses.setIcon(R.drawable.sv_test_ic_android) }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.sv_test_ic_android)))
    }

    @Test
    fun setTitleColor_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.setTitleTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_title))
            activityRule.activity.ses.setTitle(title)
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
    fun setDescriptionColor_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.setDescriptionTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_description))
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
    fun setDisabledColorTitle_lookDisabledColorTitle() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.setTitle(title)
            activityRule.activity.ses.isEnabled = false
            activityRule.activity.ses.setDisabledTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_disabled))
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.sv_color_test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabledColorDescription_lookDisabledColorDescription() {
        activityRule.runOnUiThread {
            activityRule.activity.ses.setDisabledTextColor(activityRule.activity.getColor(R.color.sv_color_test_text_disabled))
            activityRule.activity.ses.isEnabled = false
            activityRule.activity.ses.setDescription(description)
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.sv_color_test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
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
        const val max = 300
        const val progress = 150
        const val invalidProgress = 301
    }
}