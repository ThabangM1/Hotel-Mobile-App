package ifm3b.miniproject11.skyhotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ifm3b.miniproject11.skyhotels.adapters.reservationAdpater;
import ifm3b.miniproject11.skyhotels.models.reservation;
import ifm3b.miniproject11.skyhotels.models.roomItem;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;
import static ifm3b.miniproject11.skyhotels.login.getUserid;
import static ifm3b.miniproject11.skyhotels.login.userid;

public class reservations extends AppCompatActivity {
    private ArrayList<reservation> reservations;
    private RequestQueue rQueue;
    private RecyclerView rRecyclerView;
    private RecyclerView.LayoutManager rlayoutmanager;
    private reservationAdpater rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        init();
        fetchReservations();
    }

    private void init(){
        reservations = new ArrayList<>();
        rQueue = Volley.newRequestQueue(this);
        rRecyclerView = findViewById(R.id.reservationRecycler);
        rRecyclerView.setHasFixedSize(true);
        rlayoutmanager = new LinearLayoutManager(this);
        rAdapter = new reservationAdpater(reservations);
        rRecyclerView.setLayoutManager(rlayoutmanager);
        rRecyclerView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new reservationAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openBooking(reservations.get(position));
            }
        });

    }

    private void openBooking(reservation reservation) {
        Intent intent = new Intent(this,booking.class);
        intent.putExtra("booking",reservation);
        intent.putExtra("modify",true);
        startActivity(intent);
    }

    private void fetchReservations(){//getUserid();
        String url = URL+"/bookings/user/" + userid;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try{
                        JSONObject o = response.getJSONObject(i);
                        reservation res = new reservation(o.getString("name"),
                                o.getString("room_num"),
                                o.getInt("id"),
                                o.getInt("guest_id"),
                                o.getInt("room_id"),
                                o.getString("start_date"),
                                o.getString("end_date"),
                                o.getString("status"));
                        reservations.add(res);
                        System.out.println("Results array size: "+ reservations.size());
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                rAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
    rQueue.add(request);
    }

}