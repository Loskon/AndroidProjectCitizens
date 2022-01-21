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

public class CitizenRecyclerAdapter extends RecyclerView.Adapter<CitizenViewHolder> {

    private CitizenClickListener clickListener;

    private List<Citizen> list = emptyList();

    @NonNull
    @Override
    public CitizenViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowCitizensBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.row_citizens, parent, false);
        return new CitizenViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CitizenViewHolder holder, int position) {
        Citizen citizen = list.get(position);

        holder.bind(citizen);
        holder.itemView.setOnClickListener(v -> onItemClick(citizen));
    }

    private void onItemClick(Citizen citizen) {
        clickListener.onClickingItem(citizen);
    }

    //----------------------------------------------------------------------------------------------
    public void setCitizensList(List<Citizen> newList) {
        this.list = newList;
        updateChangedList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateChangedList() {
        notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------
    public void registerCitizenClickListener(CitizenClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
