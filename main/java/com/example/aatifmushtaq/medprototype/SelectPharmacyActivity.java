package com.example.aatifmushtaq.medprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SelectPharmacyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

   private List sellersList;
   // ListView sellersAvailableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pharmacy);
        sellersList= new ArrayList<>();


        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sellersList);
        sellersAvailableListView = findViewById(R.id.sellers_available_listview);
        sellersAvailableListView.setAdapter(adapter);*/

       updateSellersList();
        Log.i("sellersAvailable out",sellersList.toString());
        mRecyclerView = (RecyclerView) findViewById(R.id.select_pharmacy_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this,sellersList);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void updateSellersList(){
        sellersList.clear();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("AvailableSellers");
        Log.i("user",ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("forCustomer", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseObject object: objects){
                            String sellersAvailable = (String) object.get("sellersAvailable");
                            sellersList.add(sellersAvailable);
                            Log.i("sellersAvailable",sellersList.toString());

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.i("sellers","notavai;anle");
                    }
                }

            }
        });
    }

}
