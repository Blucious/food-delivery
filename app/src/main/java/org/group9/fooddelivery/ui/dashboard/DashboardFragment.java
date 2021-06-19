package org.group9.fooddelivery.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.FragmentDashboardBinding;
import org.group9.fooddelivery.ui.address.AddressAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {
   private RecyclerView recyclerView;
   private OrderAdapter orderAdapter;
   private DashboardViewModel dashboardViewModel;
   private FragmentDashboardBinding fragmentDashboardBinding;
   private int[] orderstate = {R.string.orderstate};
   private int[] orderfoods = {R.string.FoodName,R.string.FoodName1,R.string.FoodName2};
   private int[] orderPrice = {R.string.orderPrice};
   private int[] ordernumber = {R.string.ordernumber};
   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
      fragmentDashboardBinding = FragmentDashboardBinding.inflate(getLayoutInflater());
      View view = fragmentDashboardBinding.getRoot();
      initData();
      return view;

//      dashboardViewModel =
//         new ViewModelProvider(this).get(DashboardViewModel.class);
//      View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//      final TextView textView = root.findViewById(R.id.text_dashboard);
//      dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//         @Override
//         public void onChanged(@Nullable String s) {
//            textView.setText(s);
//         }
//      });
//      return root;
   }

   public void init(){

   }

   public void initData(){
      List<Map<String,Object>> orderdata = new ArrayList<>();
      for (int i = 0; i < 14; i++) {
         Map<String,Object> map = new HashMap<>();
         map.put("orderstate",orderstate[0]);
         map.put("orderfoods",orderfoods);
         map.put("ordernumber",ordernumber[0]);
         map.put("orderPrice",orderPrice[0]);

         orderdata.add(map);
      }

      recyclerView = fragmentDashboardBinding.orderlist;
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

      orderAdapter = new OrderAdapter(getActivity(), orderdata);
      recyclerView.setAdapter(orderAdapter);



   }



}