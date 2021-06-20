package org.group9.fooddelivery.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.group9.fooddelivery.databinding.FragmentHomeBinding;
import org.group9.fooddelivery.ui.foodmenu.FoodMenuActivity;


public class HomeFragment extends Fragment {

   private FragmentHomeBinding bd;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentHomeBinding.inflate(inflater, container, false);

      initView();

      return bd.getRoot();
   }

   public void initView() {
      bd.deliveryButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), FoodMenuActivity.class);
            startActivity(intent);
         }
      });

   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      bd = null;
   }


}