package com.awolity.settingsviews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.awolity.settingsviews.DrawableMatcher.withDrawable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.awolity.settingsviews.TextColorMatcher.withTextColor
import kotlinx.android.synthetic.main.activity_mock_attributes.*

@RunWith(AndroidJUnit4::class)
class ButtonSettingAttributesTests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, true, false)

    @Before
    fun setup() {
        MockActivity.layout = R.layout.activity_mock_attributes
        restartActivity()
    }

    @Test
    fun testTitleTextSetFromAttributes() {
        onView(withId(R.id.tv_title)).check(matches(withText(R.string.test_title)))
    }

    @Test
    fun testDescriptionTextSetFromAttributes() {
        onView(withId(R.id.tv_desc)).check(matches(withText(R.string.test_description)))
    }

    @Test
    fun testTitleColorSetFromAttributes() {
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.test_text_title, activityRule.activity.resources)))
    }

    @Test
    fun testDescriptionColorSetFromAttributes() {
        onView(withId(R.id.tv_desc)).check(matches(withTextColor(R.color.test_text_description, activityRule.activity.resources)))
    }

    @Test
    fun testDisabledTitleColorFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        onView(withId(R.id.tv_title)).check(matches(withTextColor(R.color.test_text_disabled, activityRule.activity.resources)))
    }

    @Test
    fun testDisabledDescriptionColorFromAttribute() {
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        onView(withId(R.id.tv_desc)).check(matches(withTextColor(R.color.test_text_disabled, activityRule.activity.resources)))
    }

    @Test
    fun testIconSetFromAttributes() {
        onView(withId(R.id.iv_icon)).check(matches(withDrawable(R.drawable.test_ic_android)))
    }

    @Test
    fun testCheckmarkIconSetFromAttributes() {
        onView(withId(R.id.iv_checkmark)).check(matches(withDrawable(R.drawable.ic_check_black)))
    }

    @Test
    fun testCheckmarkVisibleSetFromAttributes() {
        onView(withId(R.id.iv_checkmark)).check(matches(isDisplayed()))
    }





    @Test
    fun testDisable() {
        restartActivity()
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        activityRule.runOnUiThread { assert(!activityRule.activity.bs.isEnabled) }
    }

    @Test
    fun testEnable() {
        restartActivity()
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = false }
        activityRule.runOnUiThread { activityRule.activity.bs.isEnabled = true }
        activityRule.runOnUiThread { assert(!activityRule.activity.bs.isEnabled) }
    }

    @Test
    fun testSetTitle() {
        val title = "This is the new Title"
        restartActivity()
        activityRule.runOnUiThread { activityRule.activity.bs.setTitle(title) }
        onView(withId(R.id.tv_title)).check(matches(withText(title)))
    }

    @Test
    fun testSetDescription() {
        val description = "This is the new description. Lorem ipsum dolor sit amet"
        restartActivity()
        activityRule.runOnUiThread { activityRule.activity.bs.setTitle(description) }
        onView(withId(R.id.tv_title)).check(matches(withText(description)))
    }

    @Test
    fun testSetIcon() {

    }

    @Test
    fun testSetTitleColor() {
    }

    @Test
    fun testSetDescriptionColor() {
    }

    @Test
    fun testSetDisabledColor() {
    }

    @Test
    fun testSetCheckable() {
    }

    @Test
    fun testSetUnchekable() {
    }

    @Test
    fun testCheckCheckable() {
    }

    @Test
    fun testChekUnchekable() {
    }

    @Test
    fun testUncheckCheckable() {
    }

    @Test
    fun testUnchekUnchekable() {
    }


    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}