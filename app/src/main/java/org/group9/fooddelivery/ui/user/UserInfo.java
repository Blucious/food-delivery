package org.group9.fooddelivery.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityUserInfoBinding;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.entity.User;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.common.BaseAppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Nonnull;

import okhttp3.Request;

public class UserInfo extends BaseAppCompatActivity {
    private ActivityUserInfoBinding bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = ActivityUserInfoBinding.inflate(getLayoutInflater());
        View view = bd.getRoot();
        init();



        setContentView(view);
    }

    public void save(){

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

            }
        });

    }



}