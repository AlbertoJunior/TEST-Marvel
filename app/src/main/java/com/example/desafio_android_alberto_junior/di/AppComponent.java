package com.example.desafio_android_alberto_junior.di;

import com.example.desafio_android_alberto_junior.MainActivity;
import com.example.desafio_android_alberto_junior.view.character.CharacterDetails;
import com.example.desafio_android_alberto_junior.view.character.CharacterList;
import com.example.desafio_android_alberto_junior.view.comic.ComicDetails;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ModuleProvider.class
})
public interface AppComponent {
    public void inject(MainActivity mainActivity);

    public void inject(CharacterList characterList);

    public void inject(CharacterDetails characterDetails);

    public void inject(ComicDetails comicDetails);
}
