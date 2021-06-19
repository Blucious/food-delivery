package org.group9.fooddelivery.ui.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.AddressListItemBinding;
import org.group9.fooddelivery.databinding.CategoriesListItemBinding;

import java.util.List;
import java.util.Map;

public class AddressAdapter
   extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

   private final Activity activity;
   private final LayoutInflater layoutInflater;


   /**
    * 保存外部传入的监听器
    */
//   private ViewOnClickListener listener;
   private List<Map<String,Object>> address;


   public AddressAdapter(Activity activity, List<Map<String,Object>> address
                            ) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.address=address;
   }


   @NonNull
   @Override
   public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      AddressListItemBinding bd = DataBindingUtil.inflate(layoutInflater,
              R.layout.address_list_item, parent, false);
      return new AddressViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return address.size();
   }



   @Override
   public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
      holder.bd.address.setText((Integer) address.get(position).get("address"));
      holder.bd.addressoutline.setText((Integer) address.get(position).get("addressoutline"));
      holder.bd.phone.setText((Integer) address.get(position).get("phone"));
      holder.bd.receivername.setText((Integer) address.get(position).get("receivername"));
      holder.bd.editaddress.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent =  new Intent(activity,updateaddress.class);

            intent.putExtra("address",(Integer) address.get(position).get("address"));
            intent.putExtra("addressoutline",(Integer) address.get(position).get("addressoutline"));
            intent.putExtra("phone",(Integer) address.get(position).get("phone"));
            intent.putExtra("receivername",(Integer) address.get(position).get("receivername"));
            activity.startActivity(intent);
         }
      });

   }

   public static class AddressViewHolder extends RecyclerView.ViewHolder {
      private final AddressListItemBinding bd;

      public AddressViewHolder(AddressListItemBinding bd) {
         super(bd.getRoot());
         this.bd = bd;
      }
   }

//   public interface ViewOnClickListener {
//   }

   public interface OperationDoneListener {
      void onDone();
   }

}
