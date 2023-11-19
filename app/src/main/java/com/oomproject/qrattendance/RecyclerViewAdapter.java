package com.oomproject.qrattendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> dataList;
    private List<String> instructorIds;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<String> dataList, List<String> instructorIds, OnItemClickListener itemClickListener) {
        this.dataList = dataList;
        this.instructorIds = instructorIds;
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = dataList.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (itemClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(view, adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.recyclerContent);
        }

        public void bind(String item) {
            textViewItem.setText(item);
        }
    }

    public void setData(List<String> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    public String getInstructorId(int position) {
        return instructorIds.get(position);
    }

    public void setInstructorIds(List<String> instructorIds) {
        this.instructorIds = instructorIds;
    }
}
