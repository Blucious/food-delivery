package org.group9.fooddelivery.ui.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityUpdateAddressBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.DeliveryAddressDTO;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.entity.UserVO;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.common.BaseAppCompatActivity;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;
import org.group9.fooddelivery.ui.user.LoginActivity;
import org.group9.fooddelivery.ui.user.RegisterActivity;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class AddressUpdatingActivity extends NavigableAppCompatActivity {
    private ActivityUpdateAddressBinding updateAddressBinding;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateAddressBinding = ActivityUpdateAddressBinding.inflate(getLayoutInflater());
        View view = updateAddressBinding.getRoot();
        setContentView(view);
        init();

        setTitle("修改地址");

    }
    public void init(){
        intent = getIntent();
        String address = intent.getStringExtra("address");
        Integer id = intent.getIntExtra("id",0);
        String phone = intent.getStringExtra("phone");
        String receivername = intent.getStringExtra("receivername");
        String tag = intent.getStringExtra("tag");
        DeliveryAddress editaddress = new DeliveryAddress();
        editaddress.setReceiverAddress(address);
        editaddress.setId(id);
        editaddress.setReceiverName(receivername);
        editaddress.setReceiverPhoneNumber(phone);
        editaddress.setTag(tag);


        updateAddressBinding.setAddress(editaddress);


        updateAddressBinding.company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddressBinding.tag.setText("公司");
            }
        });

        updateAddressBinding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddressBinding.tag.setText("家");
            }
        });
        updateAddressBinding.school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddressBinding.tag.setText("学校");
            }
        });


        updateAddressBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryAddress editaddress = updateAddressBinding.getAddress();
                if(StringUtils.isEmpty(editaddress.getReceiverName())){
                    getToastHelper().showShort("请输入收件人");
                    return;
                }
                if(StringUtils.isEmpty(editaddress.getReceiverPhoneNumber())){
                    getToastHelper().showShort("请输入手机号");
                    return;
                }
                if(StringUtils.isEmpty(editaddress.getTag())){
                    getToastHelper().showShort("请输入标签");
                    return;
                }
                if(StringUtils.isEmpty(editaddress.getReceiverAddress())){
                    getToastHelper().showShort("请输入地址");
                    return;
                }
                FormBody body = new FormBody.Builder()
                        .add("id", String.valueOf(editaddress.getId()))
                        .add("receiverName",editaddress.getReceiverName())
                        .add("receiverPhoneNumber",editaddress.getReceiverPhoneNumber())
                        .add("receiverAddress",editaddress.getReceiverAddress())
                        .add("tag",editaddress.getTag())
                        .build();
                Request req = new Request.Builder()
                        .url(ApiConstants.apiUrl(ApiConstants.api_deliveryAddress_updateById))
                        .post(body)
                        .build();

                CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(AddressUpdatingActivity.this) {
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
                new CommonCallback(AddressUpdatingActivity.this, responseHandler)
                        .enqueueTo(getAppCtx().getHttpClient().newCall(req));
            }
        });
        updateAddressBinding.contactinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddressUpdatingActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddressUpdatingActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 1);
                }else {
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
                boolean hasphone = (c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)>0);
                if(hasphone){
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" = ?",new String[]{name},null);
                    if (cursor.moveToFirst()){
                        phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("TAG", "onActivityResult: "+phone);

                    }
                }
            }
        }
        updateAddressBinding.receivername.setText(name);
        updateAddressBinding.phone.setText(phone);

    }


    /******************** 菜单相关 ********************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_update_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deleteOption)  {
            System.out.println(updateAddressBinding.getAddress().getId());
            Request req = new Request.Builder()
                    .url(ApiConstants.apiUrl(ApiConstants.api_deliveryAddress_deleteById+"?id="+updateAddressBinding.getAddress().getId()))
                    .build();

            CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(AddressUpdatingActivity.this) {
                @Override
                public boolean handle200(@Nonnull Result result) {
                    runOnUiThread(() -> {
                        // 设置VO

                        // 退出当前Activity
                        getToastHelper().showLong("删除成功");
                        setResult(1,new Intent().putExtra("hasdelete",1));
                        finish();
                    });
                    return true;
                }
            };
            new CommonCallback(AddressUpdatingActivity.this, responseHandler)
                    .enqueueTo(getAppCtx().getHttpClient().newCall(req));
        }else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    /******************** /菜单相关 ********************/


}