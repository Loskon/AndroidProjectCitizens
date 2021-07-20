package com.loskon.androidprojectcitizens.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.databinding.ItemCitizensBinding;
import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.List;

/**
 * Адаптер для работы со списком граждан
 */

public class AppRecyclerAdapter extends RecyclerView.Adapter<AppViewHolder> {

    private static CallbackSelected callback;

    private final List<Citizen> citizens;

    public static void registerCallbackSelected(CallbackSelected callbackSelected) {
        AppRecyclerAdapter.callback = callbackSelected;
    }

    public AppRecyclerAdapter(List<Citizen> citizens) {
        this.citizens = citizens;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCitizensBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_citizens, parent, false);
        return new AppViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Citizen citizen = citizens.get(position);

        holder.bind(citizen);
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) callback.onCallbackSelected(citizen);
        });
    }

    @Override
    public int getItemCount() {
        return citizens.size();
    }

    public interface CallbackSelected {
        void onCallbackSelected(Citizen citizen);
    }
}
