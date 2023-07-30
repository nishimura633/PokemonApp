package com.example.pokemonapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.pokemonapp.R;
import com.example.pokemonapp.databinding.FragmentPokemonListBinding;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.ui.detail.PokemonDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class PokemonListFragment extends Fragment {

    private FragmentPokemonListBinding binding;
    private PokemonListAdapter pokemonListAdapter;
    private PokemonListViewModel viewModel;

    public static PokemonListFragment newInstance() {
        return new PokemonListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PokemonListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
        initRecyclerView();
    }

    private void initRecyclerView() {
        pokemonListAdapter = new PokemonListAdapter(new ArrayList<>(), new PokemonListItemClickListener());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(layoutManager);
        binding.list.setAdapter(pokemonListAdapter);
    }

    private void setObserver() {
        viewModel.getPokemonList().observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                updateDataSet(pokemons);
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int view = isLoading ? View.VISIBLE : View.INVISIBLE;
                binding.progressBar.setVisibility(view);
            }
        });
    }

    private void updateDataSet(List<Pokemon> pokemonList) {
        pokemonListAdapter.updateDataSet(pokemonList);
    }

    class PokemonListItemClickListener implements PokemonListAdapter.OnClickListItemListener {

        @Override
        public void click(Pokemon pokemon) {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragment_container_view, PokemonDetailFragment.newInstance(pokemon))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
