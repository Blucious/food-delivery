package org.group9.fooddelivery.ui.foodmenu;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemMenuFoodBinding;

import java.util.List;
import java.util.Map;

public class FoodAdapter
   extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
   private ViewOnClickListener listener;
   private List<Map<String, Object>> foods;


   public FoodAdapter(Activity activity, List<Map<String, Object>> foods) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.foods = foods;
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
      return foods.size();
   }


   @Override
   public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
      holder.bd.FoodDetail.setImageResource((Integer) foods.get(position).get("FoodImg"));
      holder.bd.FoodName.setText((Integer) foods.get(position).get("FoodName"));
      holder.bd.price.setText((Integer) foods.get(position).get("Price"));
      holder.bd.selected.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            TextView textView = activity.findViewById(R.id.shoppingCartPrice);
            String priceWithChiese = ((String) textView.getText());
            int sum = Integer.parseInt(priceWithChiese.substring(0, priceWithChiese.length() - 1));
            sum += Integer.parseInt((String) holder.bd.price.getText());
            textView.setText(sum + "元");
         }
      });
      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null) {
//               listener.onClick(stu, position);
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
   }

   public interface ViewOnClickListener {
//      void onClick(Student student, int position);
   }

   public interface OperationDoneListener {
      void onDone();
   }

}
