package com.awolity.settingviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingviews.DrawableMatcher.withDrawable
import com.awolity.settingviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_attributes_rs.*
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RadiogroupSettingAttributesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_attributes_rs
        restartActivity()
    }

    @Test
    fun test_titleText_SetFromAttributes() {
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.SettingViews_test_title)))
    }

    @Test
    fun test_Description_TextSetFromAttributes() {
        onView(withId(R.id.tv_desc)).check(matches(withText(R.string.SettingViews_test_description)))
    }

    @Test
    fun test_Radiobutton1Label_TextSetFromAttributes() {
        onView(withId(R.id.rb_one)).check(matches(withText(R.string.SettingViews_test_text_1)))
    }

    @Test
    fun test_Radiobutton2Label_TextSetFromAttributes() {
        onView(withId(R.id.rb_two)).check(matches(withText(R.string.SettingViews_test_text_2)))
    }

    @Test
    fun test_TitleColor_SetFromAttributes() {
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.color_SettingViews_test_text_title, activityRule.activity.resources)))
    }

    @Test
    fun test_DescriptionColor_SetFromAttributes() {
        onView(withId(R.id.tv_desc)).check(matches(withTextColor(R.color.color_SettingViews_test_text_description, activityRule.activity.resources)))
    }

    @Test
    fun test_DisabledTitleColor_SetFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.color_SettingViews_test_text_disabled, activityRule.activity.resources)))
    }

    @Test
    fun test_DisabledDescriptionColor_SetFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(matches(withTextColor(R.color.color_SettingViews_test_text_disabled, activityRule.activity.resources)))
    }

    @Test
    fun test_DisabledRadioButtonLabelColor_SetFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.rs.isEnabled = false }
        onView(withId(R.id.rb_one)).check(matches(withTextColor(R.color.color_SettingViews_test_text_disabled, activityRule.activity.resources)))
        onView(withId(R.id.rb_two)).check(matches(withTextColor(R.color.color_SettingViews_test_text_disabled, activityRule.activity.resources)))
    }

    @Test
    fun test_Icon_SetFromAttributes() {
        onView(withId(R.id.iv_icon)).check(matches(withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun test_Radiobutton2_IsChecked_SetFromAttributes() {
        onView(withId(R.id.rb_two)).check(matches(isChecked()))
    }

    @Test
    fun test_Radiobutton1_IsNotSelected_SetFromAttributes() {
        onView(withId(R.id.rb_one)).check(matches(not(isChecked())))
    }

    @Test
    fun test_getSelected_IsSecond() {
        activityRule.runOnUiThread {
            assert(activityRule.activity.rs.getSelectedRadioButton() == 1)
        }
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}