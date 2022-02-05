package com.gdsc.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdsc.homework.R;
import com.gdsc.homework.fragment.TopFrag1;
import com.gdsc.homework.model.FamilyChores;
import com.gdsc.homework.model.MyChores;

import java.util.ArrayList;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder> {
    private final ArrayList<MyChores> localDataSet;
    private Context context;

    public MyAdpater(ArrayList<MyChores> dataSet) {
        this.localDataSet = dataSet;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRole, tvMemo, tvTime;

        ViewHolder(View itemView){
            super(itemView);
            tvRole = itemView.findViewById(R.id.role);
            tvMemo = itemView.findViewById(R.id.memo);
            tvTime = itemView.findViewById(R.id.time);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_mine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyChores item = localDataSet.get(position);
        holder.tvRole.setText(item.getRole());
        holder.tvMemo.setText(item.getMemo());
        holder.tvTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
