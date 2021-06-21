package org.group9.fooddelivery.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiContants;
import org.group9.fooddelivery.databinding.ActivityLoginBinding;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.entity.UserAuthDTO;
import org.group9.fooddelivery.entity.UserVO;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends NavigableAppCompatActivity {

   private ActivityLoginBinding bd;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      bd = DataBindingUtil.setContentView(
         this, R.layout.activity_login);

      initView();

   }

   private void initView() {
      bd.setUserDto(new UserAuthDTO());

      bd.loginButton.setOnClickListener((v) -> {
         UserAuthDTO userDTO = bd.getUserDto();
         if (StringUtils.isEmpty(userDTO.getEmail())) {
            getToastHelper().showShort("请输入邮箱");
            return;
         }
         if (userDTO.getPassword() == null) {
            userDTO.setPassword("");
         }

         FormBody body = new FormBody.Builder()
            .add("email", userDTO.getEmail())
            .add("password", userDTO.getPassword())
            .build();
         Request req = new Request.Builder()
            .url(ApiContants.apiUrl(ApiContants.api_user_login))
            .post(body)
            .build();

         CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(LoginActivity.this) {
            @Override
            public boolean handle200(@Nonnull Result result) {
               runOnUiThread(() -> {
                  // 设置VO
                  UserVO userVo =  ((JSONObject) result.getData()).toJavaObject(UserVO.class);
                  getAppCtx().setUserVO(userVo);

                  // 退出当前Activity
                  getToastHelper().showLong("登录成功");
                  finish();
               });
               return true;
            }
         };
         new CommonCallback(LoginActivity.this, responseHandler)
            .enqueueTo(getAppCtx().getHttpClient().newCall(req));
      });
   }

   /******************** 菜单相关 ********************/
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_register, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.registerOption) {
         Intent intent = new Intent(this, RegisterActivity.class);
         startActivity(intent);

      } else {
         return super.onOptionsItemSelected(item);
      }
      return true;
   }
   /******************** /菜单相关 ********************/
}