package com.awolity.settingsviews

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

// TODO: listenert megadni xml-ből?

class SeekbarSetting : ConstraintLayout {

    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var seekBar: SeekBar? = null
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0
    private var max: Int = 0
    private var position: Int = 0

    constructor(context: Context) : super(context) {
        inflate()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
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

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate()
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
            resources.getColor(R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.SeekbarSetting_titleTextColor,
            resources.getColor(R.color.text_description)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.SeekbarSetting_descriptionTextColor,
            resources.getColor(R.color.text_description)
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
        max = a.getInt(R.styleable.SeekbarSetting_max, 100)
        position = a.getInt(R.styleable.SeekbarSetting_progress, 0)
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
        this.position = position
        this.max = max
        seekBar!!.progress = position
    }

    fun setSeekBarListener(listener: SeekBar.OnSeekBarChangeListener) {
        seekBar!!.setOnSeekBarChangeListener(listener)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SeekbarSettingSavedState(superState!!)
        ss.descriptionColorValue = descriptionTextColor
        ss.disabledColorValue = disabledTextColor
        ss.titleColorValue = titleTextColor
        ss.max = max
        ss.position = position
        return ss
        // TODO: tesztelni a radiobuttonokat
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SeekbarSettingSavedState
        super.onRestoreInstanceState(ss.superState)
        setDescriptionTextColor(ss.descriptionColorValue)
        setDisabledTextColor(ss.disabledColorValue)
        setTitleTextColor(ss.titleColorValue)
        setSeekBar(ss.max, ss.position)
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
