package org.group9.fooddelivery.ui.address;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityAddressAddingBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.common.BaseAppCompatActivity;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.Request;


public class AddressAddingActivity extends BaseAppCompatActivity {
   private ActivityAddressAddingBinding addAddressBinding;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setTitle("添加地址");

      addAddressBinding = ActivityAddressAddingBinding.inflate(getLayoutInflater());
      View view = addAddressBinding.getRoot();
      setContentView(view);

      init();
   }


   public void init() {
      addAddressBinding.setAddress(new DeliveryAddress());
      
         addAddressBinding.company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d("TAG", "onClick: ---------");
               addAddressBinding.tag.setText("公司");
            }
         });

         addAddressBinding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addAddressBinding.tag.setText("家");
            }
         });
         addAddressBinding.school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addAddressBinding.tag.setText("学校");
            }
         });

      addAddressBinding.save.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {



         DeliveryAddress deliveryAddress = addAddressBinding.getAddress();
            if(StringUtils.isEmpty(deliveryAddress.getReceiverName())){
               getToastHelper().showShort("请输入收件人");
               return;
            }
            if(StringUtils.isEmpty(deliveryAddress.getReceiverPhoneNumber())){
               getToastHelper().showShort("请输入手机号");
               return;
            }
            if(StringUtils.isEmpty(deliveryAddress.getTag())){
               getToastHelper().showShort("请输入标签");
               return;
            }
            if(StringUtils.isEmpty(deliveryAddress.getReceiverAddress())){
               getToastHelper().showShort("请输入地址");
               return;
            }

            FormBody body = new FormBody.Builder()
                    .add("receiverName",deliveryAddress.getReceiverName())
                    .add("receiverPhoneNumber",deliveryAddress.getReceiverPhoneNumber())
                    .add("receiverAddress",deliveryAddress.getReceiverAddress())
                    .add("tag",deliveryAddress.getTag())
                    .build();
            Request req = new Request.Builder()
                    .url(ApiConstants.apiUrl(ApiConstants.api_deliveryAddress_save))
                    .post(body)
                    .build();

            CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(AddressAddingActivity.this) {
               @Override
               public boolean handle200(@Nonnull Result result) {
                  runOnUiThread(() -> {
                     // 设置VO

                     // 退出当前Activity
                     getToastHelper().showLong("添加成功");
                     setResult(1,new Intent().putExtra("hasadd",1));
                     finish();
                  });
                  return true;
               }
            };
            new CommonCallback(AddressAddingActivity.this, responseHandler)
                    .enqueueTo(getAppCtx().getHttpClient().newCall(req));






         }
      });




      addAddressBinding.contactinput.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(AddressAddingActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(AddressAddingActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
               Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
               startActivityForResult(intent, 1);
            } else {
               Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
               startActivityForResult(intent, 1);
            }
         }
      });


   }

   @Override
   public void onActivityResult(int reqCode, int resultCode, Intent data) {
      String name = null;
      String phone = null;
      super.onActivityResult(reqCode, resultCode, data);
      if (resultCode == Activity.RESULT_OK) {
         Uri contactData = data.getData();
         Cursor c = getContentResolver().query(contactData, null, null, null, null);
         if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            boolean hasphone = (c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER) > 0);
            if (hasphone) {
               Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", new String[]{name}, null);
               if (cursor.moveToFirst()) {
                  phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                  Log.d("TAG", "onActivityResult: " + phone);

               }
               c.close();
               cursor.close();
            }
         }
      }
      addAddressBinding.receivername.setText(name);
      addAddressBinding.phone.setText(phone);


   }


}
