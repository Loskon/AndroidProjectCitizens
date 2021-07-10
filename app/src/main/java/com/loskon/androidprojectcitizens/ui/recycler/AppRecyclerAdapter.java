package com.loskon.androidprojectcitizens.ui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.List;

/**
 * Адаптер для работы со списком граждан
 */

public class AppRecyclerAdapter extends RecyclerView.Adapter<AppViewHolder> {

    private static CallbackSelected callback;

    private final Context context;

    private final List<Citizen> citizens;
    private String male, female;

    public static void registerCallbackSelected(CallbackSelected callbackSelected) {
        AppRecyclerAdapter.callback = callbackSelected;
    }

    public AppRecyclerAdapter(Context context, List<Citizen> citizens) {
        this.context = context;
        this.citizens = citizens;
        initialiseVariables();
    }

    private void initialiseVariables() {
        male = context.getString(R.string.item_sex_male);
        female = context.getString(R.string.item_sex_female);
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_citizens, parent, false);
        view.setOnClickListener(v -> handlerItemClick((RecyclerView) parent, v));
        return new AppViewHolder(view);
    }

    private void handlerItemClick(RecyclerView recyclerView, View itemView) {
        int position = recyclerView.getChildLayoutPosition(itemView);
        Citizen citizen = citizens.get(position);
        if (callback != null) callback.onCallbackSelected(citizen);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Citizen citizen = citizens.get(position);

        holder.fullName.setText(citizen.getFullName());
        holder.sex.setText(getSexName(citizen.isMale()));
        holder.age.setText(getAge(citizen.getAge()));
    }

    private String getSexName(boolean isMale) {
        String string;

        if (isMale) {
            string = male;
        } else {
            string = female;
        }

        return string;
    }

    private String getAge(int age) {
        return context.getString(R.string.Item_age, age);
    }

    @Override
    public int getItemCount() {
        return citizens.size();
    }

    public interface CallbackSelected {
        void onCallbackSelected(Citizen citizen);
    }
}
