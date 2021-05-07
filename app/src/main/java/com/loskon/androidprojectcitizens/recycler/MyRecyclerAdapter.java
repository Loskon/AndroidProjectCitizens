package com.loskon.androidprojectcitizens.recycler;

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
 * Адаптер для работы со списком горожан
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>  {

    private static CallbackSelected callback;

    private final Context context;

    private final List<Citizen> citizens;
    private String male, female;


    public static void registerCallbackSelected(CallbackSelected callback) {
        MyRecyclerAdapter.callback = callback;
    }


    public MyRecyclerAdapter(Context context, List<Citizen> citizens) {
        this.context = context;
        this.citizens = citizens;
        initialiseVariables();
    }

    private void initialiseVariables() {
        male = context.getString(R.string.sex_citizen_male);
        female = context.getString(R.string.sex_citizen_female);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_citizens, parent,false);
        rootView.setOnClickListener(view -> handlerItemClick((RecyclerView) parent, view));
        return new MyViewHolder(rootView);
    }

    private void handlerItemClick(RecyclerView recyclerView, View itemView) {
        int position = recyclerView.getChildLayoutPosition(itemView);
        Citizen citizen = citizens.get(position);
        if (callback != null) callback.onCallbackSelected(0);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Citizen citizen = citizens.get(position);

        holder.title.setText(citizen.getFullName());
        holder.sex.setText(getSexName(citizen.isMale()));
        holder.age.setText(getAgeVal(citizen.getAgeVal()));
    }

    private String getSexName(boolean isMale) {
        String string = male;

        if (!isMale) {
            string = female;
        }

        return string;
    }

    private String getAgeVal(int age) {
        return context.getString(R.string.ag_citizen, age);
    }


    @Override
    public int getItemCount() {
        return citizens.size();
    }


    public interface CallbackSelected {
        void onCallbackSelected(int id);
    }
}
