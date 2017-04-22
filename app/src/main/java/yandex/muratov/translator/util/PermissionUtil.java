package yandex.muratov.translator.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


public class PermissionUtil {

    public static int INTERNET_PERMISSION_CODE = 777;

    public static void requestInternet(AppCompatActivity activity) {
        requestPermission(activity, Manifest.permission.INTERNET, INTERNET_PERMISSION_CODE);
    }

    public static void requestPermission(AppCompatActivity activity, String permission, int code) {
        if (ContextCompat.checkSelfPermission(activity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, code);
            }
        }
    }
}
