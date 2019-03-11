package com.example.aatifmushtaq.medprototype;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<OrderDetails> {

    public CustomAdapter(@NonNull Context context) {
        super(context,R.layout.order_list_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View customView = listInflater.inflate(R.layout.order_list_item,parent,false);

        OrderDetails orderDetails = getItem(position);
        /*Bitmap bitmap = (Bitmap) getItem(position);
        ImageView orderImage = (ImageView) customView.findViewById(R.id.order_image);
         orderImage.setImageBitmap(bitmap);*/

       //String orderedBy =(String) getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.ordered_by);
        ImageView imageView =(ImageView) customView.findViewById(R.id.order_image);
        textView.setText(orderDetails.getUserName());
        byte[] data = orderDetails.getImageBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        imageView.setImageBitmap(bitmap);


    return customView;
    }
}
