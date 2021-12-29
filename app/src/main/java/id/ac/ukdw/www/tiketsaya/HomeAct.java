package id.ac.ukdw.www.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import id.ac.ukdw.www.tiketsaya.SistemPakar.DaftarPenyakitActivity;
import id.ac.ukdw.www.tiketsaya.SistemPakar.DiagnosaActivity;
import id.ac.ukdw.www.tiketsaya.SistemPakar.TentangAplikasiActivity;

public class HomeAct extends AppCompatActivity {

    CircleView btn_to_profile;
    ImageView photo_home_user ;
    TextView user_balance, nama_lengkap, bio;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    LinearLayout btn_mulai_diagnosa, btn_daftar_penyakit, btn_tentang_aplikasi;
    public Intent myIntent;
    public String type, text, toNumber, nama_dokter;


    public Button v_call_Button1, v_call_Button2;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //langsung mulai untuk mangambil username lokal
        getUsernameLocal();

        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);


        btn_to_profile = findViewById(R.id.btn_to_profile);
        btn_mulai_diagnosa = findViewById(R.id.btn_mulai_diagnosa);
        btn_daftar_penyakit = findViewById(R.id.btn_daftar_penyakit);
        btn_tentang_aplikasi = findViewById(R.id.btn_tentang_aplikasi);

        v_call_Button2 = (Button) findViewById(R.id.btn_dokter2);
        v_call_Button1 = (Button) findViewById(R.id.btn_dokter1);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
               // user_balance.setText("$"+dataSnapshot.child("user_balance").getValue().toString());

                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_mulai_diagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, DiagnosaActivity.class);
                startActivity(gotoprofile);
            }
        });

        btn_daftar_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, DaftarPenyakitActivity.class);
                startActivity(gotoprofile);
            }
        });

        btn_tentang_aplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, TentangAplikasiActivity.class);
                startActivity(gotoprofile);
            }
        });

        v_call_Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_dokter = "Dokter Inisial";
                text       = "Pesan dari pasien Diagnosa Penyakit Kulit\n\nAssalamualaikum Dokter "+nama_dokter+", saya ingin berkonsultasi mengenai penyakit kulit yang saya derita, Terimakasish..\n Wassalamualaikum";
                toNumber   = "6285839376864";
                openWhatsApp(text, toNumber);
            }
        });

        v_call_Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_dokter = "Dokter Inisial";
                text       = "Pesan dari pasien Diagnosa Penyakit Kulit\n\nAssalamualaikum Dokter "+nama_dokter+", saya ingin berkonsultasi mengenai penyakit kulit yang saya derita, Terimakasish..\n Wassalamualaikum";
                toNumber   = "6285839376864";
                openWhatsApp(text, toNumber);
            }
        });

        addListenerOnButton();

    }

    public void addListenerOnButton() {


    }

    // Open Whatsap to specific Number
    public void openWhatsApp(String text, String toNumber){
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text+type));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    //onBackPressed adalah fungsi yang akan dieksekusi saat menekan tombol kembali
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        //set pesan yang akan ditampilkan
        builder.setMessage("Anda Yakin Ingin Keluar ?");
        //set positive tombol jika menjawab ya
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //jika kalian menekan tombol ya, maka otomatis akan keluar dari activity saat ini
                finish();
            }
        });
        //set negative tombol jika menjawab tidak
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //jika menekan tombol tidak, maka kalian akan tetap berada di activity saat ini
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
