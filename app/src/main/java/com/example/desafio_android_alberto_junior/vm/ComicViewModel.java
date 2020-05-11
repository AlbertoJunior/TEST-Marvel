package com.example.desafio_android_alberto_junior.vm;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.example.desafio_android_alberto_junior.model.Comic;
import com.example.desafio_android_alberto_junior.model.ComicPrice;
import com.example.desafio_android_alberto_junior.model.Image;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComicViewModel extends ViewModel {
    private int characterId;
    private List<Comic> comicList;

    private ObservableField<Comic> current;
    private ObservableField<Drawable> image;
    private ObservableField<String> messageError;

    private ObservableInt showLoading;
    private ObservableInt showCurrent;
    private ObservableInt showError;
    private ObservableInt showButtonRefresh;
    private ObservableBoolean listFinish;

    public ComicViewModel() {
        comicList = new ArrayList<>();
        showLoading = new ObservableInt();
        showCurrent = new ObservableInt();
        showError = new ObservableInt();
        showButtonRefresh = new ObservableInt();
        current = new ObservableField<>();
        messageError = new ObservableField<>();
        image = new ObservableField<>();
        listFinish = new ObservableBoolean();

        currentListener();
        imageListener();
        listFinisListener();
    }

    private void currentListener() {
        current.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (current.get() != null)
                    setShowCurrent(true);
            }
        });
    }

    private void listFinisListener() {
        listFinish.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (listFinish.get()) {
                    final Comic current = searchMostValuable();
                    //se não encontrar a que não vale mais, é pq não existe nenhuma
                    if (current == null) {
                        setMessageError("HQs confiscadas pela S.H.I.E.L.D.");
                        setShowError(true);
                    } else {
                        setCurrent(current);
                        setShowError(false);
                    }

                    setShowLoading(false);
                }
            }
        });
    }

    private void imageListener() {
        image.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                final Comic comic = current.get();
                final Drawable drawable = image.get();
                // se setar uma imagem válida
                if (drawable != null && comic != null) {
                    final Image thumbnail = comic.getThumbnail();
                    // verifica se a comic já possui, caso não possua seta para não buscar novamente
                    if (thumbnail != null && thumbnail.getDrawableImage() == null)
                        thumbnail.setDrawableImage(drawable);
                }
            }
        });
    }

    public ObservableBoolean getListFinish() {
        return listFinish;
    }

    public void setListFinish(boolean listFinish) {
        this.listFinish.set(listFinish);
    }

    public ObservableField<String> getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError.set(messageError);
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    public List<Comic> getComicList() {
        return comicList;
    }

    public ObservableInt getShowLoading() {
        return showLoading;
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading.set(showLoading ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowCurrent() {
        return showCurrent;
    }

    public void setShowCurrent(boolean showCurrent) {
        this.showCurrent.set(showCurrent ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError.set(showError ? View.VISIBLE : View.GONE);
    }

    public ObservableInt getShowButtonRefresh() {
        return showButtonRefresh;
    }

    public void setShowButtonRefresh(boolean showButtonRefresh) {
        this.showButtonRefresh.set(showButtonRefresh ? View.VISIBLE : View.GONE);
    }

    public ObservableField<Comic> getCurrent() {
        return current;
    }

    public void setCurrent(Comic current) {
        this.current.set(current);
    }

    public void setCurrentByString(String character) {
        setCurrent(new Gson().fromJson(character, Comic.class));
    }

    public ObservableField<Drawable> getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image.set(image);
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public Comic searchMostValuable() {
        final Comic[] mostValuable = {null};

        if (getComicList().isEmpty())
            return null;

        mostValuable[0] = getComicList().get(0);
        final float[] price = {0};
        getComicList().forEach(comic ->
                comic.getPrices().forEach(p -> {
                    if (p.getPrice() > price[0]) {
                        comic.setAuxPrice(p.getPrice());
                        mostValuable[0] = comic;
                        price[0] = p.getPrice();
                    }
                }));

        return mostValuable[0];
    }

}
