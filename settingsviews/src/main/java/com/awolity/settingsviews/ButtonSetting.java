package com.awolity.settingsviews;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

// TODO: background color
// TODO: enabled

public class ButtonSetting extends ConstraintLayout {

    private static final String TAG = "ButtonSetting";
    private static final long ANIMATION_DURATION = 500;
    private TextView labelTextView, descriptionTextView;
    private ImageView iconImageView, checkmarkImageView;
    private FrameLayout clickOverlay;
    private boolean isCheckable;
    private int disabledColor, labelColor, descriptionColor, backgroundColor;

    public ButtonSetting(@NonNull Context context) {
        super(context);
        inflate();
    }

    public ButtonSetting(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ButtonSetting,
                0, 0);

        try {
            disabledColor = a.getColor(R.styleable.ButtonSetting_disabledColor,
                    getResources().getColor(R.color.disabled_text));
            backgroundColor = a.getColor(R.styleable.ButtonSetting_backgroundColor,
                    getResources().getColor(android.R.color.white));
            setBackgroundColor(backgroundColor);


            isCheckable = a.getBoolean(R.styleable.ButtonSetting_isCheckable, false);
            int checkmarkIconResource = a.getResourceId(R.styleable.ButtonSetting_checkmarkDrawableResource,
                    R.drawable.ic_check_circle_black);
            setCheckmark(isCheckable, checkmarkIconResource);

            int iconResource = a.getResourceId(R.styleable.ButtonSetting_iconDrawableResource,
                    R.drawable.ic_placeholder);
            setIconImageView(iconResource);

            labelColor = a.getColor(R.styleable.ButtonSetting_labelColor,
                    getResources().getColor(R.color.text));
            String label = a.getString(R.styleable.ButtonSetting_labelText);
            if (label != null) {
                setLabelTextView(label, labelColor);
            }

            descriptionColor = a.getColor(R.styleable.ButtonSetting_descriptionColor,
                    getResources().getColor(R.color.text));
            String description = a.getString(R.styleable.ButtonSetting_descriptionText);
            setDescriptionTextView(description, descriptionColor);

        } finally {
            a.recycle();
        }
    }

    public ButtonSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    public void setLabel(String labelText) {
        setLabelTextView(labelText, labelColor);
        //invalidateRequestLayout();
    }

    public void setDescription(String descriptionText) {
        setDescriptionTextView(descriptionText, descriptionColor);
        //invalidateRequestLayout();
    }

    public void setIcon(int iconResource) {
        setIconImageView(iconResource);
    }

    public void setCheckmarkIcon(int iconResource) {
        setCheckmark(isCheckable, iconResource);
        //invalidateRequestLayout();
    }

    public void setCheckable(boolean checkable) {
        isCheckable = checkable;
        setCheckmark(checkable);
       // invalidateRequestLayout();
    }

    public boolean isCheckable() {
        return isCheckable;
    }

    public boolean getChecked() {
        return checkmarkImageView.getVisibility() == VISIBLE;
    }

    public void setDisabledColor(int color) {
        disabledColor = color;
        setEnabled(isEnabled());
    }

    public void setLabelColor(int color) {
        labelColor = color;
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
        if (enabled) {
            labelTextView.setTextColor(labelColor);
            descriptionTextView.setTextColor(descriptionColor);
            clickOverlay.setVisibility(View.VISIBLE);
            iconImageView.setAlpha(1f);
            if (isCheckable) {
                checkmarkImageView.setAlpha(1f);
            }
        } else {
            descriptionTextView.setTextColor(disabledColor);
            labelTextView.setTextColor(disabledColor);
            clickOverlay.setVisibility(View.GONE);
            iconImageView.setAlpha(0.5f);
            if (isCheckable) {
                checkmarkImageView.setAlpha(0.5f);
            }
        }
    }

    public void check() {
        if (isCheckable) {
            checkmarkImageView.setScaleX(0.1F);
            checkmarkImageView.setScaleY(0.1F);
            checkmarkImageView.setRotation(60f);
            checkmarkImageView.setVisibility(VISIBLE);
            checkmarkImageView.animate()
                    .scaleY(1f)
                    .scaleX(1f)
                    .rotation(0f)
                    .setInterpolator(new DecelerateInterpolator(1.4f))
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            checkmarkImageView.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
        } else {
            throw new IllegalStateException("ButtonSetting is not checkable");
        }
    }

    public void showCheckmark() {
        if (isCheckable) {
            checkmarkImageView.setVisibility(View.VISIBLE);
        } else {
            throw new IllegalStateException("ButtonSetting is not checkable");
        }
    }

    public void hideCheckmark() {
        if (isCheckable) {
            checkmarkImageView.setVisibility(View.GONE);
        } else {
            throw new IllegalStateException("ButtonSetting is not checkable");
        }
    }

    public void uncheck() {
        if (isCheckable) {
            checkmarkImageView.animate()
                    .scaleY(0.1f)
                    .scaleX(0.1f)
                    .rotation(60f)
                    .setInterpolator(new AccelerateInterpolator(1.4f))
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            checkmarkImageView.setRotation(0f);
                            checkmarkImageView.setVisibility(INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        } else {
            throw new IllegalStateException("ButtonSetting is not checkable");
        }
    }

    private void invalidateRequestLayout() {
        invalidate();
        requestLayout();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_button, this, true);
        labelTextView = findViewById(R.id.tv_label);
        descriptionTextView = findViewById(R.id.tv_desc);
        iconImageView = findViewById(R.id.iv_icon);
        checkmarkImageView = findViewById(R.id.iv_checkmark);
        clickOverlay = findViewById(R.id.fl_click_overlay);
        clickOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSetting.super.callOnClick();
            }
        });
    }

    private void setCheckmark(boolean isCheckable, int iconResource) {
        checkmarkImageView.setImageResource(iconResource);
        if (isCheckable) {
            checkmarkImageView.setVisibility(VISIBLE);
        } else {
            checkmarkImageView.setVisibility(GONE);
        }
    }

    private void setCheckmark(boolean isCheckable) {
        if (isCheckable) {
            checkmarkImageView.setVisibility(VISIBLE);
        } else {
            checkmarkImageView.setVisibility(GONE);
        }
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

    private void setLabelTextView(@NonNull String labelText, int color) {
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
}
