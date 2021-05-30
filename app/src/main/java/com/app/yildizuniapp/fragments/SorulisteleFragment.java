package com.app.yildizuniapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.adapters.SoruListeleAdapter;

import static com.app.yildizuniapp.utils.Utils.sinavsorulari;

public class SorulisteleFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RecyclerView RecyclerViewSoruListele;
    SoruListeleAdapter listeleadapter;
    ImageView btnBack1;

    public SorulisteleFragment() {

    }

    public static SorulisteleFragment newInstance(String param1, String param2) {
        SorulisteleFragment fragment = new SorulisteleFragment();
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
        View v = inflater.inflate(R.layout.fragment_sorulistele, container, false);
        RecyclerViewSoruListele = v.findViewById(R.id.RecyclerViewSoruListele);
        RecyclerViewSoruListele.setLayoutManager(new LinearLayoutManager(getContext()));
        btnBack1 = v.findViewById(R.id.btnBack1);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                MenuFragment menuFragment = new MenuFragment();
                manager.beginTransaction().replace(
                        R.id.fragment_frame,
                        menuFragment,
                        "category"
                ).addToBackStack("tag").commitAllowingStateLoss();
            }
        });
            listeleadapter = new SoruListeleAdapter(getContext(), sinavsorulari);
            RecyclerViewSoruListele.setAdapter(listeleadapter);


        return v;
    }
}