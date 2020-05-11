package com.example.desafio_android_alberto_junior.vm;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.example.desafio_android_alberto_junior.model.Character;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CharacterViewModel extends ViewModel {
    private final int LIMIT_OFFSET = 10;
    private List<Character> characterList;
    private int offset;

    private ObservableField<Character> current;
    private ObservableField<String> messageError;
    private ObservableInt showLoading;
    private ObservableInt showEmpty;
    private ObservableInt showError;
    private ObservableInt showSearchButton;

    public CharacterViewModel() {
        characterList = new ArrayList<>();
        current = new ObservableField<>();
        messageError = new ObservableField<>();
        showLoading = new ObservableInt();
        showEmpty = new ObservableInt();
        showError = new ObservableInt();
        showSearchButton = new ObservableInt();
    }

    public ObservableInt getShowLoading() {
        return showLoading;
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading.set(showLoading ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowEmpty() {
        return showEmpty;
    }

    public void setShowEmpty(boolean showLoading) {
        this.showEmpty.set(showLoading ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError.set(showError ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowSearchButton() {
        return showSearchButton;
    }

    public void setShowSearchButton(boolean showSearchButton) {
        this.showSearchButton.set(showSearchButton ? View.VISIBLE : View.GONE);
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public ObservableField<Character> getCurrent() {
        return current;
    }

    public void setCurrent(Character current) {
        this.current.set(current);
    }

    public ObservableField<String> getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError.set(messageError);
    }

    public void setCurrentByString(String character) {
        setCurrent(new Gson().fromJson(character, Character.class));
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void updateOffset() {
        this.offset = offset >= LIMIT_OFFSET ? 0 : offset + 1;
    }
}
