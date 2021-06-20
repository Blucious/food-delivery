package org.group9.fooddelivery.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

/**
 * @author CBL
 */
public class PermissionUtils {

   public static boolean isPermissionGranted(Context context, String permission) {
      return ContextCompat.checkSelfPermission(context, permission)
         == PackageManager.PERMISSION_GRANTED;
   }

   public static boolean isPermissionsAllGranted(Context context, String[] permissions) {
      for (String permission : permissions) {
         if (!isPermissionGranted(context, permission)) {
            return false;
         }
      }
      return true;
   }

   public static String[] getNotGrantedPermissions(Context context, String[] permissions) {
      ArrayList<String> notGranted = new ArrayList<>();
      for (String permission : permissions) {
         if (!isPermissionGranted(context, permission)) {
            notGranted.add(permission);
         }
      }
      return notGranted.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
   }


   public static boolean requirePermissions(Activity activity,
                                            String[] permissions, int requestCode) {

      String[] notGranted = getNotGrantedPermissions(activity, permissions);

      if (notGranted.length == 0) {
         return true;

      } else {
         ActivityCompat.requestPermissions(activity, notGranted, requestCode);
         return false;
      }
   }

   public static boolean requirePermission(Activity activity, String permission, int requestCode) {
      if (isPermissionGranted(activity, permission)) {
         return true;

      } else {
         ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
         return false;
      }
   }

   public static boolean requestPermissionReadSms(Activity activity, int requestCode) {
      return requirePermission(activity, Manifest.permission.READ_SMS, requestCode);
   }

   public static String buildRequestPermissionsResultDescString(
      int requestCode,
      @NonNull String[] permissions, @NonNull int[] grantResults) {

      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < permissions.length; i++) {
         sb.append("你")
            .append(grantResults[i] == PackageManager.PERMISSION_GRANTED ? "授予" : "拒绝")
            .append("了权限")
            .append(permissions[i]);
         if (i != permissions.length - 1) {
            sb.append("\n");
         }
      }

      return sb.toString();
   }

}
