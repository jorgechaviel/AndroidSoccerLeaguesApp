package com.jchaviel.soccerleaguesapp.domain;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by jchavielreyes on 7/4/16.
 */
public class Utils {

    private Geocoder geocoder; //para la ubicación
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public Utils(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public String getAvatarUrl(String email) {
        return GRAVATAR_URL + md5(email) + "?s=64";
    }

    public String getFromLocation(double lat, double lng) {
        String result = "";
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); //1 -> cantidad maxima de resultados
            Address address = addresses.get(0);

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                result += address.getAddressLine(i) + ", ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isConnected(Activity activity){
        ConnectivityManager cm =
                (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
