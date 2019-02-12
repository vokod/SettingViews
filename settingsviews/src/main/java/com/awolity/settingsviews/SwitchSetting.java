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

    private TextView labelTextView;
    private TextView descriptionTextView;
    private ImageView iconImageView;
    private Switch aSwitch;
    private int iconResource;
    private String label;
    private String description;

    public SwitchSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public SwitchSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ButtonSetting,
                0, 0);

        try {
            iconResource = a.getResourceId(R.styleable.ButtonSetting_iconDrawableResource,
                    R.drawable.ic_placeholder);
            label = a.getString(R.styleable.ButtonSetting_labelText);
            description = a.getString(R.styleable.ButtonSetting_descriptionText);

            iconImageView.setImageResource(iconResource);
            labelTextView.setText(label);
            setDescriptionTextView(description);
        } finally {
            a.recycle();
        }
    }

    public SwitchSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    @SuppressWarnings("WeakerAccess")
    void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_switch, this, true);
        labelTextView = findViewById(R.id.tv_label);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        aSwitch = findViewById(R.id.sw);
    }

    public void setLabel(String labelText) {
        label = labelText;
        invalidate();
        requestLayout();
    }

    public void setDescription(String descriptionText) {
        setDescriptionTextView(descriptionText);
        invalidate();
        requestLayout();
    }

    private void setDescriptionTextView(String descriptionText) {
        if (descriptionText == null) {
            descriptionTextView.setVisibility(View.GONE);
            labelTextView.setPadding(labelTextView.getPaddingStart(),
                    getInPx(getContext(), 12),
                    labelTextView.getPaddingEnd(),
                    getInPx(getContext(), 12));
            return;
        }
        descriptionTextView.setText(descriptionText);
    }

    public void setIcon(int iconResource) {
        this.iconResource = iconResource;
        invalidate();
        requestLayout();
    }

    private static int getInPx(Context context, @SuppressWarnings("SameParameterValue") int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            labelTextView.setTextColor(getContext().getResources().getColor(R.color.text));
            descriptionTextView.setTextColor(getContext().getResources().getColor(R.color.text));
            iconImageView.setAlpha(1f);
            aSwitch.setEnabled(false);
        } else {
            descriptionTextView.setTextColor(getContext().getResources().getColor(R.color.disabled_text));
            labelTextView.setTextColor(getContext().getResources().getColor(R.color.disabled_text));
            iconImageView.setAlpha(0.5f);
            aSwitch.setEnabled(true);
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
}
