package com.example.aatifmushtaq.medprototype;

import android.graphics.Bitmap;
import android.net.Uri;

public class OrderDetails {
    public OrderDetails() {
    }

    byte[] imageBytes;
    String userName;

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
