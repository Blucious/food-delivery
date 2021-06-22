package org.group9.fooddelivery.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.group9.fooddelivery.databinding.FragmentHomeBinding;
import org.group9.fooddelivery.entity.UserVO;
import org.group9.fooddelivery.ui.common.BaseFragment;
import org.group9.fooddelivery.ui.foodmenu.FoodMenuActivity;
import org.group9.fooddelivery.ui.user.LoginActivity;
import org.group9.fooddelivery.util.DateUtils;


public class HomeFragment extends BaseFragment {

   private FragmentHomeBinding bd;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentHomeBinding.inflate(inflater, container, false);

      initView();

      return bd.getRoot();
   }

   @Override
   public void onStart() {
      super.onStart();

      updateViewsState();
   }

   public void initView() {

      // 外送（点餐）按钮
      bd.deliveryButton.setOnClickListener(v -> {
         Intent intent = new Intent(getContext(), FoodMenuActivity.class);
         startActivity(intent);
      });
   }

   /**
    * 根据用户登录情况，更新各视图状态
    */
   private void updateViewsState() {
      updateGreetingAndPointsInfo();
      updateLoginButtonState();
   }

   /**
    * 根据用户登录情况，更新登录按钮状态
    */
   @SuppressLint({"SetTextI18n", "DefaultLocale"})
   private void updateGreetingAndPointsInfo() {

      String greeting = DateUtils.getTimePeriodDisplayName() + "好";
      if (!getAppCtx().isUserLogined()) {
         bd.greetingTextView.setText(greeting);
         bd.pointsTextView.setText("成为会员 享更多优惠");

      } else {
         UserVO userVO = getAppCtx().getUserVO();
         bd.greetingTextView.setText(String.format("%s，%s", greeting, userVO.getNickName()));
         bd.pointsTextView.setText(String.format("积分 %d", userVO.getPoints()));
      }
   }

   /**
    * 根据用户登录情况，更新登录按钮状态
    */
   private void updateLoginButtonState() {

      if (!getAppCtx().isUserLogined()) {
         bd.loginButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
         });
      } else {
         bd.loginButton.setVisibility(View.GONE);
      }
   }


   @Override
   public void onDestroyView() {
      super.onDestroyView();
      bd = null;
   }

}