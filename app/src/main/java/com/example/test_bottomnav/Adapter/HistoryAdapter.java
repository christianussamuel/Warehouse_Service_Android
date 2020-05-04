package com.example.test_bottomnav.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test_bottomnav.Model.History_model;
import com.example.test_bottomnav.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<History_model> dataList;

    public HistoryAdapter(ArrayList<History_model> dataList) {
        this.dataList = dataList;
    }

    @Override
    public  HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.txtNamaPengirim.setText(dataList.get(position).getNamaPengirim());
        holder.txtNamaProduct.setText(dataList.get(position).getNamaProduct());
        holder.txtJumlah.setText(dataList.get(position).getJumlah());
        holder.txtTanggal.setText(dataList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNamaPengirim, txtNamaProduct, txtJumlah, txtTanggal;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            txtNamaPengirim = (TextView) itemView.findViewById(R.id.txt_nama_pengirim);
            txtNamaProduct = (TextView) itemView.findViewById(R.id.txt_nama_product);
            txtJumlah = (TextView) itemView.findViewById(R.id.txt_jumlah);
            txtTanggal = (TextView) itemView.findViewById(R.id.txt_tanggal);
        }
    }
}
