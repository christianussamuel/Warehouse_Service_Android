package com.example.test_bottomnav.Adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.test_bottomnav.R;

public class details_myProduct extends AppCompatActivity {
    TextView textnama_product, texttype_product, text_jumlah;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_my_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String nama_product = i.getStringExtra("ProductName");
        String product_type = i.getStringExtra("ProductType");
        String jumlah = i.getStringExtra("JumlahProduct");
        textnama_product = findViewById(R.id.detail_productName);
        texttype_product = findViewById(R.id.detail_productType);
        text_jumlah = findViewById(R.id.detail_jumlah);
        textnama_product.setText(nama_product);
        texttype_product.setText(product_type);
        text_jumlah.setText(jumlah);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
