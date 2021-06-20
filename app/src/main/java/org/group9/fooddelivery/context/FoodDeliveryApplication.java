package org.group9.fooddelivery.context;

import android.app.Application;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.group9.fooddelivery.entity.UserVO;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class FoodDeliveryApplication extends Application {

   private static FoodDeliveryApplication instance;

   public static FoodDeliveryApplication getInstance() {
      return instance;
   }

   @Override
   public void onCreate() {
      super.onCreate();

      instance = this;

      initHttpClient();
   }


   /**
    * 使应用程序在全局持有一个Http客户端
    */
   private OkHttpClient httpClient;

   /**
    * 已登录用户的信息会保存于此
    */
   private UserVO userVO;

   private void initHttpClient() {

      PersistentCookieJar cookieJar = new PersistentCookieJar(
         new SetCookieCache(),
         new SharedPrefsCookiePersistor(this));

      httpClient = new OkHttpClient.Builder()
         // 设置Cookie罐
         .cookieJar(cookieJar)
         // 设置超时时间
         .connectTimeout(5, TimeUnit.SECONDS)
         .readTimeout(30, TimeUnit.SECONDS)
         .writeTimeout(30, TimeUnit.SECONDS)
         .build();
   }

   public OkHttpClient getHttpClient() {
      return httpClient;
   }

   public void setUserVO(UserVO userVO) {
      this.userVO = userVO;
   }

   public UserVO getUserVO() {
      return userVO;
   }

   /**
    * 判断用户是否已登录
    */
   public boolean isUserLogined() {
      return userVO != null;
   }
}
