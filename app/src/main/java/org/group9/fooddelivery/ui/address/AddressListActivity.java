package org.group9.fooddelivery.ui.address;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityAddressListBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.UiConstants;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.Request;

public class AddressListActivity extends NavigableAppCompatActivity {

   private List<String> address = new ArrayList<>();
   private List<Integer> id = new ArrayList<>();
   private List<String> receivername = new ArrayList<>();
   private List<String> phone = new ArrayList<>();
   private List<String> tag = new ArrayList<>();
   private RecyclerView recyclerView;
   private AddressAdapter addressAdapter;
   private ActivityAddressListBinding addressListBinding;

   private boolean isSelectionMode;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addressListBinding = ActivityAddressListBinding.inflate(getLayoutInflater());
      View view = addressListBinding.getRoot();
      setContentView(view);
      setTitle("我的地址");

      initProperties();

      setListeners();
   }

   @Override
   protected void onStart() {
      super.onStart();
      updateDeliveryAddressesDataAndView();
   }

   public void initProperties() {
      isSelectionMode = getIntent().getBooleanExtra(UiConstants.SELECTION_MODE, false);
   }

   private void setListeners() {
      addressListBinding.addaddressButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(AddressListActivity.this, AddressAddingActivity.class);
            startActivityForResult(intent, 1);
         }
      });
   }

   public void updateDeliveryAddressesDataAndView() {

      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_deliveryAddress_listForCurrentUser))
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(AddressListActivity.this) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               // 刷新列表
               id.clear();
               address.clear();
               phone.clear();
               receivername.clear();
               tag.clear();
               List<DeliveryAddress> deliveryAddresses = ((JSONArray) result.getData()).toJavaList(DeliveryAddress.class);
               for (int i = 0; i < deliveryAddresses.size(); i++) {
                  id.add(deliveryAddresses.get(i).getId());
                  address.add(deliveryAddresses.get(i).getReceiverAddress());
                  phone.add(deliveryAddresses.get(i).getReceiverPhoneNumber());
                  receivername.add(deliveryAddresses.get(i).getReceiverName());
                  tag.add(deliveryAddresses.get(i).getTag());
                  Log.d("TAG", "handle200: " + deliveryAddresses.get(i).getReceiverName());
               }
               updateDeliveryAddressesView();
               Log.d("TAG", "handle200: " + address);
            });
            return true;
         }
      };

      new CommonCallback(AddressListActivity.this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }

   public void updateDeliveryAddressesView() {
      Log.d("TAG", "initData:xxxxxx ");
      List<Map<String, Object>> addresdata = new ArrayList<>();
      for (int i = 0; i < address.size(); i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("address", address.get(i));
         map.put("id", id.get(i));
         map.put("receivername", receivername.get(i));
         map.put("phone", phone.get(i));
         map.put("tag", tag.get(i));

         addresdata.add(map);
      }


      Log.d("mapdata", "initData: " + address);
      recyclerView = findViewById(R.id.addresslist);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      addressAdapter = new AddressAdapter(this, addresdata);
      if (isSelectionMode) {
         addressAdapter.setOnAddressSelectedListener(new AddressAdapter.OnAddressSelectedListener() {
            @Override
            public void addressSelected(DeliveryAddress deliveryAddress) {
               getAppCtx().setDeliveryAddress(deliveryAddress);
               finish();
            }
         });
      }

      recyclerView.setAdapter(addressAdapter);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      switch (requestCode) {
         case 1:
            if (resultCode == 1) {
               updateDeliveryAddressesDataAndView();
            }
            //来自按钮1的请求，作相应业务处理
            break;
         case 0:
            //来自按钮2的请求，作相应业务处理
            break;
      }

   }
}