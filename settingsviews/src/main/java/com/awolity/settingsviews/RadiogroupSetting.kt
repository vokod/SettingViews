package com.awolity.settingsviews

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class RadiogroupSetting : ConstraintLayout {
    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var firstButton: RadioButton? = null
    private var secondButton: RadioButton? = null
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0

    constructor(context: Context) : super(context) {
        inflate()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RadiogroupSetting, 0, 0)
        try {
            setColorsFromAttributes(a)
            setIconFromAttributes(a)
            setLabelsFromAttributes(a)
            setSelectedRadioButton(true)
        } finally {
            a.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate()
    }

    private fun inflate() {
        isSaveEnabled = true
        LayoutInflater.from(context).inflate(R.layout.setting_radiogroup, this, true)
        titleTextView = findViewById(R.id.tv_title)
        descriptionTextView = findViewById(R.id.tv_desc)
        iconImageView = findViewById(R.id.iv_icon)
        firstButton = findViewById(R.id.rb_one)
        secondButton = findViewById(R.id.rb_two)
    }

    private fun setColorsFromAttributes(a: TypedArray) {
        disabledTextColor = a.getColor(
            R.styleable.RadiogroupSetting_disabledColor,
            resources.getColor(R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.RadiogroupSetting_titleTextColor,
            resources.getColor(R.color.text)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.RadiogroupSetting_descriptionTextColor,
            resources.getColor(R.color.text)
        )
        descriptionTextView!!.setTextColor(descriptionTextColor)

        val radioButtonLabelColor = a.getColor(
            R.styleable.RadiogroupSetting_radioButtonLabelTextColor,
            resources.getColor(R.color.text)
        )
        setRadioButtonLabelColor(radioButtonLabelColor)
    }

    private fun setIconFromAttributes(a: TypedArray) {
        val iconResource = a.getResourceId(
            R.styleable.RadiogroupSetting_iconDrawableResource,
            R.drawable.ic_android
        )
        setIcon(iconResource)
    }

    private fun setLabelsFromAttributes(a: TypedArray) {
        val label = a.getString(R.styleable.RadiogroupSetting_titleText)
        if (label != null) {
            titleTextView!!.text = label
        }

        val description = a.getString(R.styleable.RadiogroupSetting_descriptionText)
        setDescription(description)

        val labelFirstRadioButtonLabel = a.getString(R.styleable.RadiogroupSetting_firstRadioButtonText)
        val labelSecondRadioButtonLabel = a.getString(R.styleable.RadiogroupSetting_secondRadioButtonText)
        setRadioButtonsLabel(labelFirstRadioButtonLabel, labelSecondRadioButtonLabel)
    }

    fun setTitle(title: String) {
        titleTextView!!.text = title
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

    fun setRadioButtonLabelColor(color: Int) {
        firstButton!!.setTextColor(color)
        secondButton!!.setTextColor(color)
    }

    fun setRadioButtonsLabel(label1: String?, label2: String?) {
        if (label1 != null)
            firstButton!!.text = label1
        if (label2 != null)
            secondButton!!.text = label2
    }

    fun setListener(listener: RadiogroupSettingListener) {
        firstButton!!.setOnClickListener { listener.OnRadioButtonClicked(0) }
        secondButton!!.setOnClickListener { listener.OnRadioButtonClicked(1) }
    }

    fun setSelectedRadioButton(firstSelected: Boolean) {
        firstButton!!.isChecked = firstSelected
        secondButton!!.isChecked = !firstSelected
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
        firstButton!!.isEnabled = enabled
        secondButton!!.isEnabled = enabled
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



    interface RadiogroupSettingListener {
        fun OnRadioButtonClicked(selected: Int)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = RadiogroupSettingSavedState(superState!!)
        ss.descriptionColorValue = descriptionTextColor
        ss.disabledColorValue = disabledTextColor
        ss.titleColorValue = titleTextColor
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as RadiogroupSettingSavedState
        super.onRestoreInstanceState(ss.superState)
        setDescriptionTextColor(ss.descriptionColorValue)
        setDisabledTextColor(ss.disabledColorValue)
        setTitleTextColor(ss.titleColorValue)
    }

    companion object {

        private val TAG = "RadiogroupSetting"

        private fun getInPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}
