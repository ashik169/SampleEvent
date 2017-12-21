package com.qinnovation.sample.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Kevin Chris on 12 May 2016.
 */
public class PermissionUtil {

    // Initialize the request code for each permission
    public static final int REQUEST_CODE_READ_PHONE_STATE = 100;
    public static final int REQUEST_CODE_CAMERA = 101;
    public static final int REQUEST_CODE_RECORD_AUDIO = 102;
    public static final int REQUEST_CODE_LOCATION = 103;
    public static final int REQUEST_CODE_CAMERA_AND_RECORD_AUDIO = 104;
    public static final int REQUEST_CODE_CAMERA_AND_LOCATION = 105;
    public static final int REQUEST_CODE_RECORD_AUDIO_AND_LOCATION = 106;
    public static final int REQUEST_CODE_CAMERA_AND_RECORD_AUDIO_AND_LOCATION = 107;
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 108;

    /**
     * Check whether the permission is already granted or not
     *
     * @param activity   Activity
     * @param permission Permission to be checked
     * @return Boolean whether permission is granted or not
     */
    public static boolean hasPermission(@NonNull Activity activity, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks whether to display the description of permission to user or not
     *
     * @param activity   Activity
     * @param permission Permission to be checked
     * @return Boolean whether to show the description to user or not
     */
    public static boolean showRationale(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * Request for the permissions
     *
     * @param activity    Activity
     * @param permission  Array of permissions to be requested
     * @param requestCode Int Request code for the permission
     */
    public static void requestPermission(@NonNull Activity activity, @NonNull String permission[], int requestCode) {
        ActivityCompat.requestPermissions(activity, permission, requestCode);
    }
}
