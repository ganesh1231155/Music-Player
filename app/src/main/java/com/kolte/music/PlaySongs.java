package com.kolte.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlaySongs<ubdateSeekBar> extends AppCompatActivity {
    TextView songText,currentTime,totalTime,p;
     ImageView forward,backward,icon,repeat,headline;
    public static ImageView pause,previous,next;
    SeekBar seekBar;
    String[] songs;
    static  int x=0;
    static MediaPlayer mediaPlayer;
    //static int change_seek=0;
    String currentSong="";
    String[] songNames;

   // MainActivity m=new MainActivity();
    Animation animShake;
    Animation animation;
    boolean a=true;
    int position;
    int currentPosition=0;
    static Thread updateSeek;

    @Override
    protected void onDestroy() {
        super.onDestroy();

            //mediaPlayer.stop();
            //mediaPlayer.release();
            //updateSeek.interrupt();
            //MainActivity.onchange(position);
            FirstActivity.lastSongmain.setVisibility(View.VISIBLE);
            FirstActivity.imgicon.setVisibility(View.VISIBLE);
            FirstActivity.pre.setVisibility(View.VISIBLE);
            FirstActivity.ply.setVisibility(View.VISIBLE);
            FirstActivity.nex.setVisibility(View.VISIBLE);
            FirstActivity.lastSongmain.setText(songNames[position]);
            MainActivity.lastSong.setVisibility(View.VISIBLE);
            MainActivity.img.setVisibility(View.VISIBLE);
            MainActivity.pre.setVisibility(View.VISIBLE);
            MainActivity.ply.setVisibility(View.VISIBLE);
            MainActivity.nex.setVisibility(View.VISIBLE);
            MainActivity.lastSong.setText(songNames[position]);

    }

    public static void changePosition() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_songs);
        pause=findViewById(R.id.pause);
        forward=findViewById(R.id.forward);
        backward=findViewById(R.id.backward);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        repeat=findViewById(R.id.repeat);
        icon=findViewById(R.id.icon);

        songText=findViewById(R.id.songText);
        currentTime=findViewById(R.id.currentTime);
        totalTime=findViewById(R.id.totalTime);
        seekBar=findViewById(R.id.seekBar);
        animation=AnimationUtils.loadAnimation(PlaySongs.this,R.anim.rotation);
        animShake = AnimationUtils.loadAnimation(PlaySongs.this, R.anim.shake);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        //songs=bundle.getStringArrayList("Urls");
        songs=bundle.getStringArray("Urls");
        songNames=bundle.getStringArray("Songs");
        currentSong=intent.getStringExtra("Current_Song");
        position=intent.getIntExtra("Position",0);
        songText.setText(currentSong);

                if(x==0) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        updateSeek.interrupt();

                    }
                    Uri uri=Uri.parse(songs[position]);
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.reset();
                    mediaPlayer=MediaPlayer.create(this,uri);

                    mediaPlayer.start();
                }
                else
                {
                    if(mediaPlayer!=null) {
                        if(mediaPlayer.isPlaying())
                        {
                            pause.setImageResource(R.drawable.pause);
                        }
                        else
                        {
                            pause.setImageResource(R.drawable.play);
                        }
                    }
                }
                songText.setText(currentSong);
                icon.startAnimation(animation);
                seekBar.setMax(mediaPlayer.getDuration());
                String minutes = "" + (mediaPlayer.getDuration() / 1000) / 60;
                String seconds = "" + (mediaPlayer.getDuration() / 1000) % 60;

                totalTime.setText(minutes+":"+seconds);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a) {
                    icon.startAnimation(animShake);
                    a=false;
                }
                else
                {
                    icon.startAnimation(animShake);            a=true;

                }
                icon.startAnimation(animation);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);

                    seekBar.setProgress(progress);

                    icon.startAnimation(animShake);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                icon.startAnimation(animShake);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                timing();
                icon.startAnimation(animShake);
                pause.callOnClick();
                pause.callOnClick();

            }
        });

        updateSeek = new Thread()
        {
            @Override
            public void run() {
                int currentPosition=0;
                try{
                    while(currentPosition<mediaPlayer.getDuration())
                    {
                        // Changing Current Time And Total Time per sec.

                        currentPosition=mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        timing();

                        sleep(990);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pause.startAnimation(animShake);
                if(mediaPlayer.isPlaying())
                {
                    pause.setImageResource(R.drawable.play);
                    MainActivity.ply.setImageResource(R.drawable.play);
                    FirstActivity.ply.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                    icon.clearAnimation();
                }
                else
                {
                    pause.setImageResource(R.drawable.pause);
                    MainActivity.ply.setImageResource(R.drawable.pause);
                    FirstActivity.ply.setImageResource(R.drawable.pause);

                    mediaPlayer.start();
                    icon.startAnimation(animation);
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                previous.startAnimation(animShake);
                if(position!=0)
                {
                    position--;
                }
                else
                {
                    position= songs.length-1;
                }

                icon.startAnimation(animation);
                if(MainActivity.lastSong.getVisibility()==View.VISIBLE) {
                    MainActivity.lastSong.setText(songNames[position]);
                    FirstActivity.lastSongmain.setText(songNames[position]);
                }
                songText.setText(songNames[position]);
               // m.current_song.setText(songText.getText().toString().replace(".mp3",""));
                Uri uri=Uri.parse(songs[position]);
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();;
                seekBar.setMax(mediaPlayer.getDuration());
                timing();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                next.startAnimation(animShake);

                if(position!= songNames.length-1)
                {
                    position++;
                }
                else
                {
                    position=0;
                }

                icon.clearAnimation();
                icon.startAnimation(animation);
                if(MainActivity.lastSong.getVisibility()==View.VISIBLE) {
                    MainActivity.lastSong.setText(songNames[position]);
                    FirstActivity.lastSongmain.setText(songNames[position]);
                }
                songText.setText(songNames[position]);

               // m.current_song.setText(songText.getText().toString().replace(".mp3",""));

                Uri uri=Uri.parse(songs[position]);
//                MainActivity m=new MainActivity();
//                m.current_song.setText(""songText);
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                timing();
            }
        });



        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animShake = AnimationUtils.loadAnimation(PlaySongs.this, R.anim.shake);
                icon.startAnimation(animShake);
                icon.clearAnimation();
                forward.startAnimation(animShake);
                if (currentPosition < mediaPlayer.getDuration() - 5000) {

                    currentPosition = mediaPlayer.getCurrentPosition() + 5000;
                }
                else {
                    currentPosition=mediaPlayer.getDuration();
                }
                seekBar.setProgress(currentPosition);
                mediaPlayer.seekTo(seekBar.getProgress());
                icon.startAnimation(animation);
//                timing();
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                icon.startAnimation(animShake);
                icon.clearAnimation();
                backward.startAnimation(animShake);
                if (currentPosition > 5000) {

                    currentPosition = mediaPlayer.getCurrentPosition() - 5000;
                }
                else
                {
                    currentPosition=0;
                }
                seekBar.setProgress(currentPosition);
                mediaPlayer.seekTo(seekBar.getProgress());

                icon.startAnimation(animation);

//                        timing();
            }
        });


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!a) {
                    repeat.setImageResource(R.drawable.repeat);
                    a=true;
                    mediaPlayer.setLooping(true);

                }
                else
                {
                    repeat.setImageResource(R.drawable.shuffle);
                    a=false;
                    mediaPlayer.setLooping(false);
                }

            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                next.callOnClick();
            }
        });


    }



    private void timing() {

        String minutes = "" + (mediaPlayer.getCurrentPosition() / 1000) / 60;
        String seconds = "" + (mediaPlayer.getCurrentPosition() / 1000) % 60;
        String tminutes = "" + ((mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition()) / 1000) / 60;
        String tseconds = "" + ((mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition()) / 1000) % 60;
        if(tminutes.length()<2)
        {
            tminutes="0"+tminutes;
        }
        if(tseconds.length()<2)
        {
            tseconds="0"+tseconds;
        }
        if(minutes.length()<2)
        {
            minutes="0"+minutes;
        }
        if(seconds.length()<2)
        {
            seconds="0"+seconds;
        }
        currentTime.setText(minutes+":"+seconds);
        totalTime.setText(tminutes+":"+tseconds);

    }
//    public static Play_Song getInstance() {
//        return instance;
//    }
//
//    public String myMethod() {
//        return songs.get(position).toString();
//    }



}