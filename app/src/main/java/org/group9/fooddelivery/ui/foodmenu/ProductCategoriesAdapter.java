package org.group9.fooddelivery.ui.foodmenu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemCategoryBinding;
import org.group9.fooddelivery.entity.ProductCategory;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;


public class ProductCategoriesAdapter
   extends RecyclerView.Adapter<ProductCategoriesAdapter.CategoriesViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;

   private List<ProductCategory> data;
   private WeakReference<CategoriesViewHolder> prevSelectedHolderRef;

   private volatile ViewOnClickListener viewOnClickListener;


   public ProductCategoriesAdapter(Activity activity) {
      this(activity, null);
   }

   public ProductCategoriesAdapter(Activity activity, List<ProductCategory> data) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      setData(data);
   }

   public ViewOnClickListener getViewOnClickListener() {
      return viewOnClickListener;
   }

   public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
      this.viewOnClickListener = viewOnClickListener;
   }

   public void setData(List<ProductCategory> data) {
      if (data == null) {
         data = Collections.emptyList();
      }
      this.data = data;
   }

   public void setDataAndNotifyChanged(List<ProductCategory> data) {
      setData(data);
      notifyDataSetChanged();
   }

   @NonNull
   @Override
   public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemCategoryBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_category, parent, false);
      return new CategoriesViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   @Override
   public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
      ProductCategory productCategory = data.get(position);

      holder.bd.nameTextView.setText(productCategory.getName());
      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            // 处理高亮
            if (prevSelectedHolderRef != null) {
               CategoriesViewHolder prevSelectedHolder = prevSelectedHolderRef.get();
               if (prevSelectedHolder != null) {
                  prevSelectedHolder.bd.nameTextView.setBackgroundColor(activity.getResources().getColor(R.color.rgb_f6f6f6));
               }
            }
            holder.bd.nameTextView.setBackgroundColor(activity.getResources().getColor(R.color.White));
            prevSelectedHolderRef = new WeakReference<>(holder);

            // 调用点击回调
            final ViewOnClickListener listener = viewOnClickListener;
            if (listener != null) {
               listener.onClick(productCategory, position);
            }
         }
      });
   }

   public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
      private final ListItemCategoryBinding bd;

      public CategoriesViewHolder(ListItemCategoryBinding bd) {
         super(bd.getRoot());
         this.bd = bd;
      }
   }

   public interface ViewOnClickListener {
      void onClick(ProductCategory productCategory, int position);
   }

   public interface OperationDoneListener {
      void onDone();
   }

}
