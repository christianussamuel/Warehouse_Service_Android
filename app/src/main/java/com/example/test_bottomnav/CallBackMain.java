package com.example.test_bottomnav;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CallBackMain extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {
    HashMap<String, String> order;
    Global global;
    private ProgressDialog pDialog;
    Context context;
    int success;
    String message;
    private JSONParser jsonParser = new JSONParser();
    JSONArray orders = null;
    ArrayList<HashMap<String, String>> isExist;


    public CallBackMain(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Add to database");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();
    }


    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

        global = new Global();


        // Building Parameters
        List<NameValuePair> param = new ArrayList<NameValuePair>();

        JSONObject json = null;

/*
      param.add(new BasicNameValuePair("P1", params[0]));
      param.add(new BasicNameValuePair("P2", params[1]));*/

        json = jsonParser.makeHttpRequest(global.getUrlGet(), "POST", param);
        System.out.println("json = "+json.toString());
        isExist = new ArrayList<HashMap<String, String>>();


        try {
            success = json.getInt(global.TAG_SUCCESS);
            message = json.getString(global.TAG_MESSAGE);

            if (success == 1) {

                // Getting Array of Return Value Login
                orders = json.getJSONArray("addrs");

                // looping through All Return Value
                for (int i = 0; i < orders.length(); i++) {
                    JSONObject c = orders.getJSONObject(i);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(global.DATA_TYPE, c.getString(global.DATA_TYPE));
                    map.put(global.DATA_PRODUCTNAME, c.getString(global.DATA_PRODUCTNAME));
                    map.put(global.DATA_QUANTITY, c.getString(global.DATA_QUANTITY));
                    map.put(global.TAG_COUNT, c.getString(global.TAG_COUNT));

                    // adding HashList to ArrayList
                    isExist.add(map);
                }
            } else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isExist;

    }


    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(ArrayList<HashMap<String, String>> file_url) {

        pDialog.dismiss();
    }
}
