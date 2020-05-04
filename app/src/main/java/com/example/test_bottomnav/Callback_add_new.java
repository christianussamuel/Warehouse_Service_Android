package com.example.test_bottomnav;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Callback_add_new extends AsyncTask<String, String, Integer> {
    Global global;
    private JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    JSONArray orders = null;

    @Override
    protected Integer doInBackground(String... params) {

        global = new Global();
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        JSONObject json = null;

        param.add(new BasicNameValuePair("P1", params[0]));
        param.add(new BasicNameValuePair("P2", params[1]));
        param.add(new BasicNameValuePair("P3", params[2]));

        //json = jsonParser.makeHttpRequest("http://generasiterpilih.or.id/webinar/svc_myaddressbook_get.php", "POST", param);
        json = jsonParser.makeHttpRequest(global.getUrlAdd(), "POST", param);

        System.out.println("json = "+json.toString());

        arrayList = new ArrayList<HashMap<String, String>>();

        try {
            return json.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
