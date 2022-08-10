package ifm3b.miniproject11.skyhotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import ifm3b.miniproject11.skyhotels.models.bookingItem;
import ifm3b.miniproject11.skyhotels.models.dates;
import ifm3b.miniproject11.skyhotels.models.reservation;
import ifm3b.miniproject11.skyhotels.models.roomItem;
import ifm3b.miniproject11.skyhotels.models.userItem;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;
import static ifm3b.miniproject11.skyhotels.login.getUserid;
import static ifm3b.miniproject11.skyhotels.login.hashText;

public class booking extends AppCompatActivity {
    private roomItem currentRoom;
    private reservation currentBooking;
    private Button btnBook;
    private Button btnUpdate;
    private MaterialCalendarView calender;
    private EditText edtTimeIn;
    private EditText edtTimeout;
    private TextView txtName;
    private TextView txtDateIn;
    private TextView txtDateout;
    private TextView txtStayCount;
    private  TextView txtTotal;
    private RequestQueue bQueue;
    private HashSet<CalendarDay> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        fetchDates(this, new VolleyCallback() {
            @Override
            public void onSuccess(Context context) {
                //continue
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
                setupCalender();
            }
        },2000);

    }

    private void fetchDates(Context context,final VolleyCallback callback){
        bQueue = Volley.newRequestQueue(this);
        dates = new HashSet<>();
        currentRoom = getIntent().getParcelableExtra("room");
        currentBooking = getIntent().getParcelableExtra("booking");
        int id = 0;
        if(currentRoom == null){
            id = currentBooking.getRoomId();
        }else{
            id = currentRoom.getId();
        }

        String url = URL +"/bookings/room/"+ id;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try{
                        JSONObject o = response.getJSONObject(i);
                        //System.out.println(o.getString("start_date"));
                        //System.out.println(o.getString("end_date"));
                        //dates d = new dates(o.getString("start_date"),o.getString("end_date"));
                        dates.add(toDate(o.getString("start_date")));
                        dates.add(toDate(o.getString("end_date")));
                        System.out.println("RESERVATIONS ARRAY LIST SIZE------------------------------------"+dates.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
           4000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        bQueue.add(request);
    }

    private void postBooking(){
        String url = URL +"/bookings";
        System.out.println(url);

            JSONObject postData = new JSONObject();
            try {
                postData.put("room_id",currentRoom.getId());
                postData.put("guest_id",getUserid());
                postData.put("start_date",txtDateIn.getText());
                postData.put("end_date",txtDateout.getText());
                postData.put("fee",txtTotal.getText());
                postData.put("status","confirmed");
                postData.put("code",codeGen());
            }catch (JSONException e){
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            bQueue.add(jsonObjectRequest);
            openReservations();
        }

    private void updateBooking(){
        String url = URL +"/bookings";
        System.out.println(url);

        JSONObject postData = new JSONObject();
        try {
            postData.put("room_id",currentRoom.getId());
            postData.put("guest_id",getUserid());
            postData.put("start_date",txtDateIn.getText());
            postData.put("end_date",txtDateout.getText());
            postData.put("fee",txtTotal.getText());
            postData.put("status","confirmed");
            postData.put("code",codeGen());
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        bQueue.add(jsonObjectRequest);
        openReservations();
    }

    private void init(){
        calender = findViewById(R.id.calendarView);
        btnBook = findViewById(R.id.btnBook);
        btnUpdate = findViewById(R.id.btnUpdate);
        txtName = findViewById(R.id.txtRoomName);
        txtDateIn = findViewById(R.id.tvCIDate);
        txtDateout = findViewById(R.id.tvCODate);
        txtTotal = findViewById(R.id.txtTotal);
        txtStayCount = findViewById(R.id.txtStayCount);
        if(currentRoom != null){
            txtName.setText(currentRoom.getType());
        }else{
            txtName.setText("My Booking");
            btnBook.setVisibility(View.GONE);
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBooking();
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBooking();
            }
        });

        if(getIntent().getParcelableExtra("modify") == null){
            btnUpdate.setVisibility(View.GONE);
        }else{
        }
    }

    private void setupCalender(){
        decorator decorator = new decorator(this);
        disabler disabler = new disabler();
        calender.addDecorator(decorator);
        calender.addDecorator(disabler);
        calender.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                //calender.removeDecorator(decorator);

            }
        });
        calender.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(new CalendarDay(CalendarDay.today().getYear(),CalendarDay.today().getMonth(),1))
                .setMaximumDate(CalendarDay.from(2021, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calender.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                String startDate = dates.get(0).getDay()+"/"+(dates.get(0).getMonth()+1)+"/"+dates.get(0).getYear();
                String endDate = dates.get(dates.size()-1).getDay()+"/"+(dates.get(dates.size()-1).getMonth()+1)+"/"+dates.get(dates.size()-1).getYear();
                txtStayCount.setText("Days booked "+ dates.size());
                txtTotal.setText("R "+ String.valueOf((currentRoom.getRate()*dates.size())));
                txtDateIn.setText(startDate);
                txtDateout.setText(endDate);
            }
        });
        calender.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
    }

    private void openReservations(){
        Intent intent = new Intent(this,reservations.class);
        startActivity(intent);
    }

    private class disabler implements DayViewDecorator{

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.isBefore(CalendarDay.today());
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }

    private class decorator implements DayViewDecorator{
        Context context;
        private int counter;
        private boolean decorate;
        private int limit = dates.size();
        public decorator(Context context){
            decorate = false;
            counter = 1;
            this.context = context;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            if(dates.contains(day)){
                if(decorate){
                    decorate=false;
                    return true;
                }else{decorate = true;}
            }
            return decorate;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(this.context, R.drawable.disableddate)));
            view.setDaysDisabled(true);
        }
    }

    public interface VolleyCallback{
        void onSuccess(Context context);
    }

    public CalendarDay toDate(String date){
        return new CalendarDay(Integer.parseInt(date.split("\\/")[2]),
                Integer.parseInt(date.split("\\/")[1])-1,
                Integer.parseInt(date.split("\\/")[0]));
    }

    public String codeGen(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return  generatedString;
    }
}
