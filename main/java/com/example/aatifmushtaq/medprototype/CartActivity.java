package com.example.aatifmushtaq.medprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView medicinesInCart = (ListView) findViewById(R.id.medicinesInCart);

        ArrayList<String> medicines = new ArrayList<String>();
        medicines.add("crocin");
        medicines.add("neopeptine");
        medicines.add("calpal");
        medicines.add("morphine");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,medicines);
        medicinesInCart.setAdapter(arrayAdapter);
    }
}
