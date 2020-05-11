package com.example.desafio_android_alberto_junior.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.jetbrains.annotations.Contract;

public class EndAnimation implements Animation.AnimationListener {
    public interface InterfaceEndAnimation {
        void onAnimationEnd();
    }

    private InterfaceEndAnimation interfaceEndAnimation;

    @Contract(pure = true)
    public EndAnimation(InterfaceEndAnimation interfaceEndAnimation) {
        this.interfaceEndAnimation = interfaceEndAnimation;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (interfaceEndAnimation != null)
            interfaceEndAnimation.onAnimationEnd();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
