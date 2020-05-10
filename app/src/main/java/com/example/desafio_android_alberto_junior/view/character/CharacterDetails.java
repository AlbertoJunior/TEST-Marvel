package com.example.desafio_android_alberto_junior.view.character;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.desafio_android_alberto_junior.MainApplication;
import com.example.desafio_android_alberto_junior.R;
import com.example.desafio_android_alberto_junior.database.Character;
import com.example.desafio_android_alberto_junior.databinding.FragmentDetailsCharacterBinding;
import com.example.desafio_android_alberto_junior.utils.ImageFromURL;
import com.example.desafio_android_alberto_junior.view.comic.ComicDetails;
import com.example.desafio_android_alberto_junior.vm.CharacterViewModel;
import com.example.desafio_android_alberto_junior.web.WebClient;

import javax.inject.Inject;

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
        if (fragmentManager != null)
            fragmentManager.popBackStack();
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
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "descricao_revista")
                        .addToBackStack("character_details")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
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
        Character character = characterViewModel.getCurrent().get();
        if (character == null)
            return;

        if (character.getDescription().isEmpty())
            character.setDescription("As informações desse personagem foram confiscadas pela S.H.I.E.L.D.");

        binding.setCharacter(character);
        ImageFromURL.setupFantastic(requireContext(), binding.ciImagem, character);
    }
}
