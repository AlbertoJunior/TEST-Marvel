package com.example.desafio_android_alberto_junior.view.character;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.desafio_android_alberto_junior.MainApplication;
import com.example.desafio_android_alberto_junior.R;
import com.example.desafio_android_alberto_junior.animation.EndAnimation;
import com.example.desafio_android_alberto_junior.databinding.FragmentDetailsCharacterBinding;
import com.example.desafio_android_alberto_junior.model.Character;
import com.example.desafio_android_alberto_junior.model.Image;
import com.example.desafio_android_alberto_junior.utils.ImageFromURL;
import com.example.desafio_android_alberto_junior.view.comic.ComicDetails;
import com.example.desafio_android_alberto_junior.vm.CharacterViewModel;
import com.example.desafio_android_alberto_junior.web.WebClient;

import javax.inject.Inject;

import static com.example.desafio_android_alberto_junior.utils.ImageFromURL.IMAGE_PORTRAIT_FANTASTIC;

public class CharacterDetails extends Fragment {
    @Inject
    WebClient webClient;
    @Inject
    CharacterViewModel characterViewModel;

    private FragmentDetailsCharacterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_character, container, false);
        MainApplication.getComponent().inject(this);
        binding.btnVoltar.setOnClickListener(v -> navigationBack());
        binding.btnRevistaCara.setOnClickListener(v -> navigationComic());
        return binding.getRoot();
    }

    private void navigationBack() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            binding.getRoot().setVisibility(View.GONE);

            final Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fast_fade_out);
            animation.setAnimationListener(new EndAnimation(fragmentManager::popBackStack));
            binding.getRoot().startAnimation(animation);
        }
    }

    private void navigationComic() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            final ComicDetails fragment = new ComicDetails();
            final Bundle args = new Bundle();
            final Character character = characterViewModel.getCurrent().get();
            if (character != null) {
                args.putInt("character_id", character.getId());
                fragment.setArguments(args);

                final Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fast_fade_out);
                animation.setAnimationListener(
                        new EndAnimation(() -> fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment, "descricao_revista")
                                .addToBackStack("character_details")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit()));
                binding.getRoot().setVisibility(View.GONE);
                binding.getRoot().startAnimation(animation);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.includeToolbar.toolbar.setTitle("Detalhes do Personagem");
        if (getArguments() != null) {
            characterViewModel.setCurrentByString(getArguments().getString("character"));
            setupInterface();
        }
    }

    private void setupInterface() {
        binding.setCharacter(characterViewModel);
        Character character = characterViewModel.getCurrent().get();
        if (character == null)
            return;

        if (character.getDescription().isEmpty())
            character.setDescription("As informações desse personagem foram confiscadas pela S.H.I.E.L.D.");

        searchImage(character);

        final Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        binding.getRoot().startAnimation(animation);
    }

    private void searchImage(Character character) {
        if (character != null) {
            final Image thumbnail = character.getThumbnail();
            final String originalPath = thumbnail.getPath();

            //se já possuir uma imagem seta e retorna
            if (thumbnail.getDrawableImage() != null) {
                binding.ciImagem.setImageDrawable(thumbnail.getDrawableImage());
                binding.ciImagem.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in));
                characterViewModel.setShowLoading(false);
                return;
            }

            // se for uma path inválida seta um default e retorna
            if (originalPath == null || originalPath.isEmpty() || originalPath.contains("image_not_available")) {
                binding.ciImagem.setImageResource(ImageFromURL.DEFAULT_XLARGE_NOT_AVAILABLE);
                binding.ciImagem.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in));
                characterViewModel.setShowLoading(false);
                return;
            }

            characterViewModel.setShowLoading(true);

            // buscando a imagem na web
            String path = String.format("%s/%s.%s", originalPath, IMAGE_PORTRAIT_FANTASTIC, thumbnail.getExtension());
            Glide.with(requireContext())
                    .load(path)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            characterViewModel.setShowLoading(false);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            characterViewModel.setShowLoading(false);
                            final Character auxCharacter = characterViewModel.getCurrent().get();
                            if (auxCharacter != null) {
                                auxCharacter.getThumbnail().setDrawableImage(resource);
                                binding.ciImagem.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in));
                            }
                            return false;
                        }
                    })
                    .placeholder(ImageFromURL.DEFAULT_XLARGE_NOT_AVAILABLE)
                    .into(binding.ciImagem);
        }
    }
}
