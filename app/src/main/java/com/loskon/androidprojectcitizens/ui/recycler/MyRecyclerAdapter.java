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

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static CallbackSelected callbackSelected;

    private final Context context;

    private final List<Citizen> citizens;
    private String male, female;

    public static void registerCallbackSelected(CallbackSelected callbackSelected) {
        MyRecyclerAdapter.callbackSelected = callbackSelected;
    }

    public MyRecyclerAdapter(Context context, List<Citizen> citizens) {
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
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_citizens, parent, false);
        rootView.setOnClickListener(view -> handlerItemClick((RecyclerView) parent, view));
        return new MyViewHolder(rootView);
    }

    private void handlerItemClick(RecyclerView recyclerView, View itemView) {
        int position = recyclerView.getChildLayoutPosition(itemView);
        Citizen citizen = citizens.get(position);
        if (callbackSelected != null) callbackSelected.onCallbackSelected(citizen);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Citizen citizen = citizens.get(position);

        holder.fullName.setText(citizen.getFullName());
        holder.sex.setText(getSexName(citizen.isMale()));
        holder.age.setText(getAgeVal(citizen.getAge()));
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

    private String getAgeVal(int age) {
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
