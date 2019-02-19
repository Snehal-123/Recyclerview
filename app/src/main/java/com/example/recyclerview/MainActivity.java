package com.example.recyclerview;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {
   // private static final String URL_DATA="https://simplifiedcoding.net/demos/marvel/";
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listitems;
    private ImageView imageView;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    public static final int RequestPermissionCode = 7;

   String[] states={"Gallery","Camera","Gmail","SMS","Call"};
 // String[] capital={"Mumbai","Jaipur","Bhopal","Gandhinagar","Thiruvananthapuram","Bengaluru"};
   // ListItem listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // cardView=(CardView)findViewById(R.id.Cardview);
        //imageView =(ImageView) findViewById(R.id.imageview);
        FragmentManager fragmentManager=getSupportFragmentManager();
        if(findViewById(R.id.frame)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
        }
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Displayfragment displayfragment=new Displayfragment();
        fragmentTransaction.add(R.id.frame,displayfragment);
        //getFragmentManager().addOnBackStackChangedListener(null);
        fragmentTransaction.commit();
        listitems= new ArrayList<>();
        for (int i=0;i<=4; i++) {

       ListItem listItem=new ListItem(states[i]);

          // ListItem listItem = new ListItem("heading" + (i + 1), "dummy text");
           // listitems.add(listItem);
            listitems.add(listItem);
        }
        adapter=new Myadapter(listitems,this);
        recyclerView.setAdapter(adapter);
        //////////////////////////////////////////////////////////////////////

        if(CheckingPermissionIsEnabledOrNot())
        {
            Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        }

        // If, If permission is not enabled then else condition will execute.
        else {

            //Calling method to enable permission.
            RequestMultiplePermission();

        }


    }//oncreate
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED ;

    }
    public void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                        CALL_PHONE

                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean CallPhonePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (CameraPermission && CallPhonePermission ) {

                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }



   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
