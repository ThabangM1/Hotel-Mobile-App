package ifm3b.miniproject11.skyhotels;

import androidx.appcompat.app.AppCompatActivity;

import ifm3b.miniproject11.skyhotels.models.config;
import ifm3b.miniproject11.skyhotels.models.userItem;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;
import static ifm3b.miniproject11.skyhotels.models.config.writeState;

public class login extends AppCompatActivity {
    private ArrayList<userItem> results;
    private TextView txtReg;
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private RequestQueue lQueue;
    public static int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public static int getUserid(){
        return userid;
    }

    private void init(){
        results = new ArrayList<>();
        edtEmail = findViewById(R.id.edtLogEmail);
        txtReg = findViewById(R.id.txtReg);
        edtPass = findViewById(R.id.edtLogPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lQueue = Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLogin();
            }
        });

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
    }

    private void jsonParse(String email, final VolleyCallBack callBack){
        String url = URL +"/user/login/" +email ;
        System.out.println(url);

        System.out.println(hashText(edtPass.getText().toString()));

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject o = response.getJSONObject(i);
                        userItem newUserItem = new userItem(o.getInt("id"),
                                                            o.getString("fname"),
                                                            o.getString("lname"),
                                                            o.getString("email"),
                                                            o.getString("password"),
                                                            o.getString("status"));
                        userid = o.getInt("id");
                        results.add(newUserItem);
                        System.out.println("Result size: "+results.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        lQueue.add(request);
    }

    private void verifyLogin(){
        if(TextUtils.isEmpty(edtPass.getText())||TextUtils.isEmpty(edtEmail.getText())){
            Toast.makeText(this,"Fill in User Email and Password",Toast.LENGTH_SHORT).show();
        }
        else {
            jsonParse(edtEmail.getText().toString(), new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    validate();
                }
            });
        }
    }

    private void validate(){
        if(results.isEmpty()) {
            Toast.makeText(this, "User does not Exist, Register New Profile", Toast.LENGTH_SHORT).show();
        }
        else if(results.get(0).getPassword().equals(hashText(edtPass.getText().toString()))){
            //SET UP SESSION VARIABLE
            config userConfig = new config(true,edtEmail.getText().toString());
            writeState(this,userConfig);
            openBrowse();
        }
        else{
            Toast.makeText(this, "Incorrect User email Password combination", Toast.LENGTH_SHORT).show();
        }
    }

    private void openBrowse(){
        Intent intent = new Intent(this, browse.class);
        startActivity(intent);
    }

    private void openRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    public static String hashText(String pass){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for(byte b:hash){
                result.append(String.format("%02x",b));
            }
            System.out.println(result.toString());
            return result.toString();

        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public interface VolleyCallBack{
        void onSuccess();
    }

}
