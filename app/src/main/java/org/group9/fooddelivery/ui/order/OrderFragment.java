package org.group9.fooddelivery.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.FragmentOrderBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {

   private FragmentOrderBinding bd;
   private RecyclerView recyclerView;
   private OrderAdapter orderAdapter;
   private int[] orderstate = {R.string.orderstate};
   private int[] orderfoods = {R.string.FoodName, R.string.FoodName1, R.string.FoodName2};
   private int[] orderPrice = {R.string.orderPrice};
   private int[] ordernumber = {R.string.ordernumber};


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentOrderBinding.inflate(getLayoutInflater());

      initData();

      return bd.getRoot();
   }

   public void initData() {
      List<Map<String, Object>> orderData = new ArrayList<>();
      for (int i = 0; i < 14; i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("orderstate", orderstate[0]);
         map.put("orderfoods", orderfoods);
         map.put("ordernumber", ordernumber[0]);
         map.put("orderPrice", orderPrice[0]);

         orderData.add(map);
      }

      recyclerView = bd.orderlist;
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

      orderAdapter = new OrderAdapter(getActivity(), orderData);
      recyclerView.setAdapter(orderAdapter);
   }

   public void initView() {

   }


}