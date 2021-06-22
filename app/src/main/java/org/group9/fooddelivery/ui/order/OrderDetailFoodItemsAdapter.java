package org.group9.fooddelivery.ui.order;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemOrderDetailFoodBinding;

import java.util.List;
import java.util.Map;

public class OrderDetailFoodItemsAdapter
   extends RecyclerView.Adapter<OrderDetailFoodItemsAdapter.OrderDetailFoodItemsViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
   private ViewOnClickListener listener;
   private List<Map<String, Object>> foods;


   public OrderDetailFoodItemsAdapter(Activity activity, List<Map<String, Object>> foods) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.foods = foods;
   }


   @NonNull
   @Override
   public OrderDetailFoodItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemOrderDetailFoodBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_order_detail_food, parent, false);
      return new OrderDetailFoodItemsViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return foods.size();
   }


   @Override
   public void onBindViewHolder(@NonNull OrderDetailFoodItemsViewHolder holder, int position) {
      holder.bd.foodDetail.setImageResource((Integer) foods.get(position).get("FoodImg"));
      holder.bd.foodName.setText((Integer) foods.get(position).get("FoodName"));
      holder.bd.price.setText((Integer) foods.get(position).get("Price"));
      holder.bd.number.setText((Integer) foods.get(position).get("number"));

      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null) {
//               listener.onClick(stu, position);
            }
         }
      });
   }

   public static class OrderDetailFoodItemsViewHolder extends RecyclerView.ViewHolder {
      private final ListItemOrderDetailFoodBinding bd;

      public OrderDetailFoodItemsViewHolder(ListItemOrderDetailFoodBinding bd) {
         super(bd.getRoot());
         this.bd = bd;
      }
   }

   public interface ViewOnClickListener {
//      void onClick(Student student, int position);
   }

   public interface OperationDoneListener {
      void onDone();
   }

}
