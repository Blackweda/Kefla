package com.gopipedream.topknotch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    /* SOURCES

    ***Android Photo Gallery App using Glide - https://youtu.be/YBXRB3CP89Q


    */

    Toolbar toolbar;
    ArrayList<Album> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // Simple Constructor (Vertical Display): layoutManager = new GridLayoutManager(this, 1);
        layoutManager = new GridLayoutManager(this, 1, HORIZONTAL, false);
        // (Context, Number of Rows/Columns, Orientation, ReverseLayout) Note: Rows or Columns decided by the Orientation I'm using!

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Configuration.serv_url, (String) null,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response){

                        int count = 0;
                        while(count < response.length()){

                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                arrayList.add(new Album(jsonObject.getString("id"), jsonObject.getString("title")));
                                count++;

                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        recyclerAdapter = new RecyclerAdapter(arrayList, MainActivity.this);
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error){

                }
        });
        MySingleton.getmInstance(MainActivity.this).addToRequestQue(jsonArrayRequest);
    }


    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.main_menu:
                Intent menuIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(menuIntent);
                return true;
            /*
            case R.id.book_appointment_menu:
                menuIntent = new Intent(MainActivity.this, BookAppointmentActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.listing_gallery_menu:
                menuIntent = new Intent(MainActivity.this, ListingGalleryActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.about_menu:
                menuIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(menuIntent);
                return true;


            case R.id.something_menu:
                menuIntent = new Intent(MainActivity.this, SomethingActivity.class);
                startActivity(menuIntent);
                return true;
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
