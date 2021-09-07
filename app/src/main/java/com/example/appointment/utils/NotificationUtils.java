package com.example.appointment.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.appointment.R;
public class NotificationUtils
{
    public static void UserToast(Context context, String Msg)
    {
        try
        {
            Toast.makeText(context, Msg + "", Toast.LENGTH_LONG).show();
        }
        catch (Exception ignored)
        {
        }
    }

    public static void DeveloperToast(Context context, String Msg)
    {
        try
        {
            if (context.getResources().getBoolean(R.bool.IsLocal))
            {
//                Toast.makeText(context, Msg + "", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ignored)
        {
        }
    }
}
