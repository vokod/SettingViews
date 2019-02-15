package com.awolity.settingsviews

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class SwitchSetting : ConstraintLayout {

    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var aSwitch: Switch? = null
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0

    var checked: Boolean
        get() = aSwitch!!.isChecked
        set(checked) {
            aSwitch!!.isChecked = checked
        }

    constructor(context: Context) : super(context) {
        inflate()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SwitchSetting, 0, 0)
        try {
            setColorsFromAttributes(a)
            setIconFromAttributes(a)
            setLabelsFromAttributes(a)
            setSwitchFromAttributes(a)
        } finally {
            a.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate()
    }

    private fun inflate() {
        isSaveEnabled = true
        LayoutInflater.from(context).inflate(R.layout.setting_switch, this, true)
        titleTextView = findViewById(R.id.tv_title)
        descriptionTextView = findViewById(R.id.tv_desc)
        iconImageView = findViewById(R.id.iv_icon)
        aSwitch = findViewById(R.id.sw)
    }

    private fun setColorsFromAttributes(a: TypedArray) {
        disabledTextColor = a.getColor(
            R.styleable.SwitchSetting_disabledColor,
            resources.getColor(R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.SwitchSetting_titleTextColor,
            resources.getColor(R.color.text_description)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.SwitchSetting_descriptionTextColor,
            resources.getColor(R.color.text_description)
        )
        descriptionTextView!!.setTextColor(descriptionTextColor)
    }

    private fun setIconFromAttributes(a: TypedArray) {
        val iconResource = a.getResourceId(
            R.styleable.SwitchSetting_iconDrawableResource,
            R.drawable.ic_android
        )
        setIcon(iconResource)
    }

    private fun setLabelsFromAttributes(a: TypedArray) {
        val label = a.getString(R.styleable.SwitchSetting_titleText)
        if (label != null) {
            titleTextView!!.text = label
        }

        val description = a.getString(R.styleable.SwitchSetting_descriptionText)
        setDescription(description)
    }

    private fun setSwitchFromAttributes(a: TypedArray) {
        checked = a.getBoolean(R.styleable.SwitchSetting_checked, false)
    }

    fun setTitle(titleText: String) {
        titleTextView!!.text = titleText
    }

    fun setDescription(descriptionText: String?) {
        if (descriptionText == null) {
            descriptionTextView!!.visibility = View.GONE
            titleTextView!!.setPadding(
                titleTextView!!.paddingStart,
                getInPx(context, 12),
                titleTextView!!.paddingEnd,
                getInPx(context, 12)
            )
            return
        }
        descriptionTextView!!.text = descriptionText
    }


    fun setIcon(iconResource: Int) {
        iconImageView!!.setImageResource(iconResource)
    }

    fun setDisabledTextColor(color: Int) {
        disabledTextColor = color
        isEnabled = isEnabled
    }

    fun setTitleTextColor(color: Int) {
        titleTextColor = color
        isEnabled = isEnabled
    }

    fun setDescriptionTextColor(color: Int) {
        descriptionTextColor = color
        isEnabled = isEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        aSwitch!!.isEnabled = enabled
        if (enabled) {
            titleTextView!!.setTextColor(titleTextColor)
            descriptionTextView!!.setTextColor(descriptionTextColor)
            iconImageView!!.alpha = 1f
        } else {
            descriptionTextView!!.setTextColor(disabledTextColor)
            titleTextView!!.setTextColor(disabledTextColor)
            iconImageView!!.alpha = 0.5f
        }
    }

    fun setOnCheckedChangedListener(onCheckedChangedListener: CompoundButton.OnCheckedChangeListener) {
        aSwitch!!.setOnCheckedChangeListener(onCheckedChangedListener)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SwitchSettingSavedState(superState!!)
        ss.descriptionColorValue = descriptionTextColor
        ss.disabledColorValue = disabledTextColor
        ss.titleColorValue = titleTextColor
        return ss
        // TODO: tesztelni a switched állapotot
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SwitchSettingSavedState
        super.onRestoreInstanceState(ss.superState)
        setDescriptionTextColor(ss.descriptionColorValue)
        setDisabledTextColor(ss.disabledColorValue)
        setTitleTextColor(ss.titleColorValue)
    }

    companion object {
        private const val TAG = "SwitchSetting"

        private fun getInPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}
