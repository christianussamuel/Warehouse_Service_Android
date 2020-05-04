package com.example.test_bottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    Animation fromtop;

    private TextView register_link;
    private EditText email, password;
    private Button loginbutton;
    private ProgressBar loading;
    private static String URL_LOGIN = "https://www.projectlab.co.id/dev/mit/1317007/login/login.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        register_link = findViewById(R.id.register_link);

        bgapp.animate().translationY(1900).setDuration(1200).setStartDelay(3000);
        clover.animate().alpha(0).setDuration(1200).setStartDelay(3000);
        textsplash.animate().translationY(140).alpha(0).setDuration(1200).setStartDelay(3000);


        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);


        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }
            }
        });

    }
    private void Login(final String email, final String password) {

        loading.setVisibility(View.VISIBLE);
        loginbutton.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();

                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);


                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            loginbutton.setVisibility(View.VISIBLE);
                            Toast.makeText(SplashActivity.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        loginbutton.setVisibility(View.VISIBLE);
                        Toast.makeText(SplashActivity.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}