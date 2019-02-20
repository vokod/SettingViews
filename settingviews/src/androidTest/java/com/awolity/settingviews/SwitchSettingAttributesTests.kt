package com.awolity.settingviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_attributes_ss.*
import kotlinx.android.synthetic.main.activity_mock_defaults_ses.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwitchSettingAttributesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_attributes_ss
        restartActivity()
    }

    @Test
    fun test_titleText_SetFromAttributes() {
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.test_title)))
    }

    @Test
    fun test_Description_TextSetFromAttributes() {
        onView(withId(R.id.tv_desc)).check(matches(withText(R.string.test_description)))
    }

    @Test
    fun test_TitleColor_SetFromAttributes() {
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
    fun test_DescriptionColor_SetFromAttributes() {
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
    fun test_DisabledTitleColor_SetFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
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
    fun test_DisabledDescriptionColor_SetFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.ss.isEnabled = false }
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
    fun test_Icon_SetFromAttributes() {
        onView(withId(R.id.iv_icon)).check(matches(DrawableMatcher.withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun test_Checked_SetFromAttributes(){
        onView(withId(R.id.sw)).check(matches(isChecked()))
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}