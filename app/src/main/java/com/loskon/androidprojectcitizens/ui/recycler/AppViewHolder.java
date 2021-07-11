package com.loskon.androidprojectcitizens.ui.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.databinding.ItemCitizensBinding;
import com.loskon.androidprojectcitizens.model.Citizen;

/**
 * Предоставление доступа View-компонентам
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    private final ItemCitizensBinding binding;

    public AppViewHolder(ItemCitizensBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Citizen citizen) {
        binding.setCitizen(citizen);
        binding.executePendingBindings();
    }

}