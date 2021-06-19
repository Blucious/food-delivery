package org.group9.fooddelivery.ui.menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.CategoriesListItemBinding;

import java.util.List;
import java.util.Map;

public class CategoriesAdapter
   extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
   private ViewOnClickListener listener;
   private List<Map<String,Object>> Categories;


   public CategoriesAdapter(Activity activity, List<Map<String,Object>> Categories
                            ) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.Categories=Categories;
   }


   @NonNull
   @Override
   public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      CategoriesListItemBinding bd = DataBindingUtil.inflate(layoutInflater,
              R.layout.categories_list_item, parent, false);
      return new CategoriesViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return Categories.size();
   }



   @Override
   public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
      holder.bd.CategoriesButton.setImageResource((Integer) Categories.get(position).get("CategortIcon"));
      holder.bd.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null) {
//               listener.onClick(stu, position);
            }
         }
      });
   }

   public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
      private final CategoriesListItemBinding bd;

      public CategoriesViewHolder(CategoriesListItemBinding bd) {
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
