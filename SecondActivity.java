package com.example.user.videoplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST=1;
    VideoView videoViewid;
    ListView listViewid;
    MediaController mediaController;
   ArrayList<String>arrayList=new ArrayList<>();
    public Button button1,button2;
    //ListView listView;
    ArrayList<String>videoList;
   // ArrayAdapter adapter;
    ArrayAdapter<String>adapter;
    public void getVideo()
    {
        ContentResolver contentResolver=getContentResolver();
        Uri videoUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor videocursor= contentResolver.query(videoUri,null,null,null,null);
        if(videocursor!=null && videocursor.moveToFirst() )
        {
            int videoTitle=videocursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            int videoArtist=videocursor.getColumnIndex(MediaStore.Video.Media.ARTIST);
            do {
                String currentTitle=videocursor.getString(videoTitle);
                String currentArtist=videocursor.getString(videoArtist);
                String currentPath = videocursor.getString(videocursor.getColumnIndex(MediaStore.Video.Media.DATA));
                //videoList.add(currentTitle + "\n" + currentArtist);
                videoList.add(currentPath);


            }
            while (videocursor.moveToNext());
        }
    }
    public  void doStuff()
    {
        listViewid=(ListView)findViewById(R.id.listViewid);
        videoList=new ArrayList<>();
        getVideo();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,videoList);
        listViewid.setAdapter(adapter);
        registerForContextMenu(listViewid);
        videoViewid=(VideoView)findViewById(R.id.videoViewid);
        mediaController=new MediaController(this);
        mediaController.setAnchorView(videoViewid);
        videoViewid.setMediaController(mediaController);
        videoViewid.start();

        listViewid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = videoList.get(position);
                Log.d("demo", path);
                videoViewid.setVideoPath(path);
                mediaController.setAnchorView(videoViewid);
                videoViewid.setMediaController(mediaController);
                videoViewid.start();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d("demo: MainActivity: ","arrived");

        if(ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(SecondActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(SecondActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
            else
            {
                ActivityCompat.requestPermissions(SecondActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }

        }
        else
        {
            doStuff();
        }


    }
    private void saveData()
    {
        SharedPreferences sharedPreferences= getSharedPreferences("shared preferences ", MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

    }

   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo obj=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
       int index=obj.position;
        switch (item.getItemId())
        {

            case R.id.delete:
            videoList.remove(obj.position);
                Toast.makeText(this," Deleted ",Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();



                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(SecondActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this,"Permission granted! ",Toast.LENGTH_SHORT).show();
                        doStuff();
                    }
                }
                else
                {
                    Toast.makeText(this,"Not granted !",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}
