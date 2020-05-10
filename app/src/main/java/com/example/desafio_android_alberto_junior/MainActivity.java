package com.example.desafio_android_alberto_junior;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.desafio_android_alberto_junior.view.character.CharacterList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDagger();
        setContentView(R.layout.activity_main);

        supportFragmentManager = getSupportFragmentManager();
        listarPersonagens();
    }

    private void listarPersonagens() {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, new CharacterList(), "character_list")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void initDagger() {
        MainApplication.getComponent().inject(this);
    }
}
