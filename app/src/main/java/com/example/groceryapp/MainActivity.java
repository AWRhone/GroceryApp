package com.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    Button btnSearch;
    TextView tv_Groceries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Edit Text
        EditText et = (EditText) findViewById(R.id.etSearchItem);

         tv_Groceries = (TextView) findViewById(R.id.tvGroceries);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://api.bluecartapi.com/request?api_key=6617FE5C6C3E4DF188483066D34F3446&type=search&search_term=" + et.getText().toString() + "&sort_by=price_low_to_high&page=1&output=json";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                String groceryName = "LOL TRY AGAIN";
                                Double groceryPrice = 0.0;

                                try {

                                    JSONArray jsonArray = response.getJSONArray("search_results");
                                    groceryName = jsonArray.getJSONObject(0).getJSONObject("product").getString("title");
                                    groceryPrice = jsonArray.getJSONObject(0).getJSONObject("offers").getJSONObject("primary").getDouble("price");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                tv_Groceries.setText("Grocery Item: " + groceryName.toString() + "\n" + "Price: $" + String.valueOf(groceryPrice));

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        });

// Access the RequestQueue through your singleton class.
                    queue.add(jsonObjectRequest);



                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                tv_Groceries.setText("Response is: " + response.substring(0,500));
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
//                    }
//                });

// Add the request to the RequestQueue.
//                queue.add(stringRequest);

            }
        });
    }
}