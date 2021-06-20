package org.group9.fooddelivery.ui.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.group9.fooddelivery.context.FoodDeliveryApplication;

import okhttp3.OkHttpClient;

public class BaseAppCompatActivity extends AppCompatActivity {


   private FoodDeliveryApplication appCtx;

   private ToastHelper toastHelper;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      toastHelper = new ToastHelper(this);
      appCtx = FoodDeliveryApplication.getInstance();
   }

   public FoodDeliveryApplication getAppCtx() {
      return appCtx;
   }

   public ToastHelper getToastHelper() {
      return toastHelper;
   }
}
