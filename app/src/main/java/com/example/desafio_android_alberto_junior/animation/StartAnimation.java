package com.example.desafio_android_alberto_junior.animation;

import android.view.animation.Animation;

import org.jetbrains.annotations.Contract;

public class StartAnimation implements Animation.AnimationListener {
    public interface InterfaceStartAnimation {
        void onAnimationEnd();
    }

    private InterfaceStartAnimation interfaceStartAnimation;

    @Contract(pure = true)
    public StartAnimation(InterfaceStartAnimation interfaceStartAnimation) {
        this.interfaceStartAnimation = interfaceStartAnimation;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (interfaceStartAnimation != null)
            interfaceStartAnimation.onAnimationEnd();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
