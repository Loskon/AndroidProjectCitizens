package com.loskon.androidprojectcitizens.ui.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectcitizens.R;

/**
 * Предоставление доступа View-компонентам
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView fullName;
    public TextView sex;
    public TextView age;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        fullName = itemView.findViewById(R.id.tv_card_full_name);
        sex = itemView.findViewById(R.id.tv_card_sex);
        age = itemView.findViewById(R.id.tv_card_age);
    }
}