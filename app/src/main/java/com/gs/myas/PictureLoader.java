package com.gs.myas;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import android.os.*;


public class PictureLoader {
    private ImageView loadImg;
    private String imgUrl;
    private byte[] picByte;


          Handler handler=new Handler(new Handler.Callback() {
              @Override
              public boolean handleMessage(Message msg) {
                  if (msg.what==(0x123))
                      setImg();
                  return true;
              }
          });

        public void setImg() {
            if (picByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                loadImg.setImageBitmap(bitmap);
            }
        }


    public void load(ImageView loadImg, String imgUrl) {
        this.loadImg = loadImg;
        this.imgUrl = imgUrl;
        Drawable drawable = loadImg.getDrawable();
        if(drawable != null && drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            if(bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(imgUrl);
                System.out.println("url-------------"+url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try {
                    conn.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                conn.setReadTimeout(10000);
                try {
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int length = -1;
                        while ((length = in.read(bytes)) != -1) {
                            out.write(bytes, 0, length);
                        }
                        picByte = out.toByteArray();
                        in.close();
                        out.close();
                        handler.sendEmptyMessage(0x123);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}