package org.group9.fooddelivery.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.ui.user.LoginActivity;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class CommonJsonResponseHandler
   extends JsonResponseHandler {

   private final Activity activity;

   public CommonJsonResponseHandler(Activity activity) {
      this.activity = activity;
   }

   @Override
   public boolean handle200(@Nonnull Result result) {
      return super.handle200(result);
   }

   @Override
   public boolean handle400(@Nonnull Result result) {
      return super.handle400(result);
   }

   @Override
   public boolean handle401(@Nonnull Result result) {

      activity.runOnUiThread(() -> {
         AlertDialog.Builder builder = new AlertDialog.Builder(activity)
            .setTitle("信息")
            .setMessage(result.getMsg());
         if (activity.getClass() != LoginActivity.class) {
            builder
               .setPositiveButton("去登录", (dialog, which) -> {
                  Intent intent = new Intent(activity, LoginActivity.class);
                  activity.startActivity(intent);
               })
               .setNegativeButton("取消", (dialog, which) -> {
                  dialog.dismiss();
                  activity.finish();
               });
         } else {
            builder
               .setPositiveButton("确认", (dialog, which) -> {
                  dialog.dismiss();
               });
         }
         AlertDialog dialog = builder.create();
         dialog.show();
      });
      return true;
   }

   @Override
   public boolean handle403(@Nonnull Result result) {
      return super.handle403(result);
   }

   @Override
   public boolean handle404(@Nonnull Result result) {
      return super.handle404(result);
   }

   @Override
   public boolean handle422(@Nonnull Result result) {
      return super.handle422(result);
   }

   @Override
   public boolean handle500(@Nullable Result result) {
      return super.handle500(result);
   }

   @Override
   public void handleFatalServerError(@Nonnull Response resp) {

      activity.runOnUiThread(() -> {
         String msg = "";
         ResponseBody body = resp.body();
         if (body == null) {
            msg = "意外的空响应体";
         } else {
            try {
               msg = body.string();
            } catch (IOException e) {
               msg = "读取响应体时发送错误：" + e.getMessage();
            }
         }

         AlertDialog dialog = new AlertDialog.Builder(activity)
            .setTitle("信息")
            .setMessage("服务器内部错误：" + msg)
            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
               }
            })
            .create();
         dialog.show();
      });
   }

   @Override
   @SuppressLint("DefaultLocale")
   public void handleCommon(@Nonnull Result result) {

      activity.runOnUiThread(() -> {
         AlertDialog dialog = new AlertDialog.Builder(activity)
            .setTitle("信息")
            .setMessage(String.format("%s，状态码：%d",
               result.getMsg(), result.getCode()))
            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
               }
            })
            .create();
         dialog.show();
      });
   }
}
