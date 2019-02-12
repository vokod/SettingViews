package com.awolity.settingsviews;

import android.content.Context;
import android.content.res.TypedArray;
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
    private int disabledColor, titleColor, descriptionColor, backgroundColor;
    private int seekbarMax, seekbarPosition;

    public SeekbarSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SeekbarSetting,
                0, 0);

        try {
            seekbarMax = a.getInt(R.styleable.SeekbarSetting_max, 100);
            seekbarPosition = a.getInt(R.styleable.SeekbarSetting_progress, 0);
            setSeekBar(seekbarMax, seekbarPosition);


            disabledColor = a.getColor(R.styleable.SeekbarSetting_disabledColor,
                    getResources().getColor(R.color.disabled_text));
            backgroundColor = a.getColor(R.styleable.SeekbarSetting_backgroundColor,
                    getResources().getColor(android.R.color.white));
            setBackgroundColor(backgroundColor);

            int iconResource = a.getResourceId(R.styleable.SeekbarSetting_iconDrawableResource,
                    R.drawable.ic_placeholder);
            setIconImageView(iconResource);

            titleColor = a.getColor(R.styleable.SeekbarSetting_settingTitleTextColor,
                    getResources().getColor(R.color.text));
            String title = a.getString(R.styleable.SeekbarSetting_titleText);
            if (title != null) {
                setTitleTextView(title, titleColor);
            }

            descriptionColor = a.getColor(R.styleable.SeekbarSetting_descriptionTextColor,
                    getResources().getColor(R.color.text));
            String description = a.getString(R.styleable.SeekbarSetting_descriptionText);
            setDescriptionTextView(description, descriptionColor);

        } finally {
            a.recycle();
        }
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    public void setTitle(String titleText) {
        setTitleTextView(titleText, titleColor);
        //invalidateRequestLayout();
    }

    public void setDescription(String descriptionText) {
        setDescriptionTextView(descriptionText, descriptionColor);
        //invalidateRequestLayout();
    }

    public void setIcon(int iconResource) {
        setIconImageView(iconResource);
        //invalidateRequestLayout();
    }

    public void setDisabledColor(int color) {
        disabledColor = color;
        setEnabled(isEnabled());
    }

    public void setTitleTextColor(int color) {
        titleColor = color;
        setEnabled(isEnabled());
    }

    @Override
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        super.setBackgroundColor(color);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        seekBar.setEnabled(enabled);
        if (enabled) {
            titleTextView.setTextColor(titleColor);
            descriptionTextView.setTextColor(descriptionColor);
            iconImageView.setAlpha(1f);
        } else {
            descriptionTextView.setTextColor(disabledColor);
            titleTextView.setTextColor(disabledColor);
            iconImageView.setAlpha(0.5f);
        }
    }

    public void setDescriptionColor(int color) {
        descriptionColor = color;
        setEnabled(isEnabled());
    }

    private void setSeekBar(int max, int pos, SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setMax(max);
        seekBar.setProgress(pos);
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setSeekBar(int max, int position) {
        if (position > max) {
            throw new IllegalArgumentException("Position is bigger then max value");
        }
        seekbarPosition = position;
        seekbarMax = max;
        seekBar.setProgress(position);
    }

    public void setSeekBarPosition(int position) {
        if (position > seekbarMax) {
            throw new IllegalArgumentException("Position is bigger then max value");
        }
        seekbarPosition = position;
        seekBar.setProgress(position);
    }

    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    private void invalidateRequestLayout() {
        invalidate();
        requestLayout();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_seekbar, this, true);
        titleTextView = findViewById(R.id.tv_title);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        seekBar = findViewById(R.id.seekbar);
    }

    private void setDescriptionTextView(String descriptionText, int color) {
        if (descriptionText == null) {
            descriptionTextView.setVisibility(View.GONE);
            titleTextView.setPadding(titleTextView.getPaddingStart(),
                    getInPx(getContext(), 12),
                    titleTextView.getPaddingEnd(),
                    getInPx(getContext(), 12));
            return;
        }
        descriptionTextView.setText(descriptionText);
        if (isEnabled()) {
            descriptionTextView.setTextColor(color);
        } else {
            descriptionTextView.setTextColor(disabledColor);
        }
    }

    private void setTitleTextView(@NonNull String labelText, int color) {
        titleTextView.setText(labelText);
        if (isEnabled()) {
            titleTextView.setTextColor(color);
        } else {
            titleTextView.setTextColor(disabledColor);
        }
    }

    private void setIconImageView(int resourceId) {
        iconImageView.setImageResource(resourceId);
    }

    private static int getInPx(Context context, @SuppressWarnings("SameParameterValue") int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
