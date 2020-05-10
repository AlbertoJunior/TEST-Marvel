package com.example.desafio_android_alberto_junior.view.character;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.desafio_android_alberto_junior.MainApplication;
import com.example.desafio_android_alberto_junior.R;
import com.example.desafio_android_alberto_junior.database.Character;
import com.example.desafio_android_alberto_junior.databinding.FragmentListCharacterBinding;
import com.example.desafio_android_alberto_junior.vm.CharacterViewModel;
import com.example.desafio_android_alberto_junior.web.WebClient;
import com.example.desafio_android_alberto_junior.web.character.CharacterDataContainer;
import com.example.desafio_android_alberto_junior.web.character.CharacterDataWrapper;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class CharacterList extends Fragment {
    @Inject
    WebClient webClient;

    @Inject
    CharacterViewModel viewModel;

    private FragmentListCharacterBinding binding;
    private CharacterAdapter characterAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_character, container, false);
        MainApplication.getComponent().inject(this);

        prepararRecyclerView();

        binding.btnRefresh.setOnClickListener(v -> buscarPersonagens());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.includeToolbar.toolbar.setTitle("Personagens");
        binding.setViewModel(viewModel);
        setupInterface();
    }

    private void setupInterface() {
        viewModel.setShowLoading(false);
        viewModel.setShowSearchButton(true);
    }

    private void prepararRecyclerView() {
        if (characterAdapter == null || characterAdapter.getItemCount() == 0)
            characterAdapter = new CharacterAdapter(requireContext(), listener);

        viewModel.setShowEmpty(characterAdapter.getList().isEmpty());
        binding.tvSemConteudo.setVisibility(View.GONE);
        binding.rvPersonagens.setAdapter(characterAdapter);
    }

    private void buscarPersonagens() {
        characterAdapter.clearList();
        viewModel.setShowEmpty(false);
        viewModel.setShowLoading(true);
        viewModel.setShowSearchButton(false);

        webClient.getCharactersEnqueue(20, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    viewModel.setShowSearchButton(true);
                    viewModel.setShowLoading(false);
                    if (characterAdapter.getList().isEmpty())
                        viewModel.setShowEmpty(true);
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    CharacterDataWrapper characterData = gson.fromJson(string, CharacterDataWrapper.class);
                    CharacterDataContainer data = characterData.getData();
                    List<Character> results = data.getResults();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        characterAdapter.setList(results);
                        viewModel.setShowSearchButton(true);
                        viewModel.setShowLoading(false);
                        viewModel.setShowEmpty(false);
                    });
                } else {
                    String format = String.format("Erro ao buscar os personagens -> Código: %s \t Mensagem: %s", response.code(), response.message());
                    Toast.makeText(requireContext(), format, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private CharacterClickListener listener = (character) -> {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            final CharacterDetails fragment = new CharacterDetails();
            final Bundle args = new Bundle();
            final String characterJson = new Gson().toJson(character);
            args.putString("character", characterJson);
            fragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "character_details")
                    .addToBackStack("character_list")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    };
}
