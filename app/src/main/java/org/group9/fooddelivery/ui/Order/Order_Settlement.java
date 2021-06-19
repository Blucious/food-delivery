package org.group9.fooddelivery.ui.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityOrderSettlementBinding;
import org.group9.fooddelivery.ui.address.address_list;
import org.group9.fooddelivery.ui.dashboard.OrderAdapter;
import org.group9.fooddelivery.ui.menu.CategoriesAdapter;
import org.group9.fooddelivery.ui.menu.FoodAdapter;
import org.group9.fooddelivery.ui.menu.Menudelivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_Settlement extends AppCompatActivity {
    private ActivityOrderSettlementBinding orderSettlementBinding;
    private int[] FoodImg ={R.drawable.doublelayfishburger,R.drawable.snackset5,R.drawable.snackset4};
    private int[] Price = {R.string.Price};
    private int[] FoodName = {R.string.FoodName};
    private int[] number = {R.string.Price};
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderSettlementBinding = ActivityOrderSettlementBinding.inflate(getLayoutInflater());
        View view = orderSettlementBinding.getRoot();
        initData();
        init();
        setContentView(view);
    }

    public void init(){
        orderSettlementBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_Settlement.this,OrderDetail.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        List<Map<String,Object>> orderfooditems = new ArrayList<>();
        for (int i = 0; i < FoodImg.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("FoodImg",FoodImg[i]);
            map.put("Price",Price[0]);
            map.put("FoodName",FoodName[0]);
            map.put("number",number[0]);
            orderfooditems.add(map);
        }

        recyclerView = orderSettlementBinding.orderfoodslist;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderFoodItemsAdapter orderFoodItemsAdapter  = new OrderFoodItemsAdapter(this, orderfooditems);
        recyclerView.setAdapter(orderFoodItemsAdapter);

    }
}