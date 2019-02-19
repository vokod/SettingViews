package com.awolity.settingsviews

import android.animation.Animator
import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor

class ButtonSetting : ConstraintLayout {
    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var checkmarkImageView: ImageView? = null
    private var clickOverlay: FrameLayout? = null
    private var checkable: Boolean = false
    private var disabledTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var descriptionTextColor: Int = 0
    private var iconResource: Int = 0

    var isCheckable: Boolean
        get() = checkable
        set(checkable) {
            this.checkable = checkable
            if (checkable) {
                if (checked) {
                    checkmarkImageView!!.visibility = View.VISIBLE
                    checked = false
                } else {
                    checkmarkImageView!!.visibility = View.INVISIBLE
                }
            } else {
                checkmarkImageView!!.visibility = View.GONE
            }
        }

    var checked: Boolean
        get() = if (checkable) {
            checkmarkImageView!!.visibility == View.VISIBLE
        } else {
            false
        }
        set(checked) {
            if (checkable) {
                if (checked) {
                    checkmarkImageView!!.visibility = View.VISIBLE
                } else {
                    checkmarkImageView!!.visibility = View.INVISIBLE
                }
            }
        }

    constructor(context: Context) : super(context) {
        inflate()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ButtonSetting, 0, 0)
        try {
            setColorsFromAttributes(a)
            setIconsFromAttributes(a)
            setLabelsFromAttributes(a)
            setCheckableFromAttributes(a)
        } finally {
            a.recycle()
        }
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inflate()
    }

    private fun inflate() {
        isSaveEnabled = true
        LayoutInflater.from(context).inflate(R.layout.setting_button, this, true)
        titleTextView = findViewById(R.id.tv_title)
        descriptionTextView = findViewById(R.id.tv_desc)
        iconImageView = findViewById(R.id.iv_icon)
        checkmarkImageView = findViewById(R.id.iv_checkmark)
        clickOverlay = findViewById(R.id.fl_click_overlay)
        clickOverlay!!.setOnClickListener { super@ButtonSetting.callOnClick() }
    }

    private fun setColorsFromAttributes(a: TypedArray) {
        disabledTextColor = a.getColor(
            R.styleable.ButtonSetting_disabledColor,
            getColor(context, R.color.text_disabled)
        )

        titleTextColor = a.getColor(
            R.styleable.ButtonSetting_titleTextColor,
            getColor(context, R.color.text_title)
        )
        titleTextView!!.setTextColor(titleTextColor)

        descriptionTextColor = a.getColor(
            R.styleable.ButtonSetting_descriptionTextColor,
            getColor(context, R.color.text_description)
        )
        descriptionTextView!!.setTextColor(descriptionTextColor)
    }

    private fun setIconsFromAttributes(a: TypedArray) {
        val iconResource = a.getResourceId(
            R.styleable.ButtonSetting_iconDrawableResource,
            R.drawable.ic_android
        )
        setIcon(iconResource)

        checkable = a.getBoolean(R.styleable.ButtonSetting_isCheckable, false)
        val checkmarkIconResource = a.getResourceId(
            R.styleable.ButtonSetting_checkmarkDrawableResource,
            R.drawable.ic_check_black
        )
        setCheckmark(checkable, checkmarkIconResource)
    }

    private fun setCheckableFromAttributes(a: TypedArray) {
        checkable = a.getBoolean(R.styleable.ButtonSetting_isCheckable, false)
        if (checkable) {
            checked = a.getBoolean(R.styleable.ButtonSetting_checked, false)
        }
    }

    private fun setCheckmark(isCheckable: Boolean, iconResource: Int) {
        checkmarkImageView!!.setImageResource(iconResource)
        if (isCheckable) {
            checkmarkImageView!!.visibility = View.VISIBLE
        } else {
            checkmarkImageView!!.visibility = View.GONE
        }
    }

    private fun setLabelsFromAttributes(a: TypedArray) {
        val label = a.getString(R.styleable.ButtonSetting_titleText)
        if (label != null) {
            titleTextView!!.text = label
        }

        val description = a.getString(R.styleable.ButtonSetting_descriptionText)
        setDescription(description)
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

    fun setCheckmarkIcon(iconResource: Int) {
        checkmarkImageView!!.setImageResource(iconResource)
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
        if (enabled) {
            titleTextView!!.setTextColor(titleTextColor)
            descriptionTextView!!.setTextColor(descriptionTextColor)
            clickOverlay!!.visibility = View.VISIBLE
            iconImageView!!.alpha = 1f
            checkmarkImageView!!.alpha = 1f
        } else {
            descriptionTextView!!.setTextColor(disabledTextColor)
            titleTextView!!.setTextColor(disabledTextColor)
            clickOverlay!!.visibility = View.GONE
            iconImageView!!.alpha = 0.5f
            checkmarkImageView!!.alpha = 0.5f
        }
    }

    fun check() {
        if (checkable) {
            checkmarkImageView!!.scaleX = 0.1f
            checkmarkImageView!!.scaleY = 0.1f
            checkmarkImageView!!.rotation = 60f
            checkmarkImageView!!.visibility = View.VISIBLE
            checkmarkImageView!!.animate()
                .scaleY(1f)
                .scaleX(1f)
                .rotation(0f)
                .setInterpolator(DecelerateInterpolator(1.4f))
                .duration = ANIMATION_DURATION
        } else {
            throw IllegalStateException("ButtonSetting is not checkable")
        }
    }

    fun uncheck() {
        if (checkable) {
            checkmarkImageView!!.animate()
                .scaleY(0.1f)
                .scaleX(0.1f)
                .rotation(60f)
                .setInterpolator(AccelerateInterpolator(1.4f))
                .setDuration(ANIMATION_DURATION)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        checkmarkImageView!!.rotation = 0f
                        checkmarkImageView!!.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
        } else {
            throw IllegalStateException("ButtonSetting is not checkable")
        }
    }

    companion object {

        private val TAG = "ButtonSetting"
        private val ANIMATION_DURATION: Long = 500

        private fun getInPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
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
        ss.checkable = checkable
        ss.checked = checked
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
        isCheckable = ss.checkable
        checked = ss.checked
    }
}
