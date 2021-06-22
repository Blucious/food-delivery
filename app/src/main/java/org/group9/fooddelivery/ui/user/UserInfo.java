package org.group9.fooddelivery.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityUserInfoBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.entity.User;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.address.AddressAddingActivity;
import org.group9.fooddelivery.ui.common.BaseAppCompatActivity;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.Request;

public class UserInfo extends NavigableAppCompatActivity {
    private ActivityUserInfoBinding bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ActivityUserInfoBinding.inflate(getLayoutInflater());
        View view = bd.getRoot();
        setTitle("个人信息");
        init();



        setContentView(view);
    }

    public void save(){

        User userInfo = bd.getUserInfo();


        if(StringUtils.isEmpty(userInfo.getNickName())){
            getToastHelper().showShort("请输入昵称");
            return;
        }
        if(StringUtils.isEmpty(userInfo.getEmail())){
            getToastHelper().showShort("请输入邮箱");
            return;
        }
        if(StringUtils.isEmpty(userInfo.getPhoneNumber())){
            getToastHelper().showShort("请输入手机号");
            return;
        }


        FormBody body = new FormBody.Builder()
                .add("id",String.valueOf(userInfo.getId()))
                .add("email",userInfo.getEmail())
                .add("phoneNumber",userInfo.getPhoneNumber())
                .add("nickName",userInfo.getNickName())
                .build();
        Request req = new Request.Builder()
                .url(ApiConstants.apiUrl(ApiConstants.api_user_changeProfile))
                .post(body)
                .build();

        CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(UserInfo.this) {
            @Override
            public boolean handle200(@Nonnull Result result) {
                runOnUiThread(() -> {
                    // 设置VO

                    // 退出当前Activity
                    getToastHelper().showLong("修改成功");
                    setResult(1,new Intent().putExtra("hasupdate",1));
                    finish();
                });
                return true;
            }
        };
        new CommonCallback(UserInfo.this, responseHandler)
                .enqueueTo(getAppCtx().getHttpClient().newCall(req));









    }



    public void binddata(User user){
        bd.setUserInfo(user);
        bd.balance.setText("0");

    }
    public void init() {
        Request req = new Request.Builder()
                .url(ApiConstants.apiUrl(ApiConstants.api_user_getCurrentUser))
                .build();
        CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(UserInfo.this) {
            @Override
            public boolean handle200(@Nonnull Result result) {
                runOnUiThread(() -> {

                    User user = ((JSONObject) result.getData()).toJavaObject(User.class);
                    Log.d("TAG", "handle200: " + user);

                    binddata(user);
                    Log.d("TAG", "handle200: " + user);

                    //               finish();
                });
                return true;
            }
        };

        new CommonCallback(UserInfo.this, responseHandler)
                .enqueueTo(getAppCtx().getHttpClient().newCall(req));


        bd.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }



}