package org.group9.fooddelivery.context;

import android.app.Application;


public class FoodDeliveryApplication extends Application {

   private static FoodDeliveryApplication instance;

   public static FoodDeliveryApplication getInstance() {
      return instance;
   }

   @Override
   public void onCreate() {
      super.onCreate();

      instance = this;

      
   }
}
