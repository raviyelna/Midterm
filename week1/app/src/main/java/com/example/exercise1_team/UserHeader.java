package com.example.exercise1_team;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public final class UserHeader {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_AVATAR = "user_avatar";
    private static final String KEY_USER_CREATED_AT = "user_created_at";

    private UserHeader() {
    }

    public static void saveUserInfo(Context context, String email, String name, long createdAtMillis, int avatarResId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .putInt(KEY_USER_AVATAR, avatarResId)
                .putLong(KEY_USER_CREATED_AT, createdAtMillis)
                .apply();
    }

    public static void saveUserInfo(Context context, String email, String name, int avatarResId) {
        saveUserInfo(context, email, name, System.currentTimeMillis(), avatarResId);
    }

    public static void saveUserInfo(Context context, String email, String name) {
        saveUserInfo(context, email, name, System.currentTimeMillis(), R.drawable.acount_avt);
    }

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String email = prefs.getString(KEY_USER_EMAIL, "");
        return email == null ? "" : email;
    }

    public static String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString(KEY_USER_NAME, "");
        return name == null ? "" : name;
    }

    public static int getUserAvatar(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_AVATAR, R.drawable.acount_avt);
    }

    public static long getUserCreatedAt(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_USER_CREATED_AT, System.currentTimeMillis());
    }

    public static void displayUserInfo(
            Context context,
            ImageView imageViewAvatar,
            TextView textViewName,
            TextView textViewEmail,
            TextView textViewGreeting
    ) {
        if (imageViewAvatar != null) {
            imageViewAvatar.setImageResource(getUserAvatar(context));
        }
        if (textViewName != null) {
            String name = getUserName(context);
            textViewName.setText(name.isEmpty() ? "User" : name);
        }
        if (textViewEmail != null) {
            String email = getUserEmail(context);
            textViewEmail.setText(email.isEmpty() ? "user@example.com" : email);
        }
        if (textViewGreeting != null) {
            textViewGreeting.setText(getGreeting());
        }
    }

    public static boolean isUserLoggedIn(Context context) {
        return !getUserEmail(context).isEmpty();
    }

    public static void clearUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    private static String getGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 5 && hour <= 11) {
            return "Good Morning";
        } else if (hour >= 12 && hour <= 17) {
            return "Good Afternoon";
        } else if (hour >= 18 && hour <= 21) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }
}
