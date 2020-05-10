package com.example.desafio_android_alberto_junior.view.character;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.desafio_android_alberto_junior.R;
import com.example.desafio_android_alberto_junior.database.Character;
import com.example.desafio_android_alberto_junior.databinding.ItemCharacterBinding;
import com.example.desafio_android_alberto_junior.utils.ImageFromURL;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterItemHolder> {
    private final Context context;
    private final List<Character> list;
    private final CharacterClickListener listener;

    CharacterAdapter(Context context, CharacterClickListener listener) {
        this.context = context;
        this.list = new ArrayList<>();
        this.listener = listener;
    }

    void setList(List<Character> others) {
        list.clear();
        list.addAll(others);
        notifyDataSetChanged();
    }

    void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    List<Character> getList() {
        return list;
    }

    @NonNull
    @Override
    public CharacterItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_character, parent, false);
        return new CharacterItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterItemHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class CharacterItemHolder extends RecyclerView.ViewHolder {
        private ItemCharacterBinding itemBinding;

        CharacterItemHolder(@NonNull ItemCharacterBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }

        private void bind(Character character) {
            itemBinding.setCharacter(character);
            itemBinding.card.setOnClickListener(v -> listener.onClick(character));
            ImageFromURL.setupLarge(context, itemBinding.ciImagem, character);
        }
    }
}
