package com.example.quanlydienthoai.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlydienthoai.R;
import com.example.quanlydienthoai.model.Manufacturer;

import java.util.List;

public class ManufacturerAdapter extends RecyclerView.Adapter<ManufacturerAdapter.ViewHolder> {
    public interface OnActionListener{
        void onEdit(Manufacturer manufacturer);
        void onDelete(Manufacturer manufacturer);
    }
    private List<Manufacturer> list;
    private Context context;
    private OnActionListener listener;
    public ManufacturerAdapter(Context context, List<Manufacturer> list, OnActionListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_manufacturer, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Manufacturer manufacturer = list.get(position);
        holder.tvName.setText(manufacturer.getName());
        holder.tvDescription.setText(manufacturer.getDescription());
        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Thao tác")
                    .setItems(new String[]{"Sửa", "Xóa"}, (dialog, which) -> {
                        if(which == 0) listener.onEdit(manufacturer);
                        else listener.onDelete(manufacturer);
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

}
