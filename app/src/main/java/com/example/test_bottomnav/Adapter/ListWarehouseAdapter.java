package com.example.test_bottomnav.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_bottomnav.Model.ListWarehouse_model;
import com.example.test_bottomnav.R;

import java.util.ArrayList;

public class ListWarehouseAdapter extends RecyclerView.Adapter<ListWarehouseAdapter.ListWarehouseViewHolder> {
    private ArrayList<ListWarehouse_model> dataList;

    public ListWarehouseAdapter(ArrayList<ListWarehouse_model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ListWarehouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_listwarehouse, parent, false);
        return new ListWarehouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListWarehouseViewHolder holder, int position) {
        holder.txtNamaWarehouse.setText(dataList.get(position).getNamaWarehouse());
        holder.txtAlamatWarehouse.setText(dataList.get(position).getAlamatWarehouse());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListWarehouseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaWarehouse, txtAlamatWarehouse;

        public ListWarehouseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaWarehouse = (TextView) itemView.findViewById(R.id.txt_nama_warehouse);
            txtAlamatWarehouse = (TextView) itemView.findViewById(R.id.txt_alamat_warehouse);
        }
    }
}
