package com.app.yildizuniapp.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

public class SinavayarFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageView btnBack1;
    EditText editSinavSuresi,editSoruPuani;
    Button btnSave;
    SharedPreferences preferences;
    Spinner spinnerzorluk;
    public static int getzorlukduzeyi;
    SharedPreferences.Editor editor;

    public SinavayarFragment() {

    }

    public static SinavayarFragment newInstance(String param1, String param2) {
        SinavayarFragment fragment = new SinavayarFragment();
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
        View v = inflater.inflate(R.layout.fragment_sinavayar, container, false);
        btnBack1 = v.findViewById(R.id.btnBack1);
        editSinavSuresi = v.findViewById(R.id.editSinavSuresi);
        editSoruPuani = v.findViewById(R.id.editSoruPuani);
        spinnerzorluk = v.findViewById(R.id.spinnerzorluk);
        btnSave = v.findViewById(R.id.btnSave);
        String[] zorlukseviyesi = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, zorlukseviyesi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerzorluk.setAdapter(adapter);
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
        final SharedPreferences sp = getActivity().getSharedPreferences("MySharedPref", Activity.MODE_PRIVATE);
        editor = sp.edit();
        String getsinavsuresi = sp.getString("saved_sinavsuresi","");
        String getsorupuani = sp.getString("saved_sorupuani","");
        getzorlukduzeyi = sp.getInt("saved_zorlukduzeyi",-1);


        if(getsinavsuresi !=null) {
            editSinavSuresi.setText(getsinavsuresi);
        }

        if(getsorupuani !=null) {
            editSoruPuani.setText(getsorupuani);
        }

        if(getzorlukduzeyi>=0) {
            spinnerzorluk.setSelection(getzorlukduzeyi);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean aynivarmi=false;
                preferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                editor = preferences.edit();
                String sinav_suresi = "saved_sinavsuresi";
                String soru_puani = "saved_sorupuani";
                String zorluk_duzeyi = "saved_zorlukduzeyi";

                String sinav_suresi1 = editSinavSuresi.getText().toString();
                String soru_puani1 = editSoruPuani.getText().toString();
                int zorluk_duzeyi1 = spinnerzorluk.getSelectedItemPosition();

                editor.putString(sinav_suresi, sinav_suresi1);
                editor.putString(soru_puani, soru_puani1);
                editor.putInt(zorluk_duzeyi, zorluk_duzeyi1);
                editor.commit();
                for(int i=0; i<Utils.zorlukduzeyi.size();i++) {
                    if(Utils.zorlukduzeyi.get(i).equals(spinnerzorluk.getSelectedItem().toString())) {
                        aynivarmi = true;
                    }
                }
                if (!aynivarmi || Utils.zorlukduzeyi.size()==0) Utils.zorlukduzeyi.add(spinnerzorluk.getSelectedItem().toString());

            }
        });

        return v;
    }
}