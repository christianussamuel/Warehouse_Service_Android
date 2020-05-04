package com.example.test_bottomnav.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_bottomnav.Adapter.HistoryAdapter;
import com.example.test_bottomnav.Model.History_model;
import com.example.test_bottomnav.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<History_model> historyModelArrayList;
    public View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addData();
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_history);
        adapter = new HistoryAdapter(historyModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    void addData() {
        historyModelArrayList = new ArrayList<>();
        historyModelArrayList.add(new History_model("Samuel", "Daging Ayam", "179", "21 Mei 2020"));
        historyModelArrayList.add(new History_model("Felli", "Daging Ayam", "179", "21 Mei 2020"));
        historyModelArrayList.add(new History_model("Samuel", "Daging Ayam", "179", "21 Mei 2020"));
    }
}
