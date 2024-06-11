package com.kolte.music;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import  android.content.Intent;
import android.Manifest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    boolean checked = false;
    public List SongName = new ArrayList();
    public List SongUrl = new ArrayList();
    static String option="";
    //public ArrayList<String> SongUrl;
    String[] items;
    String[] Urls;
    String currentSong;
    static TextView lastSong;
    static ImageView img,pre,ply,nex;
    public static int a = 0;
    public static int b=0;
    Animation animShake;

   @Override
    protected void onDestroy() {
        super.onDestroy();
       FirstActivity.lastSongmain.setText(lastSong.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // PlaySongs.mediaPlayer=new MediaPlayer();
        listView = findViewById(R.id.listView);
        lastSong=findViewById(R.id.LastSong);
        img=findViewById(R.id.imageView);
        pre=findViewById(R.id.pre);
        nex=findViewById(R.id.nex);
        ply=findViewById(R.id.ply);
        animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);


        if (checkPermission()) {

            findData();

        }

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre.startAnimation(animShake);
                PlaySongs.previous.callOnClick();
                ply.setImageResource(R.drawable.pause);
                FirstActivity.lastSongmain.setText(lastSong.getText().toString());

            }
        });

         nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nex.startAnimation(animShake);
                PlaySongs.next.callOnClick();
                ply.setImageResource(R.drawable.pause);
                FirstActivity.lastSongmain.setText(lastSong.getText().toString());

            }
        });

         ply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ply.startAnimation(animShake);
                 PlaySongs.pause.callOnClick();
                 if(PlaySongs.mediaPlayer.isPlaying())
                 {
                     ply.setImageResource(R.drawable.pause);
                     FirstActivity.ply.setImageResource(R.drawable.pause);

                 }
                 else
                 {
                     ply.setImageResource(R.drawable.play);
                     FirstActivity.ply.setImageResource(R.drawable.play);
                 }

             }
         });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSong = items[position];
                a = position;
                PlaySongs.x=0;


                /*else
                {

                    Uri uri=Uri.parse(Urls[position]);
                    PlaySongs.mediaPlayer=new MediaPlayer();
                    PlaySongs.mediaPlayer.reset();
                    PlaySongs.mediaPlayer=MediaPlayer.create(MainActivity.this,uri);
                }*/

                parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.teal_200));

                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.black));
                }

                //Toast.makeText(MainActivity.this, "Clicked on "+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, PlaySongs.class);
                intent.putExtra("Songs", items);
                intent.putExtra("Urls", Urls);
                intent.putExtra("Current_Song", currentSong);
                intent.putExtra("Position", position);
                startActivity(intent);


            }
        });

        lastSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySongs.x=1;
                PlaySongs.mediaPlayer.seekTo(PlaySongs.mediaPlayer.getCurrentPosition());
                currentSong=items[b];
                Intent intent = new Intent(MainActivity.this, PlaySongs.class);
                intent.putExtra("Songs", items);
                intent.putExtra("Urls", Urls);
                intent.putExtra("Current_Song", currentSong);
                intent.putExtra("Position", b);
                startActivity(intent);
            }
        });


    }

    private void findData() {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("option");
        //Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Song songObject = ds.getValue(Song.class);
                        SongName.add(songObject.getSongName());
                        //Toast.makeText(MainActivity.this, songObject.getSongName(), Toast.LENGTH_SHORT).show();
                        SongUrl.add(songObject.getSongUrl());
                    }
                    items = new String[SongName.size()];
                    Urls = new String[SongUrl.size()];
                    for (int i = 0; i < SongName.size(); i++) {
                        items[i] = (String) SongName.get(i).toString();
                        Urls[i] = (String) SongUrl.get(i).toString();
                        //Toast.makeText(MainActivity.this,  SongName.get(i).toString(), Toast.LENGTH_SHORT).show();
                    }
                    CustomAdapter ad = new CustomAdapter(MainActivity.this, R.layout.layout1, items);
                    //ArrayAdapter ad=new ArrayAdapter(MainActivity.this, R.layout.layout1,SongName);
                    listView.setAdapter(ad);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error Occured.", Toast.LENGTH_SHORT).show();
                }
            });
        }






    /*public static void onchange(int a) {
       b=a;
       if(lastSong.getVisibility()==View.INVISIBLE) {
           lastSong.setVisibility(View.VISIBLE);
           img.setVisibility(View.VISIBLE);
           previous.setVisibility(View.VISIBLE);
           play.setVisibility(View.VISIBLE);
           next.setVisibility(View.VISIBLE);
       }
        lastSong.setText(items[a]);
    }*/



    public boolean checkPermission()
    {
        Dexter.withContext(MainActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        checked=true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        return checked;
    }

}