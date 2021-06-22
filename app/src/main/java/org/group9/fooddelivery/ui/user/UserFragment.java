package org.group9.fooddelivery.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.group9.fooddelivery.context.ApiContants;
import org.group9.fooddelivery.databinding.FragmentUserBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.entity.User;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.address.AddressListActivity;
import org.group9.fooddelivery.ui.common.BaseFragment;

import java.util.List;

import javax.annotation.Nonnull;

import okhttp3.Request;

public class UserFragment extends BaseFragment {

   private FragmentUserBinding bd;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentUserBinding.inflate(getLayoutInflater());

      initView();
      init();

      return bd.getRoot();
   }

   public void binddata(User user){
      bd.setUserInfo(user);

   }


   public void init() {
      Request req = new Request.Builder()
              .url(ApiContants.apiUrl(ApiContants.api_user_getCurrentUser))
              .build();
      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(getActivity()) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            getActivity().runOnUiThread(() -> {

               User user = ((JSONObject) result.getData()).toJavaObject(User.class);
               binddata(user);
               Log.d("TAG", "handle200: " + user);

               //               finish();
            });
            return true;
         }
      };

      new CommonCallback(getActivity(), responseHandler)
              .enqueueTo(getAppCtx().getHttpClient().newCall(req));


   }


   private void initView() {


      bd.addressbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddressListActivity.class);
            startActivity(intent);
         }
      });

      bd.updateinfobutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), UserInfo.class);
            startActivity(intent);
         }
      });
   }

}