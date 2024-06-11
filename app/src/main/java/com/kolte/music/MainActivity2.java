package com.kolte.music;

import  androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {
    ListView listView;
    public static TextView current_song;
    LinearLayout last;
    String[] items;
    static String currentSong;
    static ImageView pre,ply,nex;
    ImageView offline,online;
    int pos;
    Animation animShake;
    TextView noSong;
    boolean a=true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Play_Song.mediaPlayer!=null) {
           if(Play_Song.mediaPlayer.isPlaying() ) {
             Play_Song.mediaPlayer.pause();
            // Play_Song.mediaPlayer.stop();
            }
           // Play_Song.mediaPlayer.reset();
            //Play_Song.mediaPlayer.release();
           // moveTaskToBack(true);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.listView);
        current_song=findViewById(R.id.clicked_song);
        last=findViewById(R.id.last);
        pre=findViewById(R.id.pre);
        ply=findViewById(R.id.ply);
        nex=findViewById(R.id.nex);
        offline=findViewById(R.id.offline);
        online=findViewById(R.id.online);
        noSong=findViewById(R.id.noSong);
        animShake = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.shake);

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Already in offline mode.", Toast.LENGTH_SHORT).show();
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(Play_Song.mediaPlayer!=null)
                {
                    Play_Song.mediaPlayer.stop();
                    Play_Song.mediaPlayer.reset();
                    Play_Song.mediaPlayer.release();
                }*/
                onDestroy();

                finish();

            }
        });

        runPermission();
//        current_song.setText(Play_Song.getInstance().myMethod());

    }
    public void runPermission()
    {
       /* Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {*/
                ArrayList<File> mySongs=findSongs(Environment.getExternalStorageDirectory());
                items=new String[mySongs.size()];
                for(int i=0;i< mySongs.size();i++)
                {
                    items[i]=mySongs.get(i).getName().replace(".mp3","").replace(".wav","");
                }
                current_song.setText("");
                if(mySongs.size()==0)
                {
                    noSong.setVisibility(View.VISIBLE);
                    noSong.setText("No Songs Found on your device.");
                    Toast.makeText(this, "No Songs found on your device.", Toast.LENGTH_SHORT).show();
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                            Toast.makeText(MainActivity.this, "clicked on "+position, Toast.LENGTH_SHORT).show();
                                                        currentSong=items[position];
                                                        last.setVisibility(View.VISIBLE);
//                                                            if(1) {
//                                                                Play_Song p = new Play_Song();
//                                                                p.alpha();
//                                                            }
                                                        Play_Song.x=1;
                                                        Intent intent=new Intent(MainActivity2.this,Play_Song.class);
                                                        intent.putExtra("Songs",mySongs);
                                                        intent.putExtra("current_Song",currentSong);
                                                        intent.putExtra("position",position);
                                                        startActivity(intent);
                                                        current_song.setText(items[position]);
                                                    }
                                                }
                );
                last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<mySongs.size();i++) {
                            if(items[i].equals(current_song.getText().toString())) {
                                Play_Song.mediaPlayer.seekTo(Play_Song.mediaPlayer.getCurrentPosition());
                                Play_Song.x=0;
                                //Toast.makeText(MainActivity.this, "pos=" + i, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity2.this, Play_Song.class);
                                intent.putExtra("Songs", mySongs);
                                intent.putExtra("current_Song", items[i]);
                                intent.putExtra("position", i);
                                startActivity(intent);
                                current_song.setText(items[i]);
                            }
                        }

                    }
                });

                pre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Play_Song.previous.callOnClick();
                        ply.setImageResource(R.drawable.pause);
                    }
                });

                ply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Play_Song.pause.callOnClick();

                    }
                });
                nex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Play_Song.next.callOnClick();
                        ply.setImageResource(R.drawable.pause);

                    }
                });



                CustomAdapter ad=new CustomAdapter(MainActivity2.this,R.layout.items_layout2,items);
                listView.setAdapter(ad);



    }

    public ArrayList<File> findSongs(File file)
    {
        ArrayList<File> arrayList=new ArrayList<>();
//        File[] songs=  file.listFiles();
        if(file.listFiles()!=null)
        {
            for(File myFile: file.listFiles())
            {
                if(myFile.isDirectory() || (!myFile.isHidden()))
                {
                    arrayList.addAll(findSongs(myFile));
                }
//                else
//                {
                if(myFile.getName().endsWith(".mp3")||myFile.getName().endsWith(".wav"))/*&& (!(myFile.getName().startsWith(".")))*/
                {
                    arrayList.add(myFile);
                }
//                }
            }

        }

        return arrayList;
    }



    class My_Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.items_layout2, null);
            TextView txtSong = view.findViewById(R.id.song_name);
            txtSong.setSelected(true);
            txtSong.setText(items[position]);
            return view;

        }
    }

}