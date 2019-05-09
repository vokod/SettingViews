package com.awolity.settingviews

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
import androidx.core.content.ContextCompat.getColor

// TODO: valami enumot, vagy inline class-t a selected value-hoz
class RadiogroupSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RadiogroupSetting, 0, 0)
        try {
            setColorsFromAttributes(a)
            setIconFromAttributes(a)
            setLabelsFromAttributes(a)
            setSelectedRadiobuttonFromAttributes(a)
        } finally {
            a.recycle()
        }
    }

    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var firstButton: RadioButton? = null
    private var secondButton: RadioButton? = null
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0
    private var radioButtonLabelTextColor: Int = 0
    private var iconResource: Int = 0

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
            getColor(context, R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.RadiogroupSetting_titleTextColor,
            getColor(context, R.color.text_title)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.RadiogroupSetting_descriptionTextColor,
            getColor(context, R.color.text_description)
        )
        descriptionTextView!!.setTextColor(descriptionTextColor)

        radioButtonLabelTextColor = a.getColor(
            R.styleable.RadiogroupSetting_radioButtonLabelTextColor,
            getColor(context, R.color.text_description)
        )
        setRadioButtonLabelColor(radioButtonLabelTextColor)
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

    private fun setSelectedRadiobuttonFromAttributes(a:TypedArray){
        val selectedButton = a.getInt(R.styleable.RadiogroupSetting_selected, 0)
        setSelectedRadioButton(selectedButton)
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
        this.iconResource = iconResource
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

    // TODO: listenert megadni attribútumból

    fun setListener(listener: RadiogroupSettingListener) {
        firstButton!!.setOnClickListener { listener.OnRadioButtonClicked(0) }
        secondButton!!.setOnClickListener { listener.OnRadioButtonClicked(1) }
    }

    fun setSelectedRadioButton(selectedButton: Int) {
        if (selectedButton == FIRST_BUTTON) {
            firstButton!!.isChecked = true
            secondButton!!.isChecked = false
        } else if (selectedButton == SECOND_BUTTON) {
            firstButton!!.isChecked = false
            secondButton!!.isChecked = true
        } else {
            throw IllegalArgumentException(context.getString(R.string.radiogroup_setting_illegal_selected))
        }
    }

    fun getSelectedRadioButton(): Int {
        return if (firstButton!!.isChecked) FIRST_BUTTON else SECOND_BUTTON
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
            firstButton!!.setTextColor(radioButtonLabelTextColor)
            secondButton!!.setTextColor(radioButtonLabelTextColor)
            iconImageView!!.alpha = 1f
        } else {
            descriptionTextView!!.setTextColor(disabledTextColor)
            titleTextView!!.setTextColor(disabledTextColor)
            firstButton!!.setTextColor(disabledTextColor)
            secondButton!!.setTextColor(disabledTextColor)
            iconImageView!!.alpha = 0.5f
        }
    }

    interface RadiogroupSettingListener {
        fun OnRadioButtonClicked(selected: Int)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SettingSavedState(superState!!)
        ss.descriptionColorValue = descriptionTextColor
        ss.disabledColorValue = disabledTextColor
        ss.titleColorValue = titleTextColor
        ss.titleText = titleTextView!!.text.toString()
        ss.descriptionText = descriptionTextView!!.text.toString()
        ss.radiobutton1Text = firstButton!!.text.toString()
        ss.radiobutton2Text = secondButton!!.text.toString()
        ss.icon = iconResource
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SettingSavedState
        super.onRestoreInstanceState(ss.superState)
        setDescriptionTextColor(ss.descriptionColorValue)
        setDisabledTextColor(ss.disabledColorValue)
        setTitleTextColor(ss.titleColorValue)
        setTitle(ss.titleText)
        setDescription(ss.descriptionText)
        setRadioButtonsLabel(ss.radiobutton1Text,ss.radiobutton2Text)
        setIcon(ss.icon)
    }

    companion object {
        private val TAG = "RadiogroupSetting"
        const val FIRST_BUTTON = 0
        const val SECOND_BUTTON = 1

        private fun getInPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}
