package org.group9.fooddelivery.ui.foodmenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.databinding.ActivityFoodMenuBinding;
import org.group9.fooddelivery.entity.Cart;
import org.group9.fooddelivery.entity.CartItem;
import org.group9.fooddelivery.entity.ProductCategory;
import org.group9.fooddelivery.entity.ProductVO;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.address.AddressListActivity;
import org.group9.fooddelivery.ui.common.NavigableAppCompatActivity;
import org.group9.fooddelivery.ui.order.OrderSettlementActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.Request;


public class FoodMenuActivity extends NavigableAppCompatActivity {

   public ActivityFoodMenuBinding bd;

   private RecyclerView categoriesRecyclerView;
   private ProductCategoriesAdapter productCategoriesAdapter;
   private RecyclerView foodRecycleView;
   private ProductsAdapter productsAdapter;

   private Button settlingButton;
   private TextView productsTotalPrice;
   private TextView deliveryFee;

   private Cart cart;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      bd = DataBindingUtil.setContentView(
         this, R.layout.activity_food_menu);

      initView();

      updateDataAndViews();
   }

   public void initView() {
      settlingButton = findViewById(R.id.settlingButton);
      categoriesRecyclerView = findViewById(R.id.categoriesIcon);
      foodRecycleView = findViewById(R.id.menuRecyclerView);
      productsTotalPrice = findViewById(R.id.productsTotalPrice);
      deliveryFee = findViewById(R.id.deliveryFee);


      bd.choiceAddressButton.setOnClickListener(v -> {
         Intent intent = new Intent(FoodMenuActivity.this, AddressListActivity.class);
         startActivity(intent);
      });

      settlingButton.setOnClickListener(v -> {
         Intent intent = new Intent(FoodMenuActivity.this, OrderSettlementActivity.class);
         startActivity(intent);
      });

      // 类别列表初始化
      {
         categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         //categoriesRecyclerView.addItemDecoration(
         //   new DividerItemDecoration(
         //      categoriesRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

         productCategoriesAdapter = new ProductCategoriesAdapter(this);
         categoriesRecyclerView.setAdapter(productCategoriesAdapter);
      }

      // 产品列表初始化
      {
         foodRecycleView.setLayoutManager(new LinearLayoutManager(this));
         foodRecycleView.addItemDecoration(
            new DividerItemDecoration(
               foodRecycleView.getContext(), DividerItemDecoration.VERTICAL));

         productsAdapter = new ProductsAdapter(this);
         productsAdapter.setOnCartItemQuantityChangedListener(() -> {
            updateCartDataAndView(false);
         });
         foodRecycleView.setAdapter(productsAdapter);
      }
   }

   private void updateDataAndViews() {
      updateProductCategoriesDataAndView();
   }

   private void updateProductCategoriesDataAndView() {

      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_productCategory_list))
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(this) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               List<ProductCategory> productCategories =
                  ((JSONArray) result.getData()).toJavaList(ProductCategory.class);

               productCategoriesAdapter.setDataAndNotifyChanged(productCategories);

               updateCartDataAndView(true);
            });
            return true;
         }
      };
      new CommonCallback(this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }

   private void updateCartDataAndView(boolean cascadeToProducts) {

      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_cart_detailForCurrentUser))
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(this) {
         @SuppressLint("SetTextI18n")
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               cart = ((JSONObject) result.getData()).toJavaObject(Cart.class);

               productsTotalPrice.setText("" + cart.getProductsTotalPrice());
               deliveryFee.setText("" + cart.getDeliveryFee());

               if (cascadeToProducts) {
                  updateProductsDataAndView();
               }
            });
            return true;
         }
      };
      new CommonCallback(this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }

   private void updateProductsDataAndView() {

      Request req = new Request.Builder()
         .url(ApiConstants.apiUrl(ApiConstants.api_product_list))
         .build();

      CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(this) {
         @Override
         public boolean handle200(@Nonnull Result result) {
            runOnUiThread(() -> {
               List<ProductVO> products =
                  ((JSONArray) result.getData()).toJavaList(ProductVO.class);

               for (ProductVO product : products) {
                  CartItem cartItem = cart.getCartItem(product.getId());
                  if (cartItem != null) {
                     product.setQuantity(cartItem.getQuantity());
                  } else {
                     product.setQuantity(0);
                  }
               }

               productsAdapter.setDataAndNotifyChanged(products);
            });
            return true;
         }
      };
      new CommonCallback(this, responseHandler)
         .enqueueTo(getAppCtx().getHttpClient().newCall(req));
   }
}