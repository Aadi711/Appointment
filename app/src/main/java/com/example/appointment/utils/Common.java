package com.example.appointment.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.appointment.activities.SignInActivity;
import com.example.appointment.model.User;
import com.google.gson.Gson;
import com.example.appointment.R;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Common {

    public static DateTimeFormatter serverDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MMM, dd, yyyy");
    public static DateTimeFormatter timeFormat = DateTimeFormat.forPattern("hh:mm a");

    public static void shareImages(List<Bitmap> bitmaps, Activity activity) {
        ArrayList<Uri> uris = new ArrayList<>();
        Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
        share.setType("image/jpeg");
        share.setType("text/plain");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        for (int i = 0; i < bitmaps.size(); i++) {
            Uri uri = activity.getContentResolver().
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);

            OutputStream outstream;
            try {
                outstream = activity.getContentResolver().openOutputStream(uri);
                bitmaps.get(i).compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                outstream.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }

            uris.add(uri);
        }

//        show.set(false);

        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        activity.startActivity(Intent.createChooser(share, "Share Image"));
    }

    public static String encodeImage(String path, int quality) {
        try {
            File imagefile = new File(path);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            Bitmap res = getResizedBitmap(bm, quality);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            res.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);
            //Base64.de
            return encImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        } catch (Exception e) {
            return null;
        }
    }

    public static void showErrorMessage(Throwable t, Context context) {
        try {
            if (t instanceof IOException) {
                Toast.makeText(context, "Network Failed, Please make sure you have an internet connection", Toast.LENGTH_SHORT).show();
                // logging probably not necessary
            } else {
//            Toast.makeText(context, "Conversion issue! Response changed from server :(", Toast.LENGTH_SHORT).show();
                // todo log to some central bug tracking service
            }
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUserToSharedPreferences(Context context, User user) {
        Gson gson = new Gson();

        String jsonString = gson.toJson(user);
        PreferencesManager.write(Constants.USER, jsonString);
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.USER_TAG_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_TAG_MODEL, jsonString);
        editor.apply();
    }

    public static User getUserFromSharedPreferences(Context context) {
        Gson gson = new Gson();
        String json = PreferencesManager.read(context, Constants.USER, "");
        if (!TextUtils.isEmpty(json)) {
            return gson.fromJson(json, User.class);
        } else {
            return null;
        }

        //SharedPreferences sharedPref = context.getSharedPreferences(Constants.USER_TAG_SP, MODE_PRIVATE);

    }

    public static void logOut(Context context) {
        PreferencesManager.clear();
        SharedPreferences preferences = context.getSharedPreferences(Constants.USER, MODE_PRIVATE);
        preferences.edit().remove(Constants.USER_TAG_MODEL).apply();
        context.startActivity(new Intent(context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        ((Activity) context).finish();
    }

    public static String roundNoDecimal(double d) {
        try {
            DecimalFormat twoDForm = new DecimalFormat("#");
            return twoDForm.format(d);
        } catch (Exception e) {
            return "";
        }
    }

    public static double roundOneDecimal(double d) {
        try {
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            return Double.valueOf(twoDForm.format(d));
        } catch (Exception e) {
            return 0;
        }
    }

    /*public static AnimationDrawable getAnimateDrawable(Context context) {
        return (AnimationDrawable) ContextCompat.getDrawable(context,R.drawable.place_holder_gif);
    }

    public static int getPlaceHolderImage() {
        return R.drawable.custom_placeholder_image;
    }*/

}
