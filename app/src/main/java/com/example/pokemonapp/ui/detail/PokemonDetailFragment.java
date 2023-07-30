package com.example.pokemonapp.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.databinding.FragmentPokemonDetailBinding;
import com.example.pokemonapp.model.Pokemon;

public class PokemonDetailFragment extends Fragment {

    public static String KEY_ARG = "KEY_ARG_POKEMON_DETAIL_FRAGMENT";
    private FragmentPokemonDetailBinding binding;

    @Nullable
    private Pokemon pokemon;

    public static PokemonDetailFragment newInstance(Pokemon pokemon) {
        PokemonDetailFragment pokemonDetailFragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ARG, pokemon);
        pokemonDetailFragment.setArguments(args);
        return pokemonDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable(KEY_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (pokemon != null) {
            binding.textNo.setText(String.format("No. %s", pokemon.getOrder()));
            binding.textName.setText(pokemon.getName());
            setImage(pokemon.getImageUrl());
        }
    }

    private void setImage(String url) {
        Context context = binding.getRoot().getContext();
        Glide.with(context).load(url).into(binding.image);
    }
}
