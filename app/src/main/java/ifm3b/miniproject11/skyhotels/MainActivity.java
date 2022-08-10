package ifm3b.miniproject11.skyhotels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ifm3b.miniproject11.skyhotels.models.config;

import static ifm3b.miniproject11.skyhotels.models.config.readState;

public class MainActivity extends AppCompatActivity {
    public static String URL = "http://192.168.8.15:4040";
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //checkUserData();
                openLogin();
            }
        },SPLASH_TIME_OUT);

    }

    private void checkUserData(){
        config userConfig = readState(this);
        if(userConfig == null){
            openRegister();
        }else
        if (userConfig.isLogged()){
            openBrowse();
        }else{
            openLogin();
        }
    }

    private void openLogin(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }
    private void openRegister(){
        Intent intent = new Intent(this,register.class);
        startActivity(intent);
    }

    private void openBrowse(){
        Intent intent = new Intent(this, browse.class);
        startActivity(intent);
    }
}
