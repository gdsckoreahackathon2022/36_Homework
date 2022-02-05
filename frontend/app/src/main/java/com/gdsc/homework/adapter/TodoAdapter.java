package com.gdsc.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdsc.homework.R;
import com.gdsc.homework.model.ToDo;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private final ArrayList<ToDo> localDataSet;

    public TodoAdapter(ArrayList<ToDo> dataSet) {
        this.localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoles;
        CardView card_temp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoles = itemView.findViewById(R.id.roles);
            card_temp = itemView.findViewById(R.id.card_temp);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDo item = localDataSet.get(position);
        holder.tvRoles.setText(item.getRoles());
        holder.card_temp.setBackgroundResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
