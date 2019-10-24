package com.example.resimuygulamasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    private static String API_KEY = "a9a3e25050f76322c2d8616791b67902";
    private static String API_SIG = "61c590c318b56dc082a2e2251a6b3e8d";

    String url = "https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key="+API_KEY+"&per_page=20&page=1&format=json&nojsoncallback=1&api_sig="+API_SIG;
    public static final String EXTRA_URL = "mimageUrl";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = VolleySingleton.getInstance(this).getmRequestQueue();

        parseJSON();


    }

    private void parseJSON() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject photos = response.getJSONObject("photos");
                            JSONArray jsonArray = photos.getJSONArray("photo");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject photo = jsonArray.getJSONObject(i);

                                String id = photo.getString("id");
                                String server = photo.getString("server");
                                String secret = photo.getString("secret");
                                int farm = photo.getInt("farm");


                                mExampleList.add(new ExampleItem(farm, server, id, secret));
                            }
                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }
        );

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getMimageUrl());
        startActivity(detailIntent);
    }
}
