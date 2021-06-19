package org.group9.fooddelivery.ui.dashboard;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.MenuListItemBinding;
import org.group9.fooddelivery.databinding.OrderListItemBinding;

import java.util.List;
import java.util.Map;


public class OrderAdapter
        extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Activity activity;
    private final LayoutInflater layoutInflater;


    /**
     * 保存外部传入的监听器
     */
    private org.group9.fooddelivery.ui.menu.FoodAdapter.ViewOnClickListener listener;
    private List<Map<String,Object>> orders;


    public OrderAdapter(Activity activity, List<Map<String,Object>> orders
    ) {
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
        this.orders=orders;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListItemBinding bd = DataBindingUtil.inflate(layoutInflater,
                R.layout.order_list_item, parent, false);
        return new OrderViewHolder(bd);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }



    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        StringBuilder stringBuilder = new StringBuilder();
        int [] foodsname = (int[]) orders.get(position).get("orderfoods");
        for (Integer i:foodsname
             ) {
            stringBuilder.append(activity.getResources().getText(i));
            stringBuilder.append("\n");
        }




        holder.bd.orderstate.setText((Integer)orders.get(position).get("orderstate"));
        holder.bd.orderfoods.setText(stringBuilder.toString());
        holder.bd.orderPrice.setText((Integer)orders.get(position).get("orderPrice"));
        holder.bd.ordernumber.setText((Integer)orders.get(position).get("ordernumber"));
        holder.bd.reorderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final OrderListItemBinding bd;

        public OrderViewHolder(OrderListItemBinding bd) {
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
