package org.group9.fooddelivery.ui.foodmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityMenuBinding;
import org.group9.fooddelivery.ui.address.AddressListActivity;
import org.group9.fooddelivery.ui.order.OrderSettlementActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodMenuActivity extends AppCompatActivity {

   public ActivityMenuBinding bd;
   private RecyclerView categoriesRecyclerView;
   private CategoriesAdapter categoriesAdapter;
   private RecyclerView foodRecycleView;
   private FoodAdapter foodAdapter;
   private ImageButton settlingButton;
   private int[] categoryIcon = {R.drawable.bucketcategoryicon, R.drawable.snackcategoryicon, R.drawable.anges, R.drawable.burger, R.drawable.drinks};
   private int[] foodImg = {R.drawable.doublelayfishburger, R.drawable.snackset5, R.drawable.snackset4};
   private int[] price = {R.string.Price};
   private int[] foodName = {R.string.FoodName};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      bd = ActivityMenuBinding.inflate(getLayoutInflater());
      View view = bd.getRoot();

      setContentView(view);

      initData();

      init();
   }

   public void init() {
      settlingButton = findViewById(R.id.settlingButton);
      settlingButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(FoodMenuActivity.this, OrderSettlementActivity.class);
            startActivity(intent);
         }
      });
      bd.myAddress.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(FoodMenuActivity.this, AddressListActivity.class);
            startActivity(intent);
         }
      });
   }

   public void initData() {

      List<Map<String, Object>> categoriesdata = new ArrayList<>();
      for (int i = 0; i < categoryIcon.length; i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("CategortIcon", categoryIcon[i]);
         categoriesdata.add(map);
      }

      categoriesRecyclerView = findViewById(R.id.categoriesIcon);
      categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      DividerItemDecoration did = new DividerItemDecoration(
         categoriesRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
      categoriesRecyclerView.addItemDecoration(did);
      categoriesAdapter = new CategoriesAdapter(this, categoriesdata);
      categoriesRecyclerView.setAdapter(categoriesAdapter);


      List<Map<String, Object>> Fooddata = new ArrayList<>();
      for (int i = 0; i < foodImg.length; i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("FoodImg", foodImg[i]);
         map.put("Price", price[0]);
         map.put("FoodName", foodName[0]);
         Fooddata.add(map);

      }

      foodRecycleView = findViewById(R.id.menuRecyclerView);
      foodRecycleView.setLayoutManager(new LinearLayoutManager(this));
      DividerItemDecoration did1 = new DividerItemDecoration(
         foodRecycleView.getContext(), DividerItemDecoration.VERTICAL);
      foodRecycleView.addItemDecoration(did1);
      foodAdapter = new FoodAdapter(this, Fooddata);
      foodRecycleView.setAdapter(foodAdapter);
   }
}