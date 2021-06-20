package org.group9.fooddelivery.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
   private ActivityOrderDetailBinding activityOrderDetailBinding;
   private int[] FoodImg = {R.drawable.doublelayfishburger, R.drawable.snackset5, R.drawable.snackset4};
   private int[] Price = {R.string.Price};
   private int[] FoodName = {R.string.FoodName};
   private int[] number = {R.string.Price};
   private RecyclerView recyclerView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      activityOrderDetailBinding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
      View view = activityOrderDetailBinding.getRoot();
      init();

      setContentView(view);
   }

   public void init() {
      List<Map<String, Object>> orderfooditems = new ArrayList<>();
      for (int i = 0; i < FoodImg.length; i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("FoodImg", FoodImg[i]);
         map.put("Price", Price[0]);
         map.put("FoodName", FoodName[0]);
         map.put("number", number[0]);
         orderfooditems.add(map);
      }

      recyclerView = activityOrderDetailBinding.orderdetailfoodslist;
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      OrderDetailFoodItemsAdapter adapter = new OrderDetailFoodItemsAdapter(OrderDetailActivity.this, orderfooditems);
      recyclerView.setAdapter(adapter);
   }
}