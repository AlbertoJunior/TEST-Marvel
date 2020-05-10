package com.example.desafio_android_alberto_junior.di;

import com.example.desafio_android_alberto_junior.vm.CharacterViewModel;
import com.example.desafio_android_alberto_junior.vm.ComicViewModel;
import com.example.desafio_android_alberto_junior.web.WebClient;

import javax.inject.Singleton;

import dagger.Provides;

@dagger.Module
public class ModuleProvider {
    @Provides
    @Singleton
    public WebClient webClientProvide(){
        return new WebClient();
    }

    @Provides
    public CharacterViewModel characterViewModel() {
        return new CharacterViewModel();
    }

    @Provides
    public ComicViewModel comicViewModel() {
        return new ComicViewModel();
    }
}
