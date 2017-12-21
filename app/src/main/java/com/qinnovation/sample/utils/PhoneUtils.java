package com.qinnovation.sample.utils;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ACTIVITY_SERVICE;


public class PhoneUtils {

    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    private static final String TAG = PhoneUtils.class.getSimpleName();

    private static TelephonyManager mTelephonyManager;

    /**
     * Get the instance of TelephonyManager
     *
     * @return TelephonyManager instance
     */
    private static TelephonyManager getTelephonyManager(Context context) {
        if (mTelephonyManager == null)
            mTelephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

        return mTelephonyManager;
    }


    /**
     * Get the device IMEI number
     *
     * @return String IMEI Number
     * @param context
     */
    public static String getDeviceIMEI(Context context) {
        String deviceId = "860152033373653";// livestock IMEI
//        String deviceId = getTelephonyManager().getDeviceId();
//        String deviceId = getTelephonyManager().getDeviceId();

        return deviceId != null ? deviceId : Settings.Secure.getString(context
                .getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Check the OS version
     *
     * @param version Version of the OS to be checked
     * @return Boolean
     */
    public static boolean checkOSVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    public static boolean isNetworkTimeEnabled(Context context) {
        int result = 0;
        if (checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
            result = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, -1);
        } else {
            result = Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME, -1);
        }
        if (result == -1 || result > 0)
            return true;

        return false;
    }

    public static boolean isNetworkTimeZoneEnabled(Context context) {
        int result = 0;
        if (checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
            result = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, -1);
        } else {
            result = Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME_ZONE, -1);
        }
        if (result == -1 || result > 0)
            return true;

        return false;
    }


    /**
     * Get the height of the status bar
     *
     * @param context activity context
     * @return Int
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
//        final long SIZE_GB = SIZE_MB * SIZE_KB;
        long availableSpace = -1L;
        try {
            StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
            if (PhoneUtils.checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
                availableSpace = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
        } catch (Exception e) {
            return availableSpace;
        }
        return availableSpace / SIZE_MB;
    }

    public static long getAvailableSpace() {
//        final long SIZE_KB = 1024L;
//        final long SIZE_MB = SIZE_KB * SIZE_KB;
//        final long SIZE_GB = SIZE_MB * SIZE_KB;
        long availableSpace = -1L;
        try {
            StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
            if (PhoneUtils.checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
                availableSpace = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
        } catch (Exception e) {
            return availableSpace;
        }
        return availableSpace;
    }


    /**
     * if available space is lower than sizeInMB it will return false
     */
    public static boolean isSpaceAvailableInMB(int sizeInMB) {
        boolean isSpaceAvail = true;

        long availableSpaceInMB = getAvailableSpaceInMB();
        if (availableSpaceInMB != -1) {
            isSpaceAvail = availableSpaceInMB > sizeInMB;
        }
        return isSpaceAvail;
    }


    public static String getDeviceInfo() {
        String details = null;
        try {
            details = "VERSION RELEASE : " + Build.VERSION.RELEASE
                    + "\nVERSION API : " + Build.VERSION.SDK_INT
                    + "\nMANUFACTURER : " + Build.MANUFACTURER
                    + "\nPRODUCT : " + Build.PRODUCT
                    + "\nBRAND : " + Build.BRAND
                    + "\nMODEL : " + Build.MODEL
                    + "\nUSER : " + Build.USER
                    + "\nVERSION INCREMENTAL : " + Build.VERSION.INCREMENTAL
                    + "\nFINGERPRINT : " + Build.FINGERPRINT
                    + "\nBOARD : " + Build.BOARD
                    + "\nDISPLAY : " + Build.DISPLAY
                    + "\nBOOTLOADER : " + Build.BOOTLOADER
                    + "\nHARDWARE : " + Build.HARDWARE
                    + "\nHOST : " + Build.HOST
                    + "\nID : " + Build.ID
                    + "\nSERIAL : " + Build.SERIAL
                    + "\nTAGS : " + Build.TAGS
                    + "\nTIME : " + Build.TIME
                    + "\nTYPE : " + Build.TYPE
//                + "\nCPU_ABI : " + Build.CPU_ABI
//                + "\nCPU_ABI2 : " + Build.CPU_ABI2
                    + "\nUNKNOWN : " + Build.UNKNOWN;
        } catch (Exception e) {
            details = e.getMessage();
        }
        return details;
    }

    public static String getScreenDimension(Context context) {
        if (context == null)
            return null;

        String dimenInfo = null;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            int dens = dm.densityDpi;
            double wi = (double) width / (double) dens;
            double hi = (double) height / (double) dens;
            double x = Math.pow(wi, 2);
            double y = Math.pow(hi, 2);
            double screenInches = Math.sqrt(x + y);

            dimenInfo = "Width x Height : " + width + " x " + height + " (pixel)"
                    + "\nInches : " + String.format("%.1f", screenInches)
                    + "\nDensityDpi : " + dm.densityDpi;
        } catch (Exception e) {
            dimenInfo = e.getMessage();
        }
        return dimenInfo;
    }

    public static String getDateTimeInfo(Context context) {
        String dateTimeInfo = null;
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            dateTimeInfo = "Device Date & Time : " + DateTimeUtil.getInstance().getTodayDate("dd MMM, yyyy hh:mm:ss a z")
                    + "\nTime Zone : " + tz.getDisplayName(true, TimeZone.SHORT)
                    + "\nTime Zone ID : " + tz.getID()
                    + "\nIs network date enabled : " + (isNetworkTimeEnabled(context) ? "Yes" : "No")
                    + "\nIs network time zone enabled : " + (isNetworkTimeZoneEnabled(context) ? "Yes" : "No");
        } catch (Exception e) {
            e.printStackTrace();
            dateTimeInfo = e.getMessage();
        }
        return dateTimeInfo;
    }

    public static String getInternalStorageInfo() {
        String storageInfo = null;
        try {
            StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
            long totalSpace = 0, freeSpace = 0;
            if (checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
                totalSpace = stat.getTotalBytes();
                freeSpace = stat.getAvailableBytes();
            } else {
                totalSpace = (long) stat.getBlockCount() * (long) stat.getBlockSize();
                freeSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
            storageInfo = "Total space : " + humanReadableByteCount(totalSpace)
                    + "\nFree space : " + humanReadableByteCount(freeSpace);
        } catch (Exception e) {
            storageInfo = e.getMessage();
        }
        return storageInfo;
    }

    public static String getRAMMemoryInfo(Context context) {
        if (context == null)
            return null;

        String memInfo = null;
        try {
            if (checkOSVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                activityManager.getMemoryInfo(mi);
//                double availableMegs = mi.availMem / 1048576L;
                memInfo = "Total Memory : " + humanReadableByteCount(mi.totalMem)
                        + "\nFree Memory : " + humanReadableByteCount(mi.availMem)
                        + "\nUsed Memory : " + humanReadableByteCount((mi.totalMem - mi.availMem));
            } else {
                RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
                memInfo = reader.readLine();
            }
        } catch (Exception e) {
            memInfo = e.getMessage();
        }
        return memInfo;
    }


    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " Bytes";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.2f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String memoryByteConvert(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " Bytes";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.0f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
