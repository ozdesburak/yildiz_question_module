package com.app.yildizuniapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.adapters.MenuAdapter;
import com.app.yildizuniapp.listener.RecyclerItemClickListener;
import com.app.yildizuniapp.models.MenuModels;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ArrayList<MenuModels> menuModels;
    MenuAdapter menuAdapter;
    RecyclerView RecyclerViewMenu;

    public MenuFragment() {

    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerViewMenu = v.findViewById(R.id.RecyclerViewMenu);
        RecyclerViewMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        menuModels = new ArrayList<>();
        menuModels.add(new MenuModels(1,"Soru Ekle"));
        menuModels.add(new MenuModels(2,"Soru Listele"));
        menuModels.add(new MenuModels(3,"Sınav Oluştur"));
        menuModels.add(new MenuModels(4,"Sınav Ayarları"));
        menuModels.add(new MenuModels(5,"Çıkış Yap"));

        menuAdapter = new MenuAdapter(getContext(), menuModels);
        RecyclerViewMenu.setAdapter(menuAdapter);

        RecyclerViewMenu.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), RecyclerViewMenu ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Fragment fragment = null;
                        final FragmentManager manager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                        switch (position) {
                            case 0:
                                fragment = new SoruekleFragment();
                                break;
                            case 1:
                                fragment = new SorulisteleFragment();
                                break;
                            case 2:
                                fragment = new SinavolusturFragment();
                                break;
                            case 3:
                                fragment = new SinavayarFragment();
                                break;
                            case 4:
                                fragment = new LoginFragment();
                                break;
                            default:
                                Log.e("CommonType", String.valueOf(position));
                        }
                        if (fragment != null) {
                            manager.beginTransaction().addToBackStack("a").replace(R.id.fragment_frame, fragment, "vb bvc v").commitAllowingStateLoss();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return v;
    }
}