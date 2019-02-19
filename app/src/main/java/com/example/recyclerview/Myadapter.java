package com.example.recyclerview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.example.recyclerview.R.drawable.ic_arrow;
import static java.security.AccessController.getContext;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;
    private static final int GALLERY_KITKAT_INTENT_CALLED=100;
    private static final int GALLERY_INTENT_CALLED=100;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    ImageView imageView;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;

    // private static final Object ListItem = 7;
    public List<ListItem> listItems;
    private Context context;
   // public static final int=PICK_IMAGE_REQUEST;

    public Myadapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data item for this position

        if (position == 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  switchfragment(new Displayfragment);
//                    Displayfragment displayfragment=new Displayfragment();
//                    fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, displayfragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
            });
        } else if (position == 1) {

            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  switchfragment(new Displayfragment());
                    Displayfragment displayfragment=new Displayfragment();
                    fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, displayfragment);
                    fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();

                }
            });




        } else if (position == 2) {
            int color = ContextCompat.getColor(context, R.color.karnataka);
            holder.cardView.setCardBackgroundColor(color);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                    /* Fill it with Data */
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                    /* Send it off to the Activity-Chooser */
                    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
            });
        } else if (position == 3) {
            int color = Color.RED;
            holder.cardView.setCardBackgroundColor(color);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri uri = Uri.parse("smsto:0800000123");
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "The SMS text");
                    ((Activity)context).startActivity(it);
                   //startActivity(it);
                }
            });
        }
        //int color=ContextCompat.getColor(context,R.color.kerala);
        else if (position == 4) {
            int myColor = ContextCompat.getColor(context, R.color.colorAccent);
            holder.cardView.setCardBackgroundColor(myColor);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /*String posted_by = "111-333-222-4";
                   String uri = "tel:" + posted_by ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    ((Activity)context).startActivity(intent);*/
                    if(isPermissionGranted()){
                        call_action();
                    }


                }
            });
        }
        else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.kerala));

        }
        ListItem list = listItems.get(position);
        //this line is for test purpose

        holder.textViewHead.setText(list.getHead());

    }
    public void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0377778888"));
        ((Activity) context).startActivity(callIntent);
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (  ContextCompat.checkSelfPermission(context,android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    public int getItemCount() {

        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textViewHead;
        // public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.Cardview);
            textViewHead = (TextView) itemView.findViewById(R.id.textviewHead);

        }
    }


}
