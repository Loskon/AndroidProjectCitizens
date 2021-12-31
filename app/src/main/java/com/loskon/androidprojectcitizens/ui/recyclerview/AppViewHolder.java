package com.loskon.androidprojectcitizens.ui.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.databinding.RowCitizensBinding;
import com.loskon.androidprojectcitizens.model.Citizen;

/**
 * Предоставление доступа View-компонентам
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    private final RowCitizensBinding binding;

    public AppViewHolder(RowCitizensBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Citizen citizen) {
        binding.setCitizen(citizen);
        binding.executePendingBindings();
    }

}