package org.group9.fooddelivery.ui.address;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.group9.fooddelivery.R;
import org.group9.fooddelivery.databinding.ListItemAddressBinding;

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
   private List<Map<String, Object>> address;


   public AddressAdapter(Activity activity, List<Map<String, Object>> address
   ) {
      this.activity = activity;
      layoutInflater = activity.getLayoutInflater();
      this.address = address;
   }




   @NonNull
   @Override
   public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ListItemAddressBinding bd = DataBindingUtil.inflate(layoutInflater,
         R.layout.list_item_address, parent, false);
      return new AddressViewHolder(bd);
   }

   @Override
   public int getItemCount() {
      return address.size();
   }


   @Override
   public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
      Log.d("adapter", "onBindViewHolder: "+(String)address.get(position).get("address"));
      Integer id = (Integer) address.get(position).get("id");
      holder.bd.address.setText((String)address.get(position).get("address"));
      holder.bd.tag.setText((String)address.get(position).get("tag"));
//      holder.bd.addressoutline.setText((String) address.get(position).get("addressoutline"));
      holder.bd.phone.setText((String) address.get(position).get("phone"));
      holder.bd.receivername.setText((String) address.get(position).get("receivername"));
      holder.bd.editaddress.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(activity, AddressUpdatingActivity.class);

            intent.putExtra("address", (String) address.get(position).get("address"));
            intent.putExtra("id", id);
            intent.putExtra("tag", (String) address.get(position).get("tag"));
            intent.putExtra("phone", (String) address.get(position).get("phone"));
            intent.putExtra("receivername", (String) address.get(position).get("receivername"));
            activity.startActivityForResult(intent,1);
         }
      });

   }


   public static class AddressViewHolder extends RecyclerView.ViewHolder {
      private final ListItemAddressBinding bd;

      public AddressViewHolder(ListItemAddressBinding bd) {
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
