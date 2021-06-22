package org.group9.fooddelivery.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;

import org.group9.fooddelivery.MainActivity;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.FragmentOrderBinding;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Order;
import org.group9.fooddelivery.entity.OrderItem;
import org.group9.fooddelivery.entity.OrderListItem;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.address.AddressListActivity;
import org.group9.fooddelivery.ui.common.BaseFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.Request;

public class OrderFragment extends BaseFragment {

   private FragmentOrderBinding bd;
   private RecyclerView recyclerView;
   private OrderAdapter orderAdapter;
   private List<Integer> orderstate = new ArrayList<>();
   private  Map<String,StringBuilder> orderfoods=new HashMap<>();
   private  List<BigDecimal> orderPrice=new ArrayList<>();
   private  Map<String,Integer> ordernumber =new HashMap<>();
   private  List<Integer> id =new ArrayList<>();
   private  List<OrderItem> items =new ArrayList<>();




   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentOrderBinding.inflate(getLayoutInflater());

      init();


      return bd.getRoot();
   }


   public void init(){
      Integer userId;
      try {
          userId = getAppCtx().getUserVO().getId();

      }catch (Exception e){
         Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();

         return;

      }

      Request req = new Request.Builder()
              .url(ApiConstants.apiUrl(ApiConstants.api_order_OrderListItemById+"?userId="+ userId))
              .build();
      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(getActivity()) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            getActivity().runOnUiThread(() -> {

               List<OrderListItem> orders = ((JSONArray) result.getData()).toJavaList(OrderListItem.class);

               for (int i = 0; i < orders.size(); i++) {
                  if (!id.contains(orders.get(i).getOrderId())){
                     orderfoods.put(String.valueOf(orders.get(i).getOrderId()),new StringBuilder().append(orders.get(i).getName()));
                     id.add(orders.get(i).getOrderId());
                     orderstate.add(orders.get(i).getState());
                     orderPrice.add(orders.get(i).getTotalprice());
                     ordernumber.put(String.valueOf(orders.get(i).getOrderId()),orders.get(i).getQuantity());

                  }else {
                     int temp =ordernumber.get(String.valueOf(orders.get(i).getOrderId()));
                     ordernumber.put(String.valueOf(orders.get(i).getOrderId()),temp+orders.get(i).getQuantity());
                     orderfoods.get(String.valueOf(orders.get(i).getOrderId())).append("\n");
                     orderfoods.get(String.valueOf(orders.get(i).getOrderId())).append(orders.get(i).getName());
                  }




               }
               initData();

               Log.d("TAG", "handle200: "+id+orderstate+orderPrice+orderfoods+ordernumber);




            });


            return true;
         }
      };

      new CommonCallback(getActivity(), responseHandler)
              .enqueueTo(getAppCtx().getHttpClient().newCall(req));

   }

   public void initData() {
      List<Map<String, Object>> orderData = new ArrayList<>();
      for (int i = 0; i < id.size(); i++) {
         Map<String, Object> map = new HashMap<>();
         map.put("id", id.get(i));
         map.put("orderstate",orderstate.get(i));
         map.put("orderfoods", orderfoods.get(String.valueOf( id.get(i))).toString());
         map.put("ordernumber", String.valueOf(ordernumber.get(String.valueOf( id.get(i)))));
         map.put("orderPrice", orderPrice.get(i).toString());

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