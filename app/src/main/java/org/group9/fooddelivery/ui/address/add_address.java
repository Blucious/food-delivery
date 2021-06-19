package org.group9.fooddelivery.ui.address;

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
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityAddAddressBinding;

public class add_address extends AppCompatActivity {
    private ActivityAddAddressBinding addAddressBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAddressBinding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        View view =addAddressBinding.getRoot();
        setContentView(view);
        init();
        setTitle("添加地址");

    }


    public void init(){
        addAddressBinding.contactinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(add_address.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(add_address.this,new String[]{Manifest.permission.READ_CONTACTS},1);
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
                    c.close();
                    cursor.close();
                }
            }
        }
        addAddressBinding.receivername.setText(name);
        addAddressBinding.phone.setText(phone);


    }


}
