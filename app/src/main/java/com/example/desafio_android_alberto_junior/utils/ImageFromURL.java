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
import com.example.desafio_android_alberto_junior.database.Character;
import com.example.desafio_android_alberto_junior.database.Comic;
import com.example.desafio_android_alberto_junior.database.Image;

import org.jetbrains.annotations.NotNull;

public class ImageFromURL {
    public final static String IMAGE_PORTRAIT_MEDIUM = "portrait_medium";
    public final static String IMAGE_PORTRAIT_XLARGE = "portrait_xlarge";
    public final static String IMAGE_PORTRAIT_FANTASTIC = "portrait_fantastic";
    public final static int DEFAULT_XLARGE_NOT_AVAILABLE = R.drawable.ic_portrait_xlarge_not_available;

    public static void setupLarge(Context context, ImageView ciImagem, Character character) {
        setupGeneral(context, ciImagem, character, IMAGE_PORTRAIT_XLARGE);
    }

    public static void setupFantastic(Context context, ImageView ciImagem, @NotNull Character character) {
        // atribuindo a imagem que já possuia
        if(character.getThumbnail().getDrawableImage() != null)
            ciImagem.setImageDrawable(character.getThumbnail().getDrawableImage());

        // invalidando a imagem para buscar outra
        character.getThumbnail().setDrawableImage(null);
        setupGeneral(context, ciImagem, character, IMAGE_PORTRAIT_FANTASTIC);
    }

    public static void setupFantastic(Context context, ImageView ciImagem, @NotNull Comic comic) {
        // atribuindo a imagem que já possuia
        if(comic.getThumbnail() != null && comic.getThumbnail().getDrawableImage() != null)
            ciImagem.setImageDrawable(comic.getThumbnail().getDrawableImage());
        setupGeneral(context, ciImagem, comic, IMAGE_PORTRAIT_FANTASTIC);
    }

    private static void setupGeneral(Context context, ImageView ciImagem, @NotNull Character character, String size) {
        Image thumbnail = character.getThumbnail();
        // verificando se já tem algum drawable ou a imagem está indisponivel
        if (thumbnail.getPath().contains("image_not_available") || thumbnail.getDrawableImage() != null) {
            if (thumbnail.getDrawableImage() != null)
                ciImagem.setImageDrawable(thumbnail.getDrawableImage());
            else
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
                            character.getThumbnail().setDrawableImage(resource);
                            return false;
                        }
                    })
                    .centerCrop()
                    .placeholder(DEFAULT_XLARGE_NOT_AVAILABLE)
                    .into(ciImagem);
        }
    }

    private static void setupGeneral(Context context, ImageView ciImagem, @NotNull Comic comic, String size) {
        Image thumbnail = comic.getThumbnail();
        // verificando se já tem algum drawable ou a imagem está indisponivel
        final String originalPath = thumbnail.getPath();
        if(originalPath == null || originalPath.isEmpty()) {
            ciImagem.setImageResource(DEFAULT_XLARGE_NOT_AVAILABLE);
            return;
        }

        if (originalPath.contains("image_not_available") || thumbnail.getDrawableImage() != null) {
            if (thumbnail.getDrawableImage() != null)
                ciImagem.setImageDrawable(thumbnail.getDrawableImage());
            else
                ciImagem.setImageResource(DEFAULT_XLARGE_NOT_AVAILABLE);
        } else { // caso não tenha buscado a imagem ainda
            String path = String.format("%s/%s.%s", originalPath, size, thumbnail.getExtension());
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
                            comic.getThumbnail().setDrawableImage(resource);
                            return false;
                        }
                    })
                    .centerCrop()
                    .placeholder(DEFAULT_XLARGE_NOT_AVAILABLE)
                    .into(ciImagem);
        }
    }
}
