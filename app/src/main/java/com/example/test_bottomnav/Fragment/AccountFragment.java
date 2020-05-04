package com.example.test_bottomnav.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test_bottomnav.R;
import com.example.test_bottomnav.SessionManager;
import com.example.test_bottomnav.account.HomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    private static final String TAG = HomeActivity.class.getSimpleName(); //getting the info
    private TextView name, email;
    private Button btn_logout, btn_photo_upload, btn_change;
    SessionManager sessionManager;
    String getId;
    private static String URL_READ = "https://www.projectlab.co.id/dev/mit/1317007/login/read_detail.php";
    private static String URL_EDIT = "https://www.projectlab.co.id/dev/mit/1317007/login/edit_detail.php";
    private static String URL_UPLOAD = "https://www.projectlab.co.id/dev/mit/1317007/login/upload.php";
    private Menu action;
    private Bitmap bitmap;
    CircleImageView profile_image;
    private FragmentActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_unedit, container, false);
//        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();
        this.context = getActivity();
        sessionManager = new SessionManager(this.context);
        sessionManager.checkLogin();

        setHasOptionsMenu(true);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_photo_upload = view.findViewById(R.id.btn_photo);
        btn_change = view.findViewById(R.id.btn_change);

        profile_image = view.findViewById(R.id.profile_image);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        btn_logout.setOnClickListener(v -> sessionManager.logout());


        btn_change.setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeActivity.class)));
        return view;
    }

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for (int i =0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();
                                    //Add this for fetch image from json
                                    String strImage = object.getString("image").trim();

                                    name.setText(strName);
                                    email.setText(strEmail);

                                    if (strImage.isEmpty()) {
                                        profile_image.setImageResource(R.drawable.logo);
                                    } else{
                                        Picasso.get().load(strImage).into(profile_image);
                                    }
//                                    //display image from string url
//                                    Picasso.get().load(strImage).into(profile_image);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error Reading Detail "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_action, menu);
//

        inflater.inflate(R.menu.menu_action, menu) ;
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_edit:

                name.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                name.setFocusableInTouchMode(false);
                email.setFocusableInTouchMode(false);
                name.setFocusable(false);
                email.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    //save
    private void SaveEditDetail() {

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                response -> {
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("1")){
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                            sessionManager.createSession(name, email, id);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            UploadPicture(getId, getStringImage(bitmap));

        }
    }

    private void UploadPicture(final String id, final String photo) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                response -> {
                    progressDialog.dismiss();
                    Log.i(TAG, response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("1")){
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.NO_WRAP);

        return encodedImage;
    }
}
