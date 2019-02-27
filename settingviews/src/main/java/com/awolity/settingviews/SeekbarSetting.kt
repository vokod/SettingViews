package com.awolity.settingviews

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor

// TODO: listenert megadni xml-ből?

class SeekbarSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var seekBar: SeekBar? = null
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0
    private var iconResource: Int = 0

   init {
        inflate()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SeekbarSetting, 0, 0)
        try {
            setColorsFromAttributes(a)
            setIconFromAttributes(a)
            setLabelsFromAttributes(a)
            setSeekBarFromAttributes(a)
        } finally {
            a.recycle()
        }
    }

    private fun inflate() {
        isSaveEnabled = true
        LayoutInflater.from(context).inflate(R.layout.setting_seekbar, this, true)
        titleTextView = findViewById(R.id.tv_title)
        descriptionTextView = findViewById(R.id.tv_desc)
        iconImageView = findViewById(R.id.iv_icon)
        seekBar = findViewById(R.id.seekbar)
    }

    private fun setColorsFromAttributes(a: TypedArray) {
        disabledTextColor = a.getColor(
            R.styleable.SeekbarSetting_disabledColor,
            getColor(context, R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.SeekbarSetting_titleTextColor,
            getColor(context, R.color.text_title)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.SeekbarSetting_descriptionTextColor,
            getColor(context, R.color.text_description)
        )
        descriptionTextView!!.setTextColor(descriptionTextColor)
    }

    private fun setIconFromAttributes(a: TypedArray) {
        val iconResource = a.getResourceId(
            R.styleable.SeekbarSetting_iconDrawableResource,
            R.drawable.ic_android
        )
        setIcon(iconResource)
    }

    private fun setLabelsFromAttributes(a: TypedArray) {
        val label = a.getString(R.styleable.SeekbarSetting_titleText)
        if (label != null) {
            titleTextView!!.text = label
        }

        val description = a.getString(R.styleable.SeekbarSetting_descriptionText)
        setDescription(description)
    }

    private fun setSeekBarFromAttributes(a: TypedArray) {
        val max = a.getInt(R.styleable.SeekbarSetting_seekbarMax, 100)
        val position = a.getInt(R.styleable.SeekbarSetting_seekbarProgress, 0)
        setSeekBar(max, position)
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
        this.iconResource = iconResource
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
        seekBar!!.isEnabled = enabled
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

    fun setSeekBar(max: Int, position: Int) {
        if (position > max) {
            throw IllegalArgumentException(exceptionText)
        }
        seekBar!!.progress = position
    }

    fun getMax(): Int {
        return seekBar!!.max
    }

    fun getPosition(): Int {
        return seekBar!!.progress
    }

    fun setSeekBarListener(listener: SeekBar.OnSeekBarChangeListener) {
        seekBar!!.setOnSeekBarChangeListener(listener)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SettingSavedState(superState!!)
        ss.descriptionColorValue = descriptionTextColor
        ss.disabledColorValue = disabledTextColor
        ss.titleColorValue = titleTextColor
        ss.titleText = titleTextView!!.text.toString()
        ss.descriptionText = descriptionTextView!!.text.toString()
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
        setIcon(ss.icon)
    }

    companion object {

        private const val TAG = "SeekbarSetting"
        private const val exceptionText = "Position is bigger then max value"

        private fun getInPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}
