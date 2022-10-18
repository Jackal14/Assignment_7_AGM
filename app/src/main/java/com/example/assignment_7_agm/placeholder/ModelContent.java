package com.example.assignment_7_agm.placeholder;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment_7_agm.Model;
import com.example.assignment_7_agm.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Make the JSON Volley calls, convert the payload to a Model using Gson, and return the array of models back to the ItemListActivity, just like we kinda did with the MediaWeb assignment.
public class ModelContent {

    //List of our models that we'll build based on our payload using GSON
    public static final List<Model> MODELS = new ArrayList<>();
    public static final Map<String, Model> MODELS_MAP = new HashMap<>();
    private static boolean BUILT = false;

    public void jsonParse(Activity activity)
    {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = activity.getString(R.string.JSON_url);
        // Request a string response from the provided URL.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // NEXT, we need to use GSON to turn that JSON into a model
                        try {
                            JSONObject object = response.getJSONObject("record");
                            JSONArray jsonArray = object.getJSONArray("civilizations");
                            MODELS.clear();
                            MODELS_MAP.clear();
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
//                                JSONObject gameCompany = jsonArray.getJSONObject(i);
//                                String name = gameCompany.getString("name");
//                                String leader = gameCompany.getString("leader");
//                                String civDescription = gameCompany.getString("description");
//                                Model model = new Model(name, leader, civDescription);

                                JSONObject civilization = jsonArray.getJSONObject(i);
                                String json = String.valueOf(civilization);
                                Gson gson = new Gson();
                                Model model = gson.fromJson(json, Model.class);
                                MODELS.add(model);
                                MODELS_MAP.put(model.getName(), model);
                            }
                            if(!BUILT)
                            {
                                activity.recreate();
                            }
                            BUILT = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // you should drop a breakpoint RIGHT HERE if you need to see the error coming back
                error.printStackTrace();
            }
        });

        queue.add(objectRequest);

    }



}