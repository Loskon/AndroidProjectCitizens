package com.loskon.androidprojectcitizens.ui.recyclerview;

import static java.util.Collections.emptyList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
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

    private List<Citizen> citizens = emptyList();

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
            if (callback != null) callback.onClickingItem(citizen);
        });
    }

    public void setCitizensList(List<Citizen> citizens) {
        this.citizens = citizens;
        updateChangedList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateChangedList() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return citizens.size();
    }

    private static CallbackAdapter callback;

    public static void listenerCallback(CallbackAdapter callbackAdapter) {
        AppRecyclerAdapter.callback = callbackAdapter;
    }

    public interface CallbackAdapter {
        void onClickingItem(Citizen citizen);
    }
}
