package com.kolte.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    ImageView bollywood_remix,party_music,love_songs,sad_songs,badshah,arijit,guru,desi_collab,neha_kakkar,marathi_song,workout,latahits,oldhits,allgod;
    ImageView online,offline;
    static ImageView imgicon,pre,nex,ply;
    static TextView lastSongmain;
    Animation animShake;
    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bollywood_remix=findViewById(R.id.bollywoodremix);
        party_music=findViewById(R.id.party);
        love_songs=findViewById(R.id.love);
        sad_songs=findViewById(R.id.sad);
        badshah=findViewById(R.id.badshah);
        arijit=findViewById(R.id.arijit);
        guru=findViewById(R.id.guru);
        desi_collab=findViewById(R.id.desicollab);
        neha_kakkar=findViewById(R.id.nehakakkar);
        marathi_song=findViewById(R.id.marathi);
        workout=findViewById(R.id.workout);
        latahits=findViewById(R.id.latahits);
        oldhits=findViewById(R.id.oldhits);
        allgod=findViewById(R.id.allgod);
        offline=findViewById(R.id.offline);
        online=findViewById(R.id.online);
        pre=findViewById(R.id.pre);
        ply=findViewById(R.id.ply);
        nex=findViewById(R.id.nex);
        animShake = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.shake);

        lastSongmain=findViewById(R.id.lastsong);
        imgicon=findViewById(R.id.imgicon);
        if(lastSongmain.getText().toString()=="")
        {
            lastSongmain.setVisibility(View.INVISIBLE);
            imgicon.setVisibility(View.INVISIBLE);
        }
        lastSongmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // PlaySongs.x=1;
               // PlaySongs.mediaPlayer.seekTo(PlaySongs.mediaPlayer.getCurrentPosition());
                MainActivity.lastSong.callOnClick();
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.pre.callOnClick();
                ply.setImageResource(R.drawable.pause);
            }
        });
        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.nex.callOnClick();
                ply.setImageResource(R.drawable.pause);
            }
        });
        ply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ply.callOnClick();
            }
        });


        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlaySongs.mediaPlayer!=null)
                {
                    PlaySongs.mediaPlayer.stop();
                    PlaySongs.mediaPlayer.reset();
                    PlaySongs.mediaPlayer.release();
                }
                Toast.makeText(FirstActivity.this, "It will take some time.", Toast.LENGTH_SHORT).show();
              Intent i=new Intent(FirstActivity.this,MainActivity2.class);
              startActivity(i);
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this, "Already in Online mode.", Toast.LENGTH_SHORT).show();
            }
        });

        bollywood_remix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.option="Bollywood_remix";
                Intent i=new Intent(FirstActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
         party_music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Party_Music";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         love_songs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Love_Song";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
          sad_songs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Love_Song";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
          badshah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Badshah";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
          arijit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Arijit_Singh";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
          guru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Guru_Randhawa";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
          desi_collab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Desi_Collab";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         neha_kakkar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Neha_Kakkar";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         marathi_song.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Marathi_Song";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         workout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Workout";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         oldhits.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Old_Hits";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         latahits.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="Lata_Mangashker";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
         allgod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.option="God_Songs";
                        Intent i=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });


    }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        if(Play_Song.mediaPlayer!=null)
        {
            Play_Song.mediaPlayer.stop();
            Play_Song.mediaPlayer.reset();
            Play_Song.mediaPlayer.release();
        }
        Intent i=new Intent(FirstActivity.this,FirstActivity.class);
        startActivity(i);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Access Restricted.", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}