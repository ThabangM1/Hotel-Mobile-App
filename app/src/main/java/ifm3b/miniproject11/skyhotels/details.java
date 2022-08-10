package ifm3b.miniproject11.skyhotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ifm3b.miniproject11.skyhotels.adapters.ViewPagerAdapter;
import ifm3b.miniproject11.skyhotels.adapters.amenitiesAdapter;
import ifm3b.miniproject11.skyhotels.models.roomItem;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;

public class details extends AppCompatActivity {
    private roomItem currentRoom;
    private amenitiesAdapter aAdapter;
    private Button btnAvailability;
    private ArrayList<String> amenityList;
    private RecyclerView aReyclerView;
    private RecyclerView.LayoutManager aLayoutManager;
    private RequestQueue dQueue;

    private ArrayList<String> imageUrls;
    private ViewPager bViewPager;
    private ViewPagerAdapter vpa;

    private TextView tvDescription;
    private TextView tvRate;
    private TextView tvType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fetchURLs(this,new VolleyCallback() {
            @Override
            public void onSuccess(Context context) {
                System.out.println(imageUrls);
                vpa.notifyDataSetChanged();
            }
        });
        init();
        fetchAmenities();
    }

    private void init(){
        btnAvailability = findViewById(R.id.btnAvailability);
        amenityList = new ArrayList<>();
        aReyclerView = findViewById(R.id.amenitiesRecycler);
        aReyclerView.setHasFixedSize(true);
        aLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        aAdapter = new amenitiesAdapter(amenityList);
        aReyclerView.setLayoutManager(aLayoutManager);
        aReyclerView.setAdapter(aAdapter);
        tvType = findViewById(R.id.tvDetailName);
        tvType.setText(currentRoom.getType());
        tvRate = findViewById(R.id.tvRoomRate);
        tvRate.setText("R"+ currentRoom.getRate());
        tvDescription = findViewById(R.id.tvdetailDescription);
        tvDescription.setText(currentRoom.getDescription());
        bViewPager = findViewById(R.id.viewPager);
        vpa = new ViewPagerAdapter(this,imageUrls);
        bViewPager.setAdapter(vpa);

        btnAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBooking();
            }
        });
    }

    private void fetchAmenities(){
        String url = URL +"/rooms/amenities/" + currentRoom.getId();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try{
                        JSONObject o = response.getJSONObject(i);
                        amenityList.add(o.getString("amenity"));
                        System.out.println("Results array size: "+ amenityList.size());
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                aAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        dQueue.add(request);
    }

    private void fetchURLs(Context context,final VolleyCallback callback){
        currentRoom = getIntent().getParcelableExtra("room");
        imageUrls = new ArrayList<>();
        dQueue = Volley.newRequestQueue(this);

        String url = URL +"/rooms/media/"+currentRoom.getId();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject o = response.getJSONObject(i);
                        imageUrls.add(URL +"/media/" + o.getString("filename"));
                        System.out.println(imageUrls.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        dQueue.add(request);
    }

    public interface VolleyCallback{
        void onSuccess(Context context);
    }

    public void openBooking(){
        Intent intent = new Intent(this, booking.class);
        intent.putExtra("room",currentRoom);
        startActivity(intent);

    }

}
