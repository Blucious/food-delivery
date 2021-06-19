package org.group9.fooddelivery.ui.Order;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.OrderDetailFoodsListBinding;

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
    private List<Map<String,Object>> Foods;


    public OrderDetailFoodItemsAdapter(Activity activity, List<Map<String,Object>> Foods
    ) {
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
        this.Foods=Foods;
    }


    @NonNull
    @Override
    public OrderDetailFoodItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderDetailFoodsListBinding bd = DataBindingUtil.inflate(layoutInflater,
                R.layout.order_detail_foods_list, parent, false);
        return new OrderDetailFoodItemsViewHolder(bd);
    }

    @Override
    public int getItemCount() {
        return Foods.size();
    }



    @Override
    public void onBindViewHolder(@NonNull OrderDetailFoodItemsViewHolder holder, int position) {
        holder.bd.FoodDetail.setImageResource((Integer) Foods.get(position).get("FoodImg"));
        holder.bd.FoodName.setText((Integer) Foods.get(position).get("FoodName"));
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

    public static class OrderDetailFoodItemsViewHolder extends RecyclerView.ViewHolder {
        private final OrderDetailFoodsListBinding bd;

        public OrderDetailFoodItemsViewHolder(OrderDetailFoodsListBinding bd) {
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
