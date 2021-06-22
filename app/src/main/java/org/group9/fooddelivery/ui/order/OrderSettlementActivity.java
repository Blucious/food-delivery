package org.group9.fooddelivery.ui.order;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityOrderSettlementBinding;
import org.group9.fooddelivery.entity.Cart;
import org.group9.fooddelivery.entity.DeliveryAddress;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.UiConstants;
import org.group9.fooddelivery.ui.address.AddressListActivity;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.Request;


public class OrderSettlementActivity extends NavigableAppCompatActivity {

   private Cart cart;

   private ActivityOrderSettlementBinding bd;

   private RecyclerView cartItemsRecyclerView;
   private OrderCartItemsAdapter cartItemsAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      bd = ActivityOrderSettlementBinding.inflate(getLayoutInflater());
      View view = bd.getRoot();
      setContentView(view);

      initViews();

      intViewData();

      setListeners();
   }

   @Override
   protected void onStart() {
      super.onStart();

      updateDeliveryAddressView();
      updateCartDataAndView();
   }

   public void initViews() {

      // 购物车项相关
      {
         cartItemsRecyclerView = bd.cartItemList;
         cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         cartItemsAdapter = new OrderCartItemsAdapter(this);
         cartItemsAdapter.setOnCartItemQuantityChangedListener(new OrderCartItemsAdapter.OnCartItemQuantityChangedListener() {
            @Override
            public void onChanged() {
               updateCartDataAndView();
            }
         });
         cartItemsRecyclerView.setAdapter(cartItemsAdapter);
      }
   }

   private void intViewData() {
      bd.tablewares.setText("0");
   }

   private void setListeners() {

      bd.addressSelectionButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(OrderSettlementActivity.this, AddressListActivity.class)
               .putExtra(UiConstants.SELECTION_MODE, true);
            startActivity(intent);
         }
      });

      bd.payButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            onPayButtonClick();
         }
      });
   }

   private void updateDeliveryAddressView() {
      DeliveryAddress da = getAppCtx().getDeliveryAddress();
      if (da == null) {
         getToastHelper().showShort("未选择收货地址");
         finish();
      } else {
         bd.receiverAddress.setText(da.getReceiverAddress());
         bd.receiverName.setText(da.getReceiverName());
         bd.receiverPhone.setText(da.getReceiverPhoneNumber());
         bd.tag.setText(da.getTag());
      }
   }

   private void updateCartDataAndView() {

      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_cart_detailForCurrentUser))
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(this) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               cart = ((JSONObject) result.getData()).toJavaObject(Cart.class);

               updateCartView();
            });
            return true;
         }
      };
      new CommonCallback(this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }

   @SuppressLint("SetTextI18n")
   private void updateCartView() {
      if (cart == null) {
         return;
      }

      if (cart.size() == 0) {
         finish();
         return;
      }

      cartItemsAdapter.setDataAndNotifyChanged(cart.getCartItems());

      bd.totalProductsPrice.setText("￥" + cart.getProductsTotalPrice());
      bd.deliveryFee.setText("￥" + cart.getDeliveryFee());
      bd.totalPrice.setText("￥" + cart.getTotalPrice());
      bd.bottomTotalPrice.setText("" + cart.getTotalPrice());
   }

   private void onPayButtonClick() {

      DeliveryAddress deliveryAddress = getAppCtx().getDeliveryAddress();
      if (deliveryAddress == null) {
         getToastHelper().showShort("未设置收货地址");
         return;
      }

      String tablewares = bd.tablewares.getText().toString();
      if (StringUtils.isEmpty(tablewares)) {
         getToastHelper().showShort("请输入餐具份数");
         return;
      }
      String remark = bd.remark.getText().toString();


      getToastHelper().showShort("支付成功");
      try {
         Thread.sleep(500);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      FormBody formBody = new FormBody.Builder()
         .add("deliveryAddressId", "" + deliveryAddress.getId())
         .add("remark", remark)
         .add("tablewares", "" + tablewares)
         .build();
      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_order_createOrder))
         .post(formBody)
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(OrderSettlementActivity.this) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               getToastHelper().showShort("订单创建成功");

               Intent intent = new Intent(OrderSettlementActivity.this, OrderDetailActivity.class);
               startActivity(intent);
            });
            return true;
         }
      };
      new CommonCallback(OrderSettlementActivity.this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }

}