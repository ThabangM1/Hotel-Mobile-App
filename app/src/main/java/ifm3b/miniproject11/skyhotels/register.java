package ifm3b.miniproject11.skyhotels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;
import static ifm3b.miniproject11.skyhotels.login.hashText;

public class register extends AppCompatActivity {
    private RequestQueue rQueue;
    private Button btnRegister;
    private EditText edtFName;
    private EditText edtLName;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtConPass;
    private TextView txtSignIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init(){
        rQueue = Volley.newRequestQueue(this);
        btnRegister = findViewById(R.id.btnRegister);
        edtFName = findViewById(R.id.edtFname);
        edtLName = findViewById(R.id.edtLname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass  = findViewById(R.id.edtPassword);
        edtConPass = findViewById(R.id.edtPasswordConfirm);
        txtSignIn = findViewById(R.id.tvSignIn);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData();
            }
        });
    }

    private void openLogin(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    private void createData(){
        String url = URL +"/user";
        System.out.println(url);
         if(TextUtils.isEmpty(edtFName.getText())||
            TextUtils.isEmpty(edtLName.getText())||
            TextUtils.isEmpty(edtEmail.getText())||
            TextUtils.isEmpty(edtPass.getText())||
            TextUtils.isEmpty(edtConPass.getText())){
             Toast.makeText(this, "Fill In the required information", Toast.LENGTH_SHORT).show();
         }
         else if(!(edtPass.getText().toString().equals(edtConPass.getText().toString()))){
             Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
         }
         else {
             JSONObject postData = new JSONObject();
             try {
                 postData.put("fname", edtFName.getText());
                 postData.put("lname", edtLName.getText());
                 postData.put("email", edtEmail.getText());
                 postData.put("password",hashText(edtPass.getText().toString()));
                 postData.put("status","active");


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
             rQueue.add(jsonObjectRequest);

             openLogin();
         }
    }
}
