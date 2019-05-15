package com.awolity.settingviews

import android.content.Intent
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

// TODO: checkable teszt (ha chekable, akkor a checkable-iv-nek kell a hely

@RunWith(AndroidJUnit4::class)
class ButtonSettingSetFromCodeTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_defaults_bs
        restartActivity()
    }

    @Test
    fun setDisabled_GetDisabled() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isEnabled = false
            assert(!activityRule.activity.bs.isEnabled)
        }
    }

    @Test
    fun setDisabled_TitleLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabled_DescriptionLooksDisabled() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setEnable_GetEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isEnabled = false
            activityRule.activity.bs.isEnabled = true
            assert(!activityRule.activity.bs.isEnabled)
        }
    }

    @Test
    fun setDisabled_setEnabled_TitleLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isEnabled = false
            activityRule.activity.bs.isEnabled = true
        }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.color_SettingViews_text_title, activityRule.activity.resources)))
    }

    @Test
    fun setDisabled_setEnabled_DescriptionLooksEnabled() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isEnabled = false
            activityRule.activity.bs.isEnabled = true
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setTitle_lookTitle() {
        activityRule.runOnUiThread { activityRule.activity.bs.setTitle(title) }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun setDescription_lookDescription() {
        activityRule.runOnUiThread { activityRule.activity.bs.setTitle(description) }
        onView(withId(R.id.tv_title)).check(matches(withText(description)))
    }

    @Test
    fun setIcon_lookIcon() {
        activityRule.runOnUiThread { activityRule.activity.bs.setIcon(R.drawable.test_ic_android) }
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun setTitleColor_lookTitleColor() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setTitleTextColor(activityRule.activity.getColor(R.color.color_SettingViews_test_text_title))
            activityRule.activity.bs.setTitle(title)
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_test_text_title,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDescriptionColor_lookDescriptionColor() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setDescriptionTextColor(activityRule.activity.getColor(R.color.color_SettingViews_test_text_description))
            activityRule.activity.bs.setDescription(description)
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_test_text_description,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabledColorTitle_lookDisabledColorTitle() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setTitle(title)
            activityRule.activity.bs.isEnabled = false
            activityRule.activity.bs.setDisabledTextColor(activityRule.activity.getColor(R.color.color_SettingViews_test_text_disabled))
        }
        onView(withId(R.id.tv_title)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setDisabledColorDescription_lookDisabledColorDescription() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.setDisabledTextColor(activityRule.activity.getColor(R.color.color_SettingViews_test_text_disabled))
            activityRule.activity.bs.isEnabled = false
            activityRule.activity.bs.setDescription(description)
        }
        onView(withId(R.id.tv_desc)).check(
            matches(
                withTextColor(
                    R.color.color_SettingViews_test_text_disabled,
                    activityRule.activity.resources
                )
            )
        )
    }

    @Test
    fun setCheckable_GetCheckable() {
        activityRule.runOnUiThread { activityRule.activity.bs.isCheckable = true }
        activityRule.runOnUiThread { assert(activityRule.activity.bs.isCheckable) }
    }

    @Test
    fun setCheckable_GetNotChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            assert(!activityRule.activity.bs.checked)
        }
    }

    @Test
    fun setCheckable_looksNotChecked() {
        activityRule.runOnUiThread { activityRule.activity.bs.isCheckable = true }
        onView(withId(R.id.iv_checkmark)).check(matches(not(isDisplayed())))
    }

    @Test
    fun setUnchekable_getNotCheckable() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = false
            assert(!activityRule.activity.bs.isCheckable)
        }
    }

    @Test
    fun setCheckable_setChecked_getChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
            assert(activityRule.activity.bs.checked)
        }
    }

    @Test
    fun setCheckable_setChecked_looksChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
        }
        onView(withId(R.id.iv_checkmark)).check(matches(isDisplayed()))
    }

    @Test
    fun setCheckable_setChecked_setUnchecked_getNotChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
            activityRule.activity.bs.checked = false
            assert(!activityRule.activity.bs.checked)
        }
    }

    @Test
    fun setCheckable_setChecked_setUnchecked_looksNotChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
            activityRule.activity.bs.checked = false
        }
        onView(withId(R.id.iv_checkmark)).check(matches(not(isDisplayed())))
    }


    fun setNotCheckable_setChecked_getChecked_returnFalse() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = false
            activityRule.activity.bs.checked = true
            assert(!activityRule.activity.bs.checked)
        }
    }

    @Test
    fun setNotCheckable_getChecked_returnFalse() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = false
            assert(!activityRule.activity.bs.checked)
        }
    }

    @Test
    fun setSetCheckable_setChecked_setUnchekable_looksNotChecked() {
        activityRule.runOnUiThread {
            activityRule.activity.bs.isCheckable = true
            activityRule.activity.bs.checked = true
            activityRule.activity.bs.isCheckable = false
        }
        onView(withId(R.id.iv_checkmark)).check(matches(not(isDisplayed())))
    }

    @Test
    fun setCheckmarkIcon_looksChekmarkIcon() {
        activityRule.runOnUiThread { activityRule.activity.bs.setCheckmarkIcon(R.drawable.test_ic_check_red) }
        onView(withId(R.id.iv_checkmark)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_check_red)))
    }

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