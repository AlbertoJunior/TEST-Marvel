package com.example.desafio_android_alberto_junior.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.desafio_android_alberto_junior.R;
import com.example.desafio_android_alberto_junior.model.Character;
import com.example.desafio_android_alberto_junior.model.Image;

import org.jetbrains.annotations.NotNull;

public class ImageFromURL {
    public final static String IMAGE_PORTRAIT_MEDIUM = "portrait_medium";
    public final static String IMAGE_PORTRAIT_XLARGE = "portrait_xlarge";
    public final static String IMAGE_PORTRAIT_FANTASTIC = "portrait_fantastic";
    public final static int DEFAULT_XLARGE_NOT_AVAILABLE = R.drawable.ic_portrait_xlarge_not_available;

    public static void setupLarge(Context context, ImageView ciImagem, Image image) {
        setupGeneral(context, ciImagem, image, IMAGE_PORTRAIT_XLARGE);
    }

    public static void setupMedium(Context context, ImageView ciImagem, Image image) {
        setupGeneral(context, ciImagem, image, IMAGE_PORTRAIT_MEDIUM);
    }

    public static void setupFantastic(Context context, ImageView ciImagem, Image image) {
        setupGeneral(context, ciImagem, image, IMAGE_PORTRAIT_FANTASTIC);
    }

    private static void setupGeneral(@NotNull Context context, @NotNull ImageView ciImagem, @NotNull Image thumbnail, String size) {
        // verificando se já tem algum drawable ou a imagem está indisponivel
        if (thumbnail.getDrawableImage() != null) {
            ciImagem.setImageDrawable(thumbnail.getDrawableImage());
        } else if (thumbnail.getPath().contains("image_not_available")) {
            ciImagem.setImageResource(DEFAULT_XLARGE_NOT_AVAILABLE);
        } else { // caso não tenha buscado a imagem ainda
            String path = String.format("%s/%s.%s", thumbnail.getPath(), size, thumbnail.getExtension());
            Glide.with(context)
                    .load(path)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // evitando buscar quando desnecessário
                            thumbnail.setDrawableImage(resource);
                            return false;
                        }
                    })
                    .centerCrop()
                    .placeholder(DEFAULT_XLARGE_NOT_AVAILABLE)
                    .into(ciImagem);
        }
    }
}
