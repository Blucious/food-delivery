package org.group9.fooddelivery.ui.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityAddAddressBinding;
import org.group9.fooddelivery.databinding.ActivityAddressListBinding;
import org.group9.fooddelivery.databinding.AddressListItemBinding;
import org.group9.fooddelivery.ui.menu.CategoriesAdapter;
import org.group9.fooddelivery.ui.menu.FoodAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class address_list extends AppCompatActivity {
    private int [] address ={R.string.address};
    private int [] addressoutline={R.string.addressoutline};
    private int [] receivername={R.string.receivername};
    private int [] phone = {R.string.phone};
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private ActivityAddressListBinding addressListBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressListBinding = ActivityAddressListBinding.inflate(getLayoutInflater());
        View view = addressListBinding.getRoot();
        setContentView(view);
        setTitle("我的地址");
        initData();
        init();
    }

    public void init(){


        addressListBinding.addaddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(address_list.this,add_address.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        List<Map<String,Object>> addresdata = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("address",address[0]);
            map.put("addressoutline",addressoutline[0]);
            map.put("receivername",receivername[0]);
            map.put("phone",phone[0]);

            addresdata.add(map);
        }

        recyclerView = findViewById(R.id.addresslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressAdapter = new AddressAdapter(this, addresdata);
        recyclerView.setAdapter(addressAdapter);



    }
}