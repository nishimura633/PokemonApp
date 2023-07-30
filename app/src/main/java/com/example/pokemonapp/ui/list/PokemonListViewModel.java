package com.example.pokemonapp.ui.list;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.repository.PokemonRepository;

import java.util.List;

public class PokemonListViewModel extends ViewModel {
    private final MutableLiveData<List<Pokemon>> _pokemonList = new MutableLiveData<>();

    public LiveData<List<Pokemon>> getPokemonList() {
        return _pokemonList;
    }

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    public LiveData<Boolean> getLoading() {
        return _loading;
    }

    public PokemonListViewModel() {
        loadData();
    }

    public void loadData() {
        _loading.setValue(true);
        PokemonRepository repository = new PokemonRepository();
        repository.getPokemonList().thenAccept(pokemonList -> {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    _loading.setValue(false);
                    _pokemonList.setValue(pokemonList);
                }
            });
        });
    }
}
