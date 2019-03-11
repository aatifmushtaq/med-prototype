package com.example.aatifmushtaq.medprototype;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    ArrayAdapter orderAdapter;
    TextView orderedBy;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        final ArrayList<Bitmap> orderImagesArray = new ArrayList<Bitmap>();
       orderedBy = findViewById(R.id.ordered_by);

        orderAdapter = new CustomAdapter(this);
        Log.i("no of items",Integer.toString(orderAdapter.getCount()));
        listView = (ListView) findViewById(R.id.order_list);
        listView.setAdapter(orderAdapter);
        Button inStockButton = (Button) findViewById(R.id.in_stock);


        //swipe to refresh listview
        final SwipeRefreshLayout sellerSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

            sellerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    orderAdapter.clear();
                    getOrdersFromServer();
                    sellerSwipeRefreshLayout.setRefreshing(false);
                }
            });

        //get image from aws

            getOrdersFromServer();
        //String[] order ={"one","two","three"};


    }

    public void inStock(View view){

        View parentRow = (View) view.getParent();
        final int position = listView.getPositionForView(parentRow);
      OrderDetails orderDetails= (OrderDetails) listView.getItemAtPosition(position);
      Log.i("current username",orderDetails.getUserName());

        String sellerAvailable =(String) ParseUser.getCurrentUser().getUsername();

        //Log.i("available", sellerAvailable);
       ParseObject object = new ParseObject("AvailableSellers");
        object.put("sellersAvailable",sellerAvailable);
        object.put("forCustomer",orderDetails.getUserName());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){

                    Toast.makeText(SellerActivity.this,"Order Set Available",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(SellerActivity.this,"Order set  not  saved",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getOrdersFromServer(){
       // final ImageUtil imageUtil= new ImageUtil();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){

                    if(objects.size()>0){
                        for(ParseObject object : objects){

                            OrderDetails orderDetails = new OrderDetails();
                             String orderedBy = object.getString("username");
                             orderDetails.setUserName(orderedBy);
                            ParseFile file = (ParseFile) object.get("image");
                            try {
                                byte[] data = file.getData();
                                orderDetails.setImageBytes(data);

                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                          /* file.getData(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if(e==null && data != null){
                                        Log.i("bitmap", Arrays.toString(data));
                                        orderDetails.setImageBytes(data);


                                       // Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                        // orderDetails.setBitmap(bitmap);
                                           // orderAdapter.add(bitmap);




                                        // orderAdapter.add(bitmap);
                                    }

                                }
                            });*/
                            //orderDetails.setUserName(orderedBy);
                           orderAdapter.add(orderDetails);
                        Log.i("bitmap", Arrays.toString(orderDetails.getImageBytes()));



                           // orderAdapter.addAll(orderedBy,bitmap);
                          Log.i("username",orderDetails.getUserName());

                        }
                    }
                }
            }
        });
    }


}
