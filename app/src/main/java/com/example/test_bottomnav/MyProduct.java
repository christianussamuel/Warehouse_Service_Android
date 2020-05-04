package com.example.test_bottomnav;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_bottomnav.Adapter.MyProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyProduct extends AppCompatActivity {

    ArrayList<MainData> mainData;
    ArrayList<HashMap<String, String>> arrayList;
    private FloatingActionButton floatAddProduct;
    private RecyclerView recyclerView;
    MyProductAdapter myProductAdapter;
    int index = 0, limit = 5, total = 0;
    Global global = new Global();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_myProduct);

        showAllData();

        floatAddProduct = (FloatingActionButton) findViewById(R.id.float_addProduct);
        floatAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyProduct.this, AddProduct.class);
                startActivity(intent);

            }
        });

    }

    public void showAllData(){

        mainData = new ArrayList<>();
        arrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyProduct.this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = getQuestion(index, limit);

        if(arrayList != null)
            total = Integer.parseInt(arrayList.get(0).get(global.TAG_COUNT));

        for(int i = 0; i < arrayList.size(); i++){
            mainData.add(new MainData(
                    arrayList.get(i).get(global.DATA_TYPE)
                    , arrayList.get(i).get(global.DATA_PRODUCTNAME)
                    , arrayList.get(i).get(global.DATA_QUANTITY)
            ));
        }

        myProductAdapter = new MyProductAdapter(recyclerView, mainData, MyProduct.this);
        recyclerView.setAdapter(myProductAdapter);

        myProductAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (arrayList.size() < total) {
                    arrayList.add(null);
                    myProductAdapter.notifyItemInserted(arrayList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            arrayList.remove(arrayList.size() - 1);
                            myProductAdapter.notifyItemRemoved(arrayList.size());

                            index = arrayList.size();

                            arrayList = getQuestion(index, limit);

                            for(int i = 0; i < arrayList.size(); i++){
                                mainData.add(new MainData(
                                        arrayList.get(i).get(global.DATA_TYPE)
                                        , arrayList.get(i).get(global.DATA_PRODUCTNAME)
                                        , arrayList.get(i).get(global.DATA_QUANTITY)
                                ));
                            }

                            index = arrayList.size();

                            myProductAdapter.notifyDataSetChanged();
                            myProductAdapter.setLoaded();
                        }
                    }, 500);
                }
            }
        });

    }

    public ArrayList<HashMap<String, String>> getQuestion(int index, int limit){

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        CallBackMain callBackMain = new CallBackMain(MyProduct.this);

        try{
            arrayList = callBackMain.execute(""+index, ""+limit).get();
        }catch (Exception x){

        }

        System.out.println("arraylist = "+arrayList);

        return arrayList;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
        }

        return super.onOptionsItemSelected(item);
    }
}
