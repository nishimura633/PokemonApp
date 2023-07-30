package com.example.pokemonapp.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.databinding.ListRowItemBinding;
import com.example.pokemonapp.model.Pokemon;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {
    private List<Pokemon> pokemonList;
    private OnClickListItemListener onClickListItemListener;

    public PokemonListAdapter(List<Pokemon> pokemonList, OnClickListItemListener onClickListItemListener) {
        this.pokemonList = pokemonList;
        this.onClickListItemListener = onClickListItemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListRowItemBinding binding;

        public ViewHolder(@NonNull ListRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Pokemon pokemon) {
            binding.textNo.setText(String.format("No. %s", pokemon.getOrder()));
            binding.textName.setText(pokemon.getName());
            setImage(pokemon.getImageUrl());
            binding.getRoot().setOnClickListener(v -> onClickListItemListener.click(pokemon));
        }

        private void setImage(String url) {
            Context context = binding.getRoot().getContext();
            Glide.with(context).load(url).into(binding.image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListRowItemBinding binding = ListRowItemBinding.inflate(layoutInflater);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void updateDataSet(List<Pokemon> newPokemonList) {
        if (pokemonList == null) {
            pokemonList = newPokemonList;
            notifyItemRangeChanged(0, newPokemonList.size());
        } else {
            DiffUtil.DiffResult diffCallback = DiffUtil.calculateDiff(new DiffCallback(pokemonList, newPokemonList));
            pokemonList = newPokemonList;
            diffCallback.dispatchUpdatesTo(this);
        }
    }

    public interface OnClickListItemListener {
        void click(Pokemon pokemon);
    }

    static class DiffCallback extends DiffUtil.Callback {
        private final List<Pokemon> oldList;
        private final List<Pokemon> newList;

        public DiffCallback(List<Pokemon> oldList, List<Pokemon> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getOrder() == newList.get(newItemPosition).getOrder();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition) == newList.get(newItemPosition);
        }
    }
}
