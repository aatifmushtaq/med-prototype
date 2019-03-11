package com.example.aatifmushtaq.medprototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    ProgressBar uploadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ImageButton takePhotobutton = (ImageButton) findViewById(R.id.photo_prescription);
        ImageButton uploadbutton = (ImageButton) findViewById(R.id.upload_prescriptio);
        uploadProgressBar =(ProgressBar) findViewById(R.id.prescription_upload_progressbar);

      /*  ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){

                    if(objects.size()>0){
                        for(ParseUser user : objects){
                            usernames.add(user.getUsername());

                        }
                    }
                }
            }
        });*/


    }
    // json request to healthos api



    public void uploadPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    public void takePhoto(View view){
    Intent photo= new Intent("android.media.action.IMAGE_CAPTURE");
    startActivityForResult(photo,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customeractivity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(intent);
        }
        if(id== R.id.action_sellers_available){
            Intent intent = new Intent(getApplicationContext(),SelectPharmacyActivity.class);
            startActivity(intent);
        }
        if(id == R.id.logout_item){
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uploadProgressBar.setVisibility(View.VISIBLE);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                //upload to parse server

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100 , stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile file =new ParseFile("image.png",byteArray);
                ParseObject object = new ParseObject("Image");
                object.put("image",file);
                object.put("username",ParseUser.getCurrentUser().getUsername());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CustomerActivity.this,"prescription saved",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(CustomerActivity.this,"prescription not  saved",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
