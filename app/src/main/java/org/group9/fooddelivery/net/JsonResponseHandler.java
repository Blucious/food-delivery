package org.group9.fooddelivery.net;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.group9.fooddelivery.entity.Result;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import okhttp3.Response;
import okhttp3.ResponseBody;


public class JsonResponseHandler {

   private static final String TAG = JsonResponseHandler.class.getSimpleName();


   @SuppressLint("DefaultLocale")
   @SuppressWarnings("ConstantConditions")
   public final void handle(Response resp) throws IOException {
      Log.d(TAG, String.format("JsonResponseHandler handle: url=%s code=%d",
         resp.request().url(), resp.code()));

      // 获取响应体
      ResponseBody body = resp.body();
      if (body == null) {
         handleFatalServerError(resp);
         return;
      }

      // 将响应体中的Json字符串转换成Result对象
      String jsonStr = body.string();
      Result result = null;
      try {
         result = JSONObject.parseObject(jsonStr, Result.class);
      } catch (JSONException e) {
         handleFatalServerError(resp);
         return;
      }
      Log.d(TAG, String.format("JsonResponseHandler handle: url=%s body=%s",
         resp.request().url(), jsonStr));

      // 根据不同的响应码，分发到不同的方法进行处理
      try {
         int code = result.getCode();
         try {
            Method method = getClass().getMethod("handle" + code, Result.class);
            boolean handled = (boolean) method.invoke(this, result);
            if (!handled) {
               handleCommon(result);
            }
         } catch (NoSuchMethodException e) {
            handleCommon(result);
         }
      } catch (Exception e) {
         Log.e(TAG, "处理响应过程中发生异常，响应内容：" + result, e);
      }
   }

   /**
    * 处理 Ok 成功
    *
    * @return 处理成功返回true，否则false
    */
   public boolean handle200(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Bad Request 错误的请求
    */
   public boolean handle400(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Unauthorized 未授权
    */
   public boolean handle401(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Forbidden 被禁止
    */
   public boolean handle403(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Not Found 未找到
    */
   public boolean handle404(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Unprocessable entity 不可处理的实体，可能由于语义错误导致
    */
   public boolean handle422(@Nonnull Result result) {
      return false;
   }

   /**
    * 处理 Error 服务器内部错误
    */
   public boolean handle500(@Nullable Result result) {
      return false;
   }

   /**
    * 处理致命服务器错误
    */
   public void handleFatalServerError(@Nonnull Response resp) {

   }

   /**
    * 通用（兜底）响应码处理，
    * 无法被处理的响应码将会被送到这里
    */
   public void handleCommon(@Nonnull Result result) {

   }
}
