package org.group9.fooddelivery.ui.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.group9.fooddelivery.context.FoodDeliveryApplication;

public class BaseFragment extends Fragment {

   private FoodDeliveryApplication appCtx;

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      appCtx = FoodDeliveryApplication.getInstance();
   }

   public FoodDeliveryApplication getAppCtx() {
      return appCtx;
   }
}
