package com.awolity.settingsviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SeekbarSetting extends ConstraintLayout {

    private TextView labelTextView;
    private TextView descriptionTextView;
    private ImageView iconImageView;
    private SeekBar seekBar;

    public SeekbarSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();
    }

    public SeekbarSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    @SuppressWarnings("WeakerAccess")
    void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_seekbar, this, true);
        labelTextView = findViewById(R.id.tv_label);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        seekBar = findViewById(R.id.seekbar);
    }

    private void setLabel(String labelText) {
        labelTextView.setText(labelText);
    }

    @SuppressWarnings("WeakerAccess")
    public void setDescription(String valueText) {
        descriptionTextView.setText(valueText);
    }

    private void setIcon(int resId) {
        iconImageView.setImageResource(resId);
    }

    private void setSeekBar(int max, int pos, SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setMax(max);
        seekBar.setProgress(pos);
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setup(String label, String description, int iconResource, int max, int pos,
                      SeekBar.OnSeekBarChangeListener listener) {
        setLabel(label);
        setDescription(description);
        setIcon(iconResource);
        setSeekBar(max, pos, listener);
    }

    public void setSeekBarPosition(int position){
        seekBar.setProgress(position);
    }
}
