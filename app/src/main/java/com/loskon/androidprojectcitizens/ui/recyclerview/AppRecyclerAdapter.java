package com.loskon.androidprojectcitizens.ui.recyclerview;

import static java.util.Collections.emptyList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.databinding.RowCitizensBinding;
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
        RowCitizensBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.row_citizens, parent, false);
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
        updateList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return citizens.size();
    }

    private static RecyclerAdapterCallback callback;

    public static void registerCallbackRecyclerAdapter(RecyclerAdapterCallback callbackAdapter) {
        AppRecyclerAdapter.callback = callbackAdapter;
    }
}
