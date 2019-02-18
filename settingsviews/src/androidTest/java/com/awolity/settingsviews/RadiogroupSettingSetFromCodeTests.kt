package com.awolity.settingsviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingsviews.TextColorMatcher.withTextColor
import junit.framework.Assert.fail
import kotlinx.android.synthetic.main.activity_mock_defaults_rs.*
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

// TODO: listener invocation test

@RunWith(AndroidJUnit4::class)
class RadiogroupSettingSetFromCodeTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_rs
        restartActivity()
    }

    @Test
    fun setDisabled_GetDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            assert(!activityRule.activity.rs.isEnabled)
        }
    }

    @Test
    fun setDisabled_TitleLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
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
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
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
    fun setDisabled_RadioButtonLabelsLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.rb_one)).check(
            matches(
                withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
        onView(withId(R.id.rb_two)).check(
            matches(
                withTextColor(
                    R.color.text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setEnabled_GetEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.isEnabled = true
            assert(!activityRule.activity.rs.isEnabled)
        }
    }

    @Test
    fun setDisabled_setEnabled_TitleLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.isEnabled = true
        }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.text_title, activityRule.activity.resources)))
    }

    @Test
    fun setDisabled_setEnabled_DescriptionLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.isEnabled = true
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
    fun setDisabled_setEnabled_RadioButtonLabelLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.isEnabled = true
        }
        onView(withId(R.id.rb_one)).check(
            matches(
                withTextColor(
                    R.color.text_description,
                    activityRule.activity.resources
                )
            )
        )
        onView(withId(R.id.rb_two)).check(
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
        activityRule.runOnUiThread { activityRule.activity.rs.setTitle(title) }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescription_lookDescription() {
        activityRule.runOnUiThread { activityRule.activity.rs.setTitle(description) }
        onView(withId(R.id.tv_title)).check(matches(withText(description)))
    }

    @Test
    fun setRadioButtonLabel_lookLabel() {
        activityRule.runOnUiThread { activityRule.activity.rs.setRadioButtonsLabel(title, title) }
        onView(withId(R.id.rb_two)).check(matches(withText(title)))
        onView(withId(R.id.rb_one)).check(matches(withText(title)))
    }

    @Test
    fun setIcon_lookIcon() {
        activityRule.runOnUiThread { activityRule.activity.rs.setIcon(R.drawable.test_ic_android) }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun setTitleColor_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setTitleTextColor(activityRule.activity.getColor(R.color.test_text_title))
            activityRule.activity.rs.setTitle(title)
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
            activityRule.activity.rs.setDescriptionTextColor(activityRule.activity.getColor(R.color.test_text_description))
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
    fun setRadiobuttonLabelColor_lookRadiobuttonLabelColor() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setRadioButtonLabelColor(activityRule.activity.getColor(R.color.test_text_description))
        }
        onView(withId(R.id.rb_one)).check(
            matches(
                withTextColor(
                    R.color.test_text_description,
                    activityRule.activity.resources
                )
            )
        )
        onView(withId(R.id.rb_two)).check(
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
            activityRule.activity.rs.setTitle(title)
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.setDisabledTextColor(activityRule.activity.getColor(R.color.test_text_disabled))
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
            activityRule.activity.rs.setDisabledTextColor(activityRule.activity.getColor(R.color.test_text_disabled))
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.setDescription(description)
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
    fun setDisabledRadioButtonLabelColor_lookDisabledRadioButtonLabelColor() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setDisabledTextColor(activityRule.activity.getColor(R.color.test_text_disabled))
            activityRule.activity.rs.isEnabled = false
            activityRule.activity.rs.setDescription(description)
        }
        onView(withId(R.id.rb_one)).check(
            matches(
                withTextColor(
                    R.color.test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
        onView(withId(R.id.rb_one)).check(
            matches(
                withTextColor(
                    R.color.test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setChecked_looksChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setSelectedRadioButton(1)
        }
        onView(withId(R.id.rb_two)).check(matches(isChecked()))
        onView(withId(R.id.rb_one)).check(matches(not(isChecked())))
    }

    @Test
    fun setChecked_getChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setSelectedRadioButton(1)
            assert(activityRule.activity.rs.getSelectedRadioButton() == 1)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun setCheckedInvalid_ThrowException() {
        activityRule.runOnUiThread {
            activityRule.activity.rs.setSelectedRadioButton(2)
        }
    }

    @Test
    fun clickRadioButton2_getChecked() {
        onView(withId(R.id.rb_two)).perform(click())
        activityRule.runOnUiThread {
            assert(activityRule.activity.rs.getSelectedRadioButton() == 1)
        }
    }

    @Test
    fun clickRadioButton2_lookChecked() {
        onView(withId(R.id.rb_two)).perform(click())
        onView(withId(R.id.rb_two)).check(matches(isChecked()))
        onView(withId(R.id.rb_one)).check(matches(not(isChecked())))
    }

    @Test
    fun clickRadioButton2_clickRadioButton1_lookChecked() {
        onView(withId(R.id.rb_two)).perform(click())
        onView(withId(R.id.rb_one)).perform(click())
        onView(withId(R.id.rb_one)).check(matches(isChecked()))
        onView(withId(R.id.rb_two)).check(matches(not(isChecked())))
    }

    @Test
    fun clickRadioButton2_clickRadioButton1_getChecked() {
        onView(withId(R.id.rb_two)).perform(click())
        onView(withId(R.id.rb_one)).perform(click())
        activityRule.runOnUiThread {
            assert(activityRule.activity.rs.getSelectedRadioButton() == 0)
        }
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