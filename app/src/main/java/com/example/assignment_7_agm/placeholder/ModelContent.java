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
                            JSONArray jsonArray = response.getJSONArray("record:gameCompanies");
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject gameCompany = jsonArray.getJSONObject(i);
                                String name = gameCompany.getString("name");
                                Integer year = gameCompany.getInt("year");
                                String recentConsole = gameCompany.getString("recentConsole");
                                Model model = new Model(name, year, recentConsole);
                                MODELS.add(model);
                                MODELS_MAP.put(name, model);
                            }
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


    //Method to add an item to our List and Map
    private static void addItem(Model item) {
        MODELS.add(item);
        MODELS_MAP.put(item.getName(), item);
    }


}