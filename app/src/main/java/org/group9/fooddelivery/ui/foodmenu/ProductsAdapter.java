package org.group9.fooddelivery.ui.foodmenu;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.context.ApiConstants;
import org.group9.fooddelivery.context.FoodDeliveryApplication;
import org.group9.fooddelivery.databinding.ListItemMenuFoodBinding;
import org.group9.fooddelivery.entity.ProductVO;
import org.group9.fooddelivery.entity.Result;
import org.group9.fooddelivery.net.CommonCallback;
import org.group9.fooddelivery.net.CommonJsonResponseHandler;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import okhttp3.FormBody;
import okhttp3.Request;

public class ProductsAdapter
   extends RecyclerView.Adapter<ProductsAdapter.FoodViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;

   private List<ProductVO> data;
   private volatile ViewOnClickListener viewOnClickListener;
   private volatile OnCartItemQuantityChangedListener onCartItemQuantityChangedListener;


   public ProductsAdapter(Activity activity) {
      this(activity, null);
   }

   public ProductsAdapter(Activity activity, List<ProductVO> data) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      setData(data);
   }

   public void setData(List<ProductVO> data) {
      if (data == null) {
         data = Collections.emptyList();
      }
      this.data = data;
   }

   public void setDataAndNotifyChanged(List<ProductVO> data) {
      setData(data);
      notifyDataSetChanged();
   }

   public ViewOnClickListener getViewOnClickListener() {
      return viewOnClickListener;
   }

   public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
      this.viewOnClickListener = viewOnClickListener;
   }

   public OnCartItemQuantityChangedListener getOnCartItemQuantityChangedListener() {
      return onCartItemQuantityChangedListener;
   }

   public void setOnCartItemQuantityChangedListener(OnCartItemQuantityChangedListener onCartItemQuantityChangedListener) {
      this.onCartItemQuantityChangedListener = onCartItemQuantityChangedListener;
   }

   @NonNull
   @Override
   public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemMenuFoodBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_menu_food, parent, false);
      return new FoodViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   @SuppressLint("SetTextI18n")
   @Override
   public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
      ProductVO product = data.get(position);


      Picasso.with(activity)
         .load(Uri.parse(ApiConstants.resUrl(product.getPictureUrl())))
         .into(holder.bd.picture);
      holder.bd.name.setText(product.getName());
      holder.bd.price.setText("" + product.getPrice());
      holder.updateQuantityViewState(product.getQuantity());

      holder.bd.subOneButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FormBody formBody = new FormBody.Builder()
               .add("productId", "" + product.getId())
               .add("quantity", "" + (product.getQuantity() - 1))
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
                     product.setQuantity(quantity);
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
               .add("productId", "" + product.getId())
               .add("quantity", "" + (product.getQuantity() + 1))
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
                     product.setQuantity(quantity);
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

            // 调用点击回调
            final ViewOnClickListener listener = viewOnClickListener;
            if (listener != null) {
               listener.onClick(product, position);
            }

         }
      });
   }

   public static class FoodViewHolder extends RecyclerView.ViewHolder {
      private final ListItemMenuFoodBinding bd;

      public FoodViewHolder(ListItemMenuFoodBinding bd) {
         super(bd.getRoot());
         this.bd = bd;
      }

      public void updateQuantityViewState(int quantity) {
         bd.quantity.setText("" + quantity);
         bd.subOneButton.setEnabled(quantity != 0);
      }
   }

   public interface ViewOnClickListener {
      void onClick(ProductVO product, int position);
   }

   public interface OnCartItemQuantityChangedListener {
      void onChanged();
   }

}
