package ifm3b.miniproject11.skyhotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ifm3b.miniproject11.skyhotels.adapters.browseAdapter;
import ifm3b.miniproject11.skyhotels.adapters.expandableListAdapter;
import ifm3b.miniproject11.skyhotels.models.roomItem;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;

public class browse extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView bRecyclerView;
    private browseAdapter bAdapter;
    private RecyclerView.LayoutManager bLayoutManager;
    private RequestQueue bQueue;
    private ArrayList<roomItem> results;
    private DrawerLayout bDrawerLayout;
    private ActionBarDrawerToggle bToggle;
    private NavigationView navigationView;

    private ExpandableListView expandableListView;
    private expandableListAdapter listAdapter;

    private List<String> listTitles;
    private Map<String,List<String>> listChildren;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        initDrawer();
        initRecyclerView();
        getRooms();
    }

    private void initDrawer(){
        bDrawerLayout = findViewById(R.id.browseDrawer);
        bToggle = new ActionBarDrawerToggle(this,bDrawerLayout,R.string.Open,R.string.Close);
        bDrawerLayout.addDrawerListener(bToggle);
        bToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initRecyclerView(){
        results = new ArrayList<>();
        bQueue = Volley.newRequestQueue(this);
        bRecyclerView = findViewById(R.id.browseRecyclerview);
        bRecyclerView.setHasFixedSize(true);
        bLayoutManager = new LinearLayoutManager(this);
        bAdapter = new browseAdapter(results);
        bRecyclerView.setLayoutManager(bLayoutManager);
        bRecyclerView.setAdapter(bAdapter);

        bAdapter.setOnItemClickListener(new browseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDetails(results.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawermenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void getRooms(){
        String url = URL+"/rooms/filter/a";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try{
                        JSONObject o = response.getJSONObject(i);
                        roomItem room = new roomItem(o.getInt("id"),
                                                     o.getString("room_num"),
                                                     o.getString("name"),
                                                     o.getString("description"),
                                                     o.getDouble("rate"),
                                                     o.getString("status"),
                                                     o.getInt("capacity"));
                        results.add(room);
                        room.setImgUrl(URL + "/media/"+o.getString("filename"));
                        System.out.println("Results array size: "+ results.size());
                        System.out.println(room.getImgUrl());
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                bAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        bQueue.add(request);
    }

    public void openDetails(roomItem room){
        Intent intent = new Intent(this,details.class);
        intent.putExtra("room",room);
        startActivity(intent);
    }

    public void openLogin(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    public void openReservations(){
        Intent intent = new Intent(this,reservations.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reservations: {
                openReservations();
                break;
            }

            case R.id.Logout: {
                openLogin();
                break;
            }
        }
            return true;
    }
}