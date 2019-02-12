package com.awolity.settingsviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SwitchSetting extends ConstraintLayout {

    private TextView titleTextView, descriptionTextView;
    private ImageView iconImageView;
    private Switch aSwitch;
    private int disabledColor, titleTextColor, descriptionColor, backgroundColor;

    public SwitchSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public SwitchSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SwitchSetting,
                0, 0);

        try {
            disabledColor = a.getColor(R.styleable.SwitchSetting_disabledColor,
                    getResources().getColor(R.color.disabled_text));
            backgroundColor = a.getColor(R.styleable.SwitchSetting_backgroundColor,
                    getResources().getColor(android.R.color.white));
            setBackgroundColor(backgroundColor);

            int iconResource = a.getResourceId(R.styleable.SwitchSetting_iconDrawableResource,
                    R.drawable.ic_placeholder);
            setIconImageView(iconResource);

            titleTextColor = a.getColor(R.styleable.SwitchSetting_settingTitleTextColor,
                    getResources().getColor(R.color.text));
            String labelText = a.getString(R.styleable.SwitchSetting_titleText);
            if (labelText != null) {
                setLabelTextView(labelText, titleTextColor);
            }

            descriptionColor = a.getColor(R.styleable.SwitchSetting_descriptionTextColor,
                    getResources().getColor(R.color.text));
            String descriptionText = a.getString(R.styleable.SwitchSetting_descriptionText);
            setDescriptionTextView(descriptionText, descriptionColor);
        } finally {
            a.recycle();
        }
    }

    public SwitchSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }


    public void setTitle(String labelText) {
        setLabelTextView(labelText, titleTextColor);
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
        titleTextColor = color;
        setEnabled(isEnabled());
    }

    public void setDescriptionColor(int color) {
        descriptionColor = color;
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
        aSwitch.setEnabled(enabled);
        if (enabled) {
            titleTextView.setTextColor(titleTextColor);
            descriptionTextView.setTextColor(descriptionColor);
            iconImageView.setAlpha(1f);
        } else {
            descriptionTextView.setTextColor(disabledColor);
            titleTextView.setTextColor(disabledColor);
            iconImageView.setAlpha(0.5f);
        }
    }

    public void setChecked(boolean checked){
        aSwitch.setChecked(checked);
    }

    public boolean getChecked(){
       return  aSwitch.isChecked();
    }

    public void setOnCheckedChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangedListener){
        aSwitch.setOnCheckedChangeListener(onCheckedChangedListener);
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_switch, this, true);
        titleTextView = findViewById(R.id.tv_title);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        aSwitch = findViewById(R.id.sw);
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

    private void setLabelTextView(@NonNull String labelText, int color) {
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
