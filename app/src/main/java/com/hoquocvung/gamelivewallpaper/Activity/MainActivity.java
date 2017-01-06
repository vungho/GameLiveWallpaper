package com.hoquocvung.gamelivewallpaper.Activity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.service.wallpaper.WallpaperService;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Connection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoquocvung.gamelivewallpaper.Adapter.MainAdapter;
import com.hoquocvung.gamelivewallpaper.Adapter.MainOfflineAdapter;
import com.hoquocvung.gamelivewallpaper.Adapter.MenuAdapter;
import com.hoquocvung.gamelivewallpaper.Entity.DataStructure;
import com.hoquocvung.gamelivewallpaper.Entity.GameInfo;
import com.hoquocvung.gamelivewallpaper.Entity.MenuInfo;
import com.hoquocvung.gamelivewallpaper.Entity.ProjectContent;
import com.hoquocvung.gamelivewallpaper.Entity.WallpaperInfo;
import com.hoquocvung.gamelivewallpaper.Performance.CreateFolder;
import com.hoquocvung.gamelivewallpaper.Performance.SaveState;
import com.hoquocvung.gamelivewallpaper.R;
import com.hoquocvung.gamelivewallpaper.Service.WallPaperService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DataStructure {

    private MenuAdapter menuAdapter;
    private ArrayList<MenuInfo> menuInfos;
    private ListView listViewMenu;
    private GridView gridViewContent;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MainAdapter mainAdapter;
    private MainOfflineAdapter mainOfflineAdapter;
    private ProjectContent projectContent;
    private RecyclerView recyclerViewMain;
    private RecyclerView.LayoutManager layoutManager;
    private SaveState saveState;
    private String itemSelected;
    private GameInfo offGameInfo;
    private ProjectContent proContentDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Config the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Config data view
        proContentDownload = new ProjectContent();
        projectContent = new ProjectContent();
        CreateFolder createFolder = new CreateFolder(this);
        saveState = new SaveState(this);
        itemSelected = saveState.restoringSharePreferences();
        Log.i("item", itemSelected);

        //config drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView txtHeaderName = (TextView)findViewById(R.id.txt_header_name);
        TextView txtHeaderMess = (TextView)findViewById(R.id.txt_header_mess);
        ImageView imgHeaderStt = (ImageView)findViewById(R.id.img_header_stt);

        //config list view
        listViewMenu = (ListView)findViewById(R.id.listMainDrawer);
        menuInfos = new ArrayList<MenuInfo>();
        menuAdapter = new MenuAdapter(this, R.layout.item_main_drawer, menuInfos);
        listViewMenu.setAdapter(menuAdapter);

        //list menu event
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                String gameID = menuInfos.get(position).getId();
                Log.i("iMenu", menuInfos.get(position).toString());
                iIntContent(gameID);
            }
        });

        //config RecyclerView
        offGameInfo = new GameInfo();
        recyclerViewMain = (RecyclerView)findViewById(R.id.main_recycler_view);
        recyclerViewMain.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerViewMain.setLayoutManager(layoutManager);

        if (isOnline()){      //Có kết nối đến Internet
            Log.i("connect", "Online");
            Picasso.with(this).load(R.drawable.img_on).fit().centerCrop().into(imgHeaderStt);
            getFileDownloaded();
            //Firebase
            database = FirebaseDatabase.getInstance();
            reference = database.getReference();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot iData: dataSnapshot.getChildren()){
                        MenuInfo item = new MenuInfo();
                        item.setId(iData.getKey());
                        item.setIcon(iData.child("icon").getValue().toString());
                        item.setName(iData.child("name").getValue().toString());

                        menuInfos.add(item);
                        menuAdapter.notifyDataSetChanged();

                        GameInfo gameInfo = new GameInfo(item.getId(),
                                item.getName(),
                                item.getIcon());
                        projectContent.addGame(gameInfo);
                        if (item.getId().equalsIgnoreCase("lol")){
                            iIntContent("lol");
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {  //Không có kết nối đến internet
            Picasso.with(this).load(R.drawable.img_off).fit().centerCrop().into(imgHeaderStt);
            txtHeaderName.setText(getString(R.string.off_name));
            txtHeaderMess.setText(getString(R.string.off_mess));
            Log.i("connect", "Offline");
            iIntOfflineContent();
        }
    }


    private void iIntContent(final String gameID) {

        DatabaseReference childRef = reference.child(gameID).child(CONTENT);
        final GameInfo gameInfo = projectContent.getGameInfo(gameID);
        final  GameInfo infoOff = proContentDownload.getGameInfo(gameID);
        final String folderPath = saveState.restoringVideoSharePreferences();

        mainAdapter = new MainAdapter(R.layout.item_main, gameInfo);
        recyclerViewMain.setAdapter(mainAdapter);

        childRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot iData: dataSnapshot.getChildren()){
                   WallpaperInfo info = new WallpaperInfo();
                   info.setParent(gameID);
                   info.setId(iData.getKey());
                   info.setName(iData.child(NAME).getValue().toString());
                   info.setImage(iData.child(IMAGE).getValue().toString());
                   info.setVideo(iData.child(VIDEO).getValue().toString());

                   if (infoOff != null && infoOff.getListWallpaper() != null){
                       if (infoOff.getListWallpaper().contains(info)){
                           info.setFileUrl(folderPath +"/"+info.getParent() +"_" +info.getId()+"_"+info.getName()+ ".mp4");
                           info.setDownloadState(true);
                       }else{
                           info.setDownloadState(false);
                       }
                   }
                   //NHỚ Cấu hình item đã được chọn!!!
                   gameInfo.addWallpaper(info);
                   mainAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }

    private void iIntOfflineContent() {
        offGameInfo.setId("Offline");
        offGameInfo.setName("Offline list");

        getFileDownloadedOffline(offGameInfo);

        mainOfflineAdapter = new MainOfflineAdapter(R.layout.item_main, offGameInfo);
        recyclerViewMain.setAdapter(mainOfflineAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemSelected = saveState.restoringSharePreferences();
    }

    public boolean isOnline(){
        ConnectivityManager conManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conManager.getActiveNetworkInfo();
        return  netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void getFileDownloadedOffline(GameInfo gameInfo){
        String path = saveState.restoringVideoSharePreferences();
        File directory = new File(path);
        File[] files = directory.listFiles();
        //Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {   WallpaperInfo item = new WallpaperInfo(path +"/" +files[i].getName());
            String arr[] = (files[i].getName()).split("_");
            item.setParent(arr[0]);
            item.setId(arr[1]);
            item.setName(arr[2]);
            item.setDownloadState(true);
            gameInfo.addWallpaperOff(item);
            Log.d("Files", "FileName:" + files[i].getName());
        }
    }

    public void getFileDownloaded(){
        String path = saveState.restoringVideoSharePreferences();
        Log.d("Files", "Path: " + path);

        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);

        if (proContentDownload == null)
            proContentDownload = new ProjectContent();

        for (int i = 0; i < files.length; i++)
        {
            String arr[] = (files[i].getName()).split("_");
            GameInfo gameInfo = new GameInfo(arr[0]);
            if (!proContentDownload.getListGame().contains(gameInfo)){
                WallpaperInfo wpInfo = new WallpaperInfo(arr[1], arr[0]);
                wpInfo.setName(arr[2]);
                wpInfo.setDownloadState(true);
                wpInfo.setFileUrl(files[i].getPath());

                gameInfo.addWallpaper(wpInfo);
                proContentDownload.addGame(gameInfo);
            }else {
                WallpaperInfo wpInfo = new WallpaperInfo(arr[1], arr[0]);
                wpInfo.setName(arr[2]);
                wpInfo.setDownloadState(true);
                wpInfo.setFileUrl(files[i].getPath());

                proContentDownload.getListGame().get(
                        proContentDownload.getListGame().indexOf(gameInfo)
                ).addWallpaper(wpInfo);

            }
        }

        logDownload();
    }

    public void logDownload(){
        for (GameInfo item: proContentDownload.getListGame()){
            Log.i("game_info", item.toString());
        }
    }
}
