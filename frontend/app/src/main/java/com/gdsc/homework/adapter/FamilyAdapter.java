package com.gdsc.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdsc.homework.R;
import com.gdsc.homework.model.FamilyChores;

import java.util.ArrayList;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {
    private final ArrayList<FamilyChores> localDataSet;

    public FamilyAdapter(ArrayList<FamilyChores> dataSet) {
        localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tvProfile;
        TextView tvRole, tvMemo;

        // 뷰홀더는 리스트의 한 아이템 뷰를 나타내며 필요에 따라 재사용됨.
        ViewHolder(View itemView){
            super(itemView);

            tvProfile = itemView.findViewById(R.id.profile);
            tvRole = itemView.findViewById(R.id.role);
            tvMemo = itemView.findViewById(R.id.memo);
        }
    }

    @NonNull
    @Override // 재사용할 수 있는 뷰홀더가 없을 때, 새로운 뷰홀더 객체 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_family, parent, false);
        return new ViewHolder(view);
    }

    @Override // 뷰홀더의 아이템뷰에 있는 모든 뷰의 내용 변경 (바인딩)
    public void onBindViewHolder(@NonNull FamilyAdapter.ViewHolder holder, int position) {
        FamilyChores item = localDataSet.get(position);
        holder.tvProfile.setImageResource(item.getImageResource());
        holder.tvRole.setText(item.getRole());
        holder.tvMemo.setText(item.getMemo());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
