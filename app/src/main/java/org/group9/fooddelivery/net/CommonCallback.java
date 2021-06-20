package org.group9.fooddelivery.net;

import android.Manifest;
import android.app.Activity;

import org.group9.fooddelivery.ui.UiConstants;
import org.group9.fooddelivery.ui.common.ToastHelper;
import org.group9.fooddelivery.util.PermissionUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonCallback implements Callback {

   private final Activity activity;
   private final JsonResponseHandler responseHandler;

   public CommonCallback(Activity activity, JsonResponseHandler responseHandler) {
      this.activity = activity;
      this.responseHandler = responseHandler;
   }


   public Activity getActivity() {
      return activity;
   }


   public void enqueueTo(Call call) {

      boolean granted = PermissionUtils.requirePermission(
         activity, Manifest.permission.INTERNET,
         UiConstants.REQUEST_CODE_IGNORE);

      // 如果网络权限已经授予，则将自己入队
      if (granted) {
         call.enqueue(this);
      }
   }

   @Override
   public void onFailure(@NotNull Call call,
                         @NotNull IOException e) {

      activity.runOnUiThread(() -> {
         new ToastHelper(activity)
            .showLong("发送请求失败：%s", e.getMessage());
      });
   }

   @Override
   public void onResponse(@NotNull Call call,
                          @NotNull Response response) throws IOException {

      responseHandler.handle(response);
   }
}
