package org.group9.fooddelivery.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.group9.fooddelivery.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

   private FragmentUserBinding bd;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

      bd = FragmentUserBinding.inflate(getLayoutInflater());

      initView();

      return bd.getRoot();
   }


   private void initView() {
   }

}