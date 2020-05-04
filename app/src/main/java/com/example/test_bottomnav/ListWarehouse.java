package com.example.test_bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test_bottomnav.Adapter.HistoryAdapter;
import com.example.test_bottomnav.Adapter.ListWarehouseAdapter;
import com.example.test_bottomnav.Model.History_model;
import com.example.test_bottomnav.Model.ListWarehouse_model;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListWarehouse extends AppCompatActivity {
    private FloatingActionButton floatAddWarehouse;
    private RecyclerView recyclerView;
    private ListWarehouseAdapter adapter;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private ArrayList<ListWarehouse_model> listWarehouseModelArrayList;
    public View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_warehouse);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatAddWarehouse = (FloatingActionButton) findViewById(R.id.float_addWarehouse);
        floatAddWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListWarehouse.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                }else {
                    move();
                }
            }
        });

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_listWarehouse);
        adapter = new ListWarehouseAdapter(listWarehouseModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListWarehouse.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void addData() {
        listWarehouseModelArrayList = new ArrayList<>();
        listWarehouseModelArrayList.add(new ListWarehouse_model("Warehouse 1", "jl.Flamboyan no 19"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                move();
            }else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
        }

        return super.onOptionsItemSelected(item);
    }

    private void move() {
        Intent intent = new Intent(ListWarehouse.this, AddWarehouse.class);
        startActivity(intent);
    }
}
