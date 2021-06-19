package org.group9.fooddelivery.ui.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityMenuBinding;
import org.group9.fooddelivery.ui.Order.Order_Settlement;
import org.group9.fooddelivery.ui.address.address_list;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menudelivery extends AppCompatActivity {
    public ActivityMenuBinding activityMenuBinding;
    private RecyclerView CategoriesrecyclerView;
    private CategoriesAdapter CategoriesAdapter;
    private RecyclerView FoodRecycleView;
    private FoodAdapter foodAdapter;
    private ImageButton Myaddress;
    private ImageButton OrderSettlement;
    private int[] categoryIcon ={R.drawable.bucketcategoryicon,R.drawable.snackcategoryicon,R.drawable.anges,R.drawable.burger,R.drawable.drinks};
    private int[] FoodImg ={R.drawable.doublelayfishburger,R.drawable.snackset5,R.drawable.snackset4};
    private int[] Price = {R.string.Price};
    private int[] FoodName = {R.string.FoodName};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMenuBinding =ActivityMenuBinding.inflate(getLayoutInflater());
        View view = activityMenuBinding.getRoot();
        setContentView(view);
        initData();
        init();







    }

    public void init(){
        OrderSettlement = findViewById(R.id.done);
        OrderSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menudelivery.this, Order_Settlement.class);
                startActivity(intent);
            }
        });
        Myaddress = findViewById(R.id.Myaddress);
        Myaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menudelivery.this,address_list.class);
                startActivity(intent);


            }
        });
    }

    public void initData(){
        List<Map<String,Object>> categoriesdata = new ArrayList<>();
        for (int i = 0; i < categoryIcon.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("CategortIcon",categoryIcon[i]);
            categoriesdata.add(map);
        }

        CategoriesrecyclerView = findViewById(R.id.categoriesicon);
        CategoriesrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration did = new DividerItemDecoration(
                CategoriesrecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        CategoriesrecyclerView.addItemDecoration(did);
        CategoriesAdapter = new CategoriesAdapter(this, categoriesdata);
        CategoriesrecyclerView.setAdapter(CategoriesAdapter);


        List<Map<String,Object>> Fooddata = new ArrayList<>();
        for (int i = 0; i < FoodImg.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("FoodImg",FoodImg[i]);
            map.put("Price",Price[0]);
            map.put("FoodName",FoodName[0]);
            Fooddata.add(map);

        }

        FoodRecycleView = findViewById(R.id.menuRecyclerView);
        FoodRecycleView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration did1 = new DividerItemDecoration(
                FoodRecycleView.getContext(), DividerItemDecoration.VERTICAL);
        FoodRecycleView.addItemDecoration(did1);
        foodAdapter = new FoodAdapter(this, Fooddata);
        FoodRecycleView.setAdapter(foodAdapter);
    }
}