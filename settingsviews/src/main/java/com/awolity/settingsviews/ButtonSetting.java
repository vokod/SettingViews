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

public class ButtonSetting extends ConstraintLayout {

    private static final String TAG = "ButtonSetting";
    private static final long ANIMATION_DURATION = 500;
    private TextView titleTextView, descriptionTextView;
    private ImageView iconImageView, checkmarkImageView;
    private FrameLayout clickOverlay;
    private boolean checkable;
    private int disabledColor;
    private int titleTextColor;
    private int descriptionTextColor;

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
            setColorsFromAttributes(a);
            setIconsFromAttributes(a);
            setLabelsFromAttributes(a);


        } finally {
            a.recycle();
        }
    }

    public ButtonSetting(@NonNull Context context, @Nullable AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.setting_button, this, true);
        titleTextView = findViewById(R.id.tv_title);
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

    private void setColorsFromAttributes(TypedArray a) {
        disabledColor = a.getColor(R.styleable.ButtonSetting_disabledColor,
                getResources().getColor(R.color.disabled_text));

        int backgroundColor = a.getColor(R.styleable.ButtonSetting_backgroundColor,
                getResources().getColor(android.R.color.white));
        setBackgroundColor(backgroundColor);

        titleTextColor = a.getColor(R.styleable.ButtonSetting_settingTitleTextColor,
                getResources().getColor(R.color.text));
        titleTextView.setTextColor(titleTextColor);

        descriptionTextColor = a.getColor(R.styleable.ButtonSetting_descriptionTextColor,
                getResources().getColor(R.color.text));
        descriptionTextView.setTextColor(descriptionTextColor);
    }

    private void setIconsFromAttributes(TypedArray a) {
        int iconResource = a.getResourceId(R.styleable.ButtonSetting_iconDrawableResource,
                R.drawable.ic_placeholder);
        setIcon(iconResource);

        checkable = a.getBoolean(R.styleable.ButtonSetting_isCheckable, false);
        int checkmarkIconResource = a.getResourceId(R.styleable.ButtonSetting_checkmarkDrawableResource,
                R.drawable.ic_check_black);
        setCheckmark(checkable, checkmarkIconResource);
    }

    private void setCheckmark(boolean isCheckable, int iconResource) {
        checkmarkImageView.setImageResource(iconResource);
        if (isCheckable) {
            checkmarkImageView.setVisibility(VISIBLE);
        } else {
            checkmarkImageView.setVisibility(GONE);
        }
    }

    private void setLabelsFromAttributes(TypedArray a) {
        String label = a.getString(R.styleable.ButtonSetting_titleText);
        if (label != null) {
            titleTextView.setText(label);
        }

        String description = a.getString(R.styleable.ButtonSetting_descriptionText);
        setDescription(description);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
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

    public void setCheckmarkIcon(int iconResource) {
        checkmarkImageView.setImageResource(iconResource);
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
        if (checkable) {
            checkmarkImageView.setVisibility(VISIBLE);
        } else {
            checkmarkImageView.setVisibility(GONE);
        }
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean getChecked() {
        return checkmarkImageView.getVisibility() == VISIBLE;
    }

    public void setChecked(boolean checked) {
        if (checkable) {
            if (checked) {
                checkmarkImageView.setVisibility(View.VISIBLE);
            } else {
                checkmarkImageView.setVisibility(GONE);
            }
        } else {
            throw new IllegalStateException("ButtonSetting is not checkable");
        }
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
        descriptionTextColor = color;
        setEnabled(isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            titleTextView.setTextColor(titleTextColor);
            descriptionTextView.setTextColor(descriptionTextColor);
            clickOverlay.setVisibility(View.VISIBLE);
            iconImageView.setAlpha(1f);
            checkmarkImageView.setAlpha(1f);
        } else {
            descriptionTextView.setTextColor(disabledColor);
            titleTextView.setTextColor(disabledColor);
            clickOverlay.setVisibility(View.GONE);
            iconImageView.setAlpha(0.5f);
            checkmarkImageView.setAlpha(0.5f);
        }
    }

    public void check() {
        if (checkable) {
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

    public void uncheck() {
        if (checkable) {
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

    private static int getInPx(Context context, @SuppressWarnings("SameParameterValue") int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
