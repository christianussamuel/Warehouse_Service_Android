package com.example.test_bottomnav.CrudProduct;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test_bottomnav.MainActivity;
import com.example.test_bottomnav.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityCrud extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Pets> petsList;
    ApiInterface apiInterface;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincrud);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(MainActivityCrud.this, EditorActivity.class);
                intent.putExtra("id", petsList.get(position).getId());
                intent.putExtra("name", petsList.get(position).getName());
                intent.putExtra("warehouse_asal", petsList.get(position).getWarehouse_asal());
                intent.putExtra("quantity", petsList.get(position).getQuantity());
                intent.putExtra("type", petsList.get(position).getType());
                intent.putExtra("picture", petsList.get(position).getPicture());
                intent.putExtra("expired", petsList.get(position).getExpired());
                startActivity(intent);

            }



            @Override
            public void onLoveClick(View view, int position) {

                final int id = petsList.get(position).getId();
                final Boolean love = petsList.get(position).getLove();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    petsList.get(position).setLove(false);
                    updateLove("update_love", id, false);
                    adapter.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    petsList.get(position).setLove(true);
                    updateLove("update_love", id, true);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityCrud.this, EditorActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Product...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent gotoBack = new Intent(MainActivityCrud.this, MainActivity.class);
        //gotoBack.putExtra(USER_GLOBAL_SENDER, username_global); <-- Use this if you want to carry some data to the other activity.
        startActivity(gotoBack);
    }

    public void getPets(){

        Call<List<Pets>> call = apiInterface.getPets();
        call.enqueue(new Callback<List<Pets>>() {
            @Override
            public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                progressBar.setVisibility(View.GONE);
                petsList = response.body();
                Log.i(MainActivityCrud.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(petsList, MainActivityCrud.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pets>> call, Throwable t) {
                Toast.makeText(MainActivityCrud.this, "rp :"+
                        t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLove(final String key, final int id, final Boolean love){

        Call<Pets> call = apiInterface.updateLove(key, id, love);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {

                Log.i(MainActivityCrud.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(MainActivityCrud.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivityCrud.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                Toast.makeText(MainActivityCrud.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPets();
    }

}
