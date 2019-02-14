package com.awolity.settingsviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RadiogroupSetting extends ConstraintLayout {

    private static final String TAG = "RadiogroupSetting";
    private TextView labelTextView, descriptionTextView;
    private ImageView iconImageView;
    private RadioButton firstButton, secondButton;
    private int disabledColor, titleColor, descriptionColor,  radioButtonLabelColor;

    public RadiogroupSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public RadiogroupSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RadiogroupSetting,
                0, 0);

        try {
            disabledColor = a.getColor(R.styleable.RadiogroupSetting_disabledColor,
                    getResources().getColor(R.color.text_disabled));

            int iconResource = a.getResourceId(R.styleable.RadiogroupSetting_iconDrawableResource,
                    R.drawable.ic_placeholder);
            setIconImageView(iconResource);

            titleColor = a.getColor(R.styleable.RadiogroupSetting_titleTextColor,
                    getResources().getColor(R.color.text));
            String label = a.getString(R.styleable.RadiogroupSetting_titleText);
            if (label != null) {
                setTitleTextView(label, titleColor);
            }

            descriptionColor = a.getColor(R.styleable.RadiogroupSetting_descriptionTextColor,
                    getResources().getColor(R.color.text));
            String description = a.getString(R.styleable.RadiogroupSetting_descriptionText);
            setDescriptionTextView(description, descriptionColor);

            radioButtonLabelColor = a.getColor(R.styleable.RadiogroupSetting_radioButtonLabelTextColor,
                    getResources().getColor(R.color.text));
            String labelFirstRadioButtonLabel = a.getString(R.styleable.RadiogroupSetting_firstRadioButtonText);
            String labelSecondRadioButtonLabel = a.getString(R.styleable.RadiogroupSetting_secondRadioButtonText);
            setRadioButtonsLabel(labelFirstRadioButtonLabel, labelSecondRadioButtonLabel);
            setSelectedRadioButton(true);
            setRadioButtonLabelColor(radioButtonLabelColor);

        } finally {
            a.recycle();
        }
    }

    public RadiogroupSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    public void setTitle(String labelText) {
        setTitleTextView(labelText, titleColor);
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

    public void setRadioButtonLabelColor(int color) {
        radioButtonLabelColor = color;
        //invalidateRequestLayout();
    }

    public void setRadioButtonsLabel(@Nullable String label1, @Nullable String label2) {
        if (label1 != null)
            firstButton.setText(label1);
        if (label2 != null)
            secondButton.setText(label2);
    }

    public void setListener(@NonNull final RadiogroupSettingListener listener) {
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnRadioButtonClicked(0);
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnRadioButtonClicked(1);
            }
        });
    }

    public void setSelectedRadioButton(boolean firstSelected) {
        firstButton.setChecked(firstSelected);
        secondButton.setChecked(!firstSelected);
    }

    public void setDisabledColor(int color) {
        disabledColor = color;
        setEnabled(isEnabled());
    }

    public void setTitleTextColor(int color) {
        titleColor = color;
        setEnabled(isEnabled());
    }

    public void setDescriptionColor(int color) {
        descriptionColor = color;
        setEnabled(isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        firstButton.setEnabled(enabled);
        secondButton.setEnabled(enabled);
        if (enabled) {
            labelTextView.setTextColor(titleColor);
            descriptionTextView.setTextColor(descriptionColor);
            iconImageView.setAlpha(1f);
        } else {
            descriptionTextView.setTextColor(disabledColor);
            labelTextView.setTextColor(disabledColor);
            iconImageView.setAlpha(0.5f);
        }
    }

    private void invalidateRequestLayout() {
        invalidate();
        requestLayout();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_radiogroup, this, true);
        labelTextView = findViewById(R.id.tv_title);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        firstButton = findViewById(R.id.rb_one);
        secondButton = findViewById(R.id.rb_two);
    }

    private void setDescriptionTextView(String descriptionText, int color) {
        if (descriptionText == null) {
            descriptionTextView.setVisibility(View.GONE);
            labelTextView.setPadding(labelTextView.getPaddingStart(),
                    getInPx(getContext(), 12),
                    labelTextView.getPaddingEnd(),
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
        labelTextView.setText(labelText);
        if (isEnabled()) {
            labelTextView.setTextColor(color);
        } else {
            labelTextView.setTextColor(disabledColor);
        }
    }

    private void setIconImageView(int resourceId) {
        iconImageView.setImageResource(resourceId);
    }

    private static int getInPx(Context context, @SuppressWarnings("SameParameterValue") int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public interface RadiogroupSettingListener {
        void OnRadioButtonClicked(int selected);
    }

}
