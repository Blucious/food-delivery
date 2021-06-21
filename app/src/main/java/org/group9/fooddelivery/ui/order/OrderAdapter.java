package org.group9.fooddelivery.ui.order;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemOrderBinding;
import org.group9.fooddelivery.entity.Order;

import java.util.List;
import java.util.Map;


public class OrderAdapter
   extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
   private org.group9.fooddelivery.ui.foodmenu.FoodAdapter.ViewOnClickListener listener;
   private List<Map<String, Object>> orders;


   public OrderAdapter(Activity activity, List<Map<String, Object>> orders
   ) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.orders = orders;
   }


   @NonNull
   @Override
   public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemOrderBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_order, parent, false);
      return new OrderViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return orders.size();
   }


   @Override
   public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
      String state =null;
      switch ((Integer) orders.get(position).get("orderstate")){
         case Order.STATE_DELIVERED:
            state ="已送达";
            break;
         case  Order.STATE_DELIVERING:
            state ="正在配送";
            break;
         case Order.STATE_RECEIVED:
            state ="已收货";
            break;
         case Order.STATE_CANCELED:
            state="已取消";
            break;




      }



      holder.bd.orderstate.setText(state);
      holder.bd.orderfoods.setText((String)orders.get(position).get("orderfoods"));
      holder.bd.orderPrice.setText((String) orders.get(position).get("orderPrice"));
      holder.bd.ordernumber.setText((String) orders.get(position).get("ordernumber"));
      holder.bd.reorderbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
      });

   }

   public static class OrderViewHolder extends RecyclerView.ViewHolder {
      private final ListItemOrderBinding bd;

      public OrderViewHolder(ListItemOrderBinding bd) {
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
