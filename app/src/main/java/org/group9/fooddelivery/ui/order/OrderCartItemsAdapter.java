package org.group9.fooddelivery.ui.order;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.context.FoodDeliveryApplication;
import org.group9.fooddelivery.databinding.ListItemOrderFoodsBinding;
import org.group9.fooddelivery.entity.CartItem;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;
import org.group9.fooddelivery.ui.foodmenu.ProductsAdapter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.Request;

public class OrderCartItemsAdapter
   extends RecyclerView.Adapter<OrderCartItemsAdapter.OrderFoodItemsViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   private List<CartItem> data;
   private OnCartItemQuantityChangedListener onCartItemQuantityChangedListener;


   public OrderCartItemsAdapter(Activity activity) {
      this(activity, null);
   }

   public OrderCartItemsAdapter(Activity activity, List<CartItem> data) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      setData(data);
   }

   public void setData(List<CartItem> data) {
      if (data == null) {
         data = Collections.emptyList();
      }
      this.data = data;
   }

   public void setDataAndNotifyChanged(List<CartItem> data) {
      setData(data);
      notifyDataSetChanged();
   }

   public void setOnCartItemQuantityChangedListener(OnCartItemQuantityChangedListener onCartItemQuantityChangedListener) {
      this.onCartItemQuantityChangedListener = onCartItemQuantityChangedListener;
   }

   @NonNull
   @Override
   public OrderFoodItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemOrderFoodsBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_order_foods, parent, false);
      return new OrderFoodItemsViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return data.size();
   }


   @SuppressLint("SetTextI18n")
   @Override
   public void onBindViewHolder(@NonNull OrderFoodItemsViewHolder holder, int position) {
      CartItem cartItem = data.get(position);

      Picasso.with(activity)
         .load(ApiConstants.resUrl(cartItem.getPictureUrl()))
         .into(holder.bd.foodPicture);
      holder.bd.foodName.setText(cartItem.getName());
      holder.bd.quantity.setText("" + cartItem.getQuantity());
      holder.bd.totalPrice.setText("￥" + cartItem.getPrice().multiply(
         BigDecimal.valueOf(cartItem.getQuantity())));


      holder.bd.subOneButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FormBody formBody = new FormBody.Builder()
               .add("productId", "" + cartItem.getProductId())
               .add("quantity", "" + (cartItem.getQuantity() - 1))
               .build();
            Request req = new Request.Builder()
               .url(ApiConstants.apiUrl(ApiConstants.api_cart_updateByProductId))
               .post(formBody)
               .build();

            CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(activity) {
               @Override
               public boolean handle200(@Nonnull Result result) {
                  activity.runOnUiThread(() -> {
                     Integer quantity = (Integer) result.getData();
                     cartItem.setQuantity(quantity);
                     holder.updateQuantityViewState(quantity);
                     final OnCartItemQuantityChangedListener listener = onCartItemQuantityChangedListener;
                     if (listener != null) {
                        listener.onChanged();
                     }
                  });
                  return true;
               }
            };
            new CommonCallback(activity, responseHandler)
               .enqueueTo(FoodDeliveryApplication.getInstance().getHttpClient().newCall(req));
         }
      });

      holder.bd.addOneButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FormBody formBody = new FormBody.Builder()
               .add("productId", "" + cartItem.getProductId())
               .add("quantity", "" + (cartItem.getQuantity() + 1))
               .build();
            Request req = new Request.Builder()
               .url(ApiConstants.apiUrl(ApiConstants.api_cart_updateByProductId))
               .post(formBody)
               .build();

            CommonJsonResponseHandler responseHandler = new CommonJsonResponseHandler(activity) {
               @Override
               public boolean handle200(@Nonnull Result result) {
                  activity.runOnUiThread(() -> {
                     Integer quantity = (Integer) result.getData();
                     cartItem.setQuantity(quantity);
                     holder.updateQuantityViewState(quantity);
                     final OnCartItemQuantityChangedListener listener = onCartItemQuantityChangedListener;
                     if (listener != null) {
                        listener.onChanged();
                     }
                  });
                  return true;
               }
            };
            new CommonCallback(activity, responseHandler)
               .enqueueTo(FoodDeliveryApplication.getInstance().getHttpClient().newCall(req));
         }
      });

      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
      });
   }

   public static class OrderFoodItemsViewHolder extends RecyclerView.ViewHolder {
      private final ListItemOrderFoodsBinding bd;

      public OrderFoodItemsViewHolder(ListItemOrderFoodsBinding bd) {
         super(bd.getRoot());
         this.bd = bd;
      }

      @SuppressLint("SetTextI18n")
      public void updateQuantityViewState(int quantity) {
         bd.quantity.setText("" + quantity);
         bd.subOneButton.setEnabled(quantity != 0);
      }
   }

   public interface OnCartItemQuantityChangedListener {
      void onChanged();
   }

}
