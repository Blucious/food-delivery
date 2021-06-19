package org.group9.fooddelivery.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.group9.fooddelivery.MainActivity;
import org.group9.fooddelivery.databinding.FragmentHomeBinding;
import org.group9.fooddelivery.ui.menu.Menudelivery;

public class HomeFragment extends Fragment {

   private HomeViewModel homeViewModel;
   private FragmentHomeBinding fragmentHomeBinding;

   private ImageButton imageButton;


   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

         fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);
         View view = fragmentHomeBinding.getRoot();
         init();
         return view;

//      homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//      View root = inflater.inflate(R.layout.fragment_home, container, false);
//      final TextView textView = root.findViewById(R.id.text_home);
//      homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//         @Override
//         public void onChanged(@Nullable String s) {
//            textView.setText(s);
//         }
//      });
//      return root;
   }


   public void init(){
      imageButton = fragmentHomeBinding.delivery;
      imageButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getContext(), Menudelivery.class);
            startActivity(intent);
         }
      });
   }


   @Override
   public void onDestroyView() {
      super.onDestroyView();
      fragmentHomeBinding = null;
   }




}