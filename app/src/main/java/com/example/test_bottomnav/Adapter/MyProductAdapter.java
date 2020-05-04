package com.example.test_bottomnav.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_bottomnav.MainData;
import com.example.test_bottomnav.OnLoadMoreListener;
import com.example.test_bottomnav.R;

public class MyProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    ArrayList<MainData> mainData;
    LayoutInflater layoutInflater;
    View view;

    public MyProductAdapter(RecyclerView recyclerView, ArrayList<MainData> mainData, Context mContext) {
        this.mainData = mainData;
        this.mContext = mContext;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(viewGroup.getContext());

        if (i == VIEW_TYPE_ITEM) {
            view = layoutInflater.inflate(R.layout.row_myproduct, viewGroup, false);
            MainViewHoldew mViewHolder = new MainViewHoldew(view);
            return mViewHolder;
        } else if (i == VIEW_TYPE_LOADING) {
            view = layoutInflater.inflate(R.layout.loading, viewGroup, false);
            LoadingViewHolder mViewHolder2 = new LoadingViewHolder(view);
            return mViewHolder2;
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof MainViewHoldew) {
            MainViewHoldew RMAViewHolder = (MainViewHoldew) viewHolder;

            RMAViewHolder.nama_product.setText(mainData.get(i).getNama());
            RMAViewHolder.type.setText(mainData.get(i).getAlamat());
            RMAViewHolder.jumlah.setText(mainData.get(i).getNo_hp());

            RMAViewHolder.selr_tv_row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, details_myProduct.class);

                    //i.putExtra("ProductName",mainData.get(1).getAlamat());
                    i.putExtra("ProductName",mainData.get(viewHolder.getAdapterPosition()).getAlamat());
                    //i.putExtra("ProductType", String.valueOf(mainData.get(getAdapterPosition())));
                    i.putExtra("ProductType", mainData.get(viewHolder.getAdapterPosition()).getNama());
                    //i.putExtra("JumlahProduct", String.valueOf(mainData.get(getAdapterPosition())));
                    i.putExtra("JumlahProduct", mainData.get(viewHolder.getAdapterPosition()).getNo_hp());
                    v.getContext().startActivity(i);

                    mContext.startActivity(i);
                    ((Activity)mContext).finish();
                }
            });

        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mainData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public class MainViewHoldew extends RecyclerView.ViewHolder{
        private TextView nama_product, type, jumlah;
        CardView selr_tv_row;

        public MainViewHoldew(View itemView) {
            super(itemView);
            selr_tv_row = (CardView) view.findViewById(R.id.selr_tv_row);
            nama_product = (TextView) itemView.findViewById(R.id.txt_nama_product);
            type = (TextView) itemView.findViewById(R.id.txt_type);
            jumlah = (TextView) itemView.findViewById(R.id.txt_jumlah);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }
}
