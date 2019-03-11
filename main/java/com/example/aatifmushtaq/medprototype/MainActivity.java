package com.example.aatifmushtaq.medprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean sellermode = false;
    EditText username;
    EditText password;
    TextView sellerLoginTextView;
   /* public void Start(View view){
        Switch userSellerSwitch = (Switch) findViewById(R.id.userSellerSwitch);
        Log.i("switch value",String.valueOf(userSellerSwitch.isChecked()));
        String userType = "customer";
        if(userSellerSwitch.isChecked()){
            userType = "seller";
            Intent intent = new Intent(getApplicationContext(),SellerActivity.class);
            startActivity(intent);

        }
        else{
        Intent intent = new Intent(getApplicationContext(),CustomerActivity.class);
        startActivity(intent);}
        Log.i("info","redirecting as" + userType);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.paaword);
        sellerLoginTextView =(TextView) findViewById(R.id.seller_login);
        sellerLoginTextView.setOnClickListener(this);
        Button loginButton =(Button) findViewById(R.id.login_button);
        /*//Signup to aws
        ParseUser user = new ParseUser();
         user.setUsername("Aatifmushtaq");
         user.setPassword("atif1234");

         user.signUpInBackground(new SignUpCallback() {
             @Override
             public void done(ParseException e) {
                 if(e==null){
                     Log.i("Sign up"," successful");
                 }
                 else {
                     Log.i("Sign up"," Failed");
                 }
                 }});*/

         //login to aws
        //check if already loggedin
       // ParseUser.logOut();
        if(ParseUser.getCurrentUser()!= null){
            Log.i("currentuser", "userloggedin"+ParseUser.getCurrentUser().getUsername());
            Intent intent = new Intent(getApplicationContext(),CustomerActivity.class);
            startActivity(intent);
        }
        else{
            Log.i("current user","user not logged in"+ "logging in");




    }
}
    public void login(View view){
        if (sellermode==false){
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Log.i("log in"," successful");
                }
                else {
                    Log.i("login"," Failed"+e.toString());
                }
            }
        });
            ParseAnalytics.trackAppOpenedInBackground(getIntent());
            Intent intent = new Intent(getApplicationContext(),CustomerActivity.class);
            startActivity(intent);
        }
        else{
            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e==null){
                        Log.i("log in"," successful");
                    }
                    else {
                        Log.i("login"," Failed"+e.toString());
                    }
                }
            });
            Intent intent = new Intent(getApplicationContext(),SellerActivity.class);
            startActivity(intent);

        }

    }
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.seller_login){
            if(sellermode==false){
               sellermode=true;
               sellerLoginTextView.setText("login as customer");

            }
            else{
                sellermode=false;
                sellerLoginTextView.setText("log in as Seller");
            }
        }
    }
}
