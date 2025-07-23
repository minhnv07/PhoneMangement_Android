package com.example.quanlydienthoai.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlydienthoai.PhoneListActivity;
import com.example.quanlydienthoai.R;
import com.example.quanlydienthoai.model.Phone;

import java.util.List;
import java.util.Map;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {
    public interface OnActionListener {
        void onEdit(Phone phone);
        void onDelete(Phone phone);
    }

    private List<Phone> phones;
    private OnActionListener listener;
    private Context context;
    private Map<Integer, String> manufacturerMap;

    public PhoneAdapter(Context context,List<Phone> phones,  Map<Integer, String> manufacturerMap, OnActionListener listener) {
        this.phones = phones;
        this.listener = listener;
        this.context = context;
        this.manufacturerMap = manufacturerMap;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.tvName.setText(phone.getName());
        String manufacturerName = manufacturerMap.get(phone.getManufacturerId());
        holder.tvManufacturer.setText("Hãng: " + (manufacturerName == null ? "?" : manufacturerName));
        holder.tvScreenSize.setText("Kích thước màn hình: " + phone.getScreenSize() + " inch");
        holder.tvRating.setText("Đánh giá: " + phone.getRating());

        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Chọn thao tác")
                    .setItems(new String[]{"Sửa", "Xóa"}, (dialog, which) -> {
                        if (which == 0) listener.onEdit(phone);
                        else if (which == 1) listener.onDelete(phone);
                    })
                    .show();
        });
    }

    public int getItemCount() {
        return phones.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvManufacturer;
        TextView tvScreenSize;
        TextView tvRating;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvManufacturer = itemView.findViewById(R.id.tvManufacturer);
            tvScreenSize = itemView.findViewById(R.id.tvScreenSize);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
