package org.group9.fooddelivery.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.group9.fooddelivery.databinding.FragmentUserBinding;
import org.group9.fooddelivery.ui.address.AddressListActivity;

public class UserFragment extends Fragment {

   private FragmentUserBinding bd;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentUserBinding.inflate(getLayoutInflater());

      initView();

      return bd.getRoot();
   }


   private void initView() {
      bd.addressbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddressListActivity.class);
            startActivity(intent);
         }
      });

      bd.updateinfobutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), UserInfo.class);
            startActivity(intent);
         }
      });
   }

}