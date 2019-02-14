package com.awolity.settingsviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

// TODO: listenert megadni xml-ből?

public class SeekbarSetting extends ConstraintLayout {

    private TextView titleTextView, descriptionTextView;
    private ImageView iconImageView;
    private SeekBar seekBar;
    private int disabledTextColor, titleTextColor, descriptionTextColor, max, position;
    private String exceptionText = "Position is bigger then max value";

    public SeekbarSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekbarSetting, 0, 0);
        try {
            setColorsFromAttributes(a);
            setIconFromAttributes(a);
            setLabelsFromAttributes(a);
            setSeekBarFromAttributes(a);
        } finally {
            a.recycle();
        }
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_seekbar, this, true);
        titleTextView = findViewById(R.id.tv_title);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        seekBar = findViewById(R.id.seekbar);
    }

    private void setColorsFromAttributes(TypedArray a) {
        disabledTextColor = a.getColor(R.styleable.SeekbarSetting_disabledColor,
                getResources().getColor(R.color.text_disabled));

        titleTextColor = a.getColor(R.styleable.SeekbarSetting_titleTextColor,
                getResources().getColor(R.color.text));
        titleTextView.setTextColor(titleTextColor);

        descriptionTextColor = a.getColor(R.styleable.SeekbarSetting_descriptionTextColor,
                getResources().getColor(R.color.text));
        descriptionTextView.setTextColor(descriptionTextColor);
    }

    private void setIconFromAttributes(TypedArray a) {
        int iconResource = a.getResourceId(R.styleable.SeekbarSetting_iconDrawableResource,
                R.drawable.ic_placeholder);
        setIcon(iconResource);
    }

    private void setLabelsFromAttributes(TypedArray a) {
        String label = a.getString(R.styleable.SeekbarSetting_titleText);
        if (label != null) {
            titleTextView.setText(label);
        }

        String description = a.getString(R.styleable.SeekbarSetting_descriptionText);
        setDescription(description);
    }

    private void setSeekBarFromAttributes(TypedArray a) {
        max = a.getInt(R.styleable.SeekbarSetting_max, 100);
        position = a.getInt(R.styleable.SeekbarSetting_progress, 0);
        setSeekBar(max, position);
    }

    public void setTitle(String titleText) {
        titleTextView.setText(titleText);
    }

    public void setDescription(String descriptionText) {
        if (descriptionText == null) {
            descriptionTextView.setVisibility(View.GONE);
            titleTextView.setPadding(titleTextView.getPaddingStart(),
                    getInPx(getContext(), 12),
                    titleTextView.getPaddingEnd(),
                    getInPx(getContext(), 12));
            return;
        }
        descriptionTextView.setText(descriptionText);
    }

    public void setIcon(int iconResource) {
        iconImageView.setImageResource(iconResource);
    }

    public void setDisabledTextColor(int color) {
        disabledTextColor = color;
        setEnabled(isEnabled());
    }

    public void setTitleTextColor(int color) {
        titleTextColor = color;
        setEnabled(isEnabled());
    }

    public void setDescriptionTextColor(int color) {
        descriptionTextColor = color;
        setEnabled(isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        seekBar.setEnabled(enabled);
        if (enabled) {
            titleTextView.setTextColor(titleTextColor);
            descriptionTextView.setTextColor(descriptionTextColor);
            iconImageView.setAlpha(1f);
        } else {
            descriptionTextView.setTextColor(disabledTextColor);
            titleTextView.setTextColor(disabledTextColor);
            iconImageView.setAlpha(0.5f);
        }
    }

    public void setSeekBar(int max, int position) {
        if (position > max) {
            throw new IllegalArgumentException(exceptionText);
        }
        this.position = position;
        this.max = max;
        seekBar.setProgress(position);
    }

    public void setSeekBar(int max, int position, SeekBar.OnSeekBarChangeListener listener) {
        setSeekBar(max, position);
        setSeekBarListener(listener);
    }

    public void setSeekBarPosition(int position) {
        if (position > max) {
            throw new IllegalArgumentException(exceptionText);
        }
        this.position = position;
        seekBar.setProgress(position);
    }

    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    private static int getInPx(Context context, @SuppressWarnings("SameParameterValue") int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SeekbarSettingSavedState ss = new SeekbarSettingSavedState(superState);
        ss.setDescriptionColorValue(descriptionTextColor);
        ss.setDisabledColorValue(disabledTextColor);
        ss.setTitleColorValue(titleTextColor);
        ss.setMax(max);
        ss.setPosition(position);
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SeekbarSettingSavedState ss = (SeekbarSettingSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setDescriptionTextColor(ss.getDescriptionColorValue());
        setDisabledTextColor(ss.getDisabledColorValue());
        setTitleTextColor(ss.getTitleColorValue());
        setSeekBar(ss.getMax(), ss.getPosition());
    }
}
