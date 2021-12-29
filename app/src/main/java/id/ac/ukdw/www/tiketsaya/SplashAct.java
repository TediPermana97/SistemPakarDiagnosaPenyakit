package id.ac.ukdw.www.tiketsaya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import id.ac.ukdw.www.tiketsaya.SistemPakar.DatabaseHelper;

public class SplashAct extends AppCompatActivity {

    Animation app_splash,btt;
    ImageView app_logo;
    TextView app_subtitle;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // load database
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        // update database, jika versi yang di set di databasehelper lebih baru
        try {
            mDBHelper.updateDatabase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }

        //load animasi ke activity
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        //load element
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);

        //run animation
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btt);

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");

        if(username_key_new.isEmpty()){
            //setting timer untuk 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //ganti activity
                    Intent gogetstarted = new Intent(SplashAct.this,GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            },5000);
        }else{
            //setting timer untuk 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //ganti activity
                    Intent gotohome = new Intent(SplashAct.this,HomeAct.class);
                    startActivity(gotohome);
                    finish();
                }
            },5000);
        }



    }
}
