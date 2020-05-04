package com.example.test_bottomnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    EditText productName, productType, quantity;
    Button save;
    public static final String UPLOAD_URL = "https://www.projectlab.co.id/dev/mit/1317007/upload.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;

    int ret = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        productName=(EditText) findViewById(R.id.productName);
        productType=(EditText) findViewById(R.id.productType);
        quantity=(EditText) findViewById(R.id.quantity);
        save=(Button) findViewById(R.id.btn_saveProduct);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productName.getText().toString().length()==0){
                    productName.setError("Tidak boleh kosong!");
                }
                else if(productType.getText().toString().length()==0){
                    productType.setError("Tidak boleh kosong!");
                }
                else if(quantity.getText().toString().length()==0){
                    quantity.setError("Tidak boleh kosong!");
                }else {

                    Callback_add_new callback_add_new = new Callback_add_new();

                    try {
                        ret = callback_add_new.execute(
                                productName.getText().toString()
                                , productType.getText().toString()
                                , quantity.getText().toString()

                        ).get();
                    }catch (Exception x){

                    }

                    if(ret == 1){
                        Toast.makeText(getApplicationContext(), "Product Added!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddProduct.this, MainActivity.class);
                        startActivity(i);
                    }else
                        Toast.makeText(AddProduct.this, "Gagal Insert", Toast.LENGTH_SHORT).show();
                }
            }
        });
}






    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddProduct.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
        }
    }



