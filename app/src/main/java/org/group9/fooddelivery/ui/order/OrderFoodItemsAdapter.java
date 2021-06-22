package org.group9.fooddelivery.ui.order;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemOrderFoodsBinding;

import java.util.List;
import java.util.Map;

public class OrderFoodItemsAdapter
   extends RecyclerView.Adapter<OrderFoodItemsAdapter.OrderFoodItemsViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
   private ViewOnClickListener listener;
   private List<Map<String, Object>> Foods;


   public OrderFoodItemsAdapter(Activity activity, List<Map<String, Object>> foods) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.Foods = foods;
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
      return Foods.size();
   }


   @Override
   public void onBindViewHolder(@NonNull OrderFoodItemsViewHolder holder, int position) {
      holder.bd.foodDetail.setImageResource((Integer) Foods.get(position).get("FoodImg"));
      holder.bd.foodName.setText((Integer) Foods.get(position).get("FoodName"));
      holder.bd.price.setText((Integer) Foods.get(position).get("Price"));
      holder.bd.number.setText((Integer) Foods.get(position).get("number"));

      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null) {
//               listener.onClick(stu, position);
            }
         }
      });
   }

   public static class OrderFoodItemsViewHolder extends RecyclerView.ViewHolder {
      private final ListItemOrderFoodsBinding bd;

      public OrderFoodItemsViewHolder(ListItemOrderFoodsBinding bd) {
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
