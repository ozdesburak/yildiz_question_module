package com.app.yildizuniapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.models.ListelemeModels;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.app.yildizuniapp.utils.Utils.sinavsorulari;

public class SoruekleFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    ImageView btnBack1;
    Spinner spinner1, spinner2;
    EditText editQuestion, editAnswer1, editAnswer2, editAnswer3, editAnswer4;
    String question,answ1,answ2,answ3,answ4,seviyesi,cevabi,newdata;
    Button btnSave;

    public SoruekleFragment() {

    }

    public static SoruekleFragment newInstance(String param1, String param2) {
        SoruekleFragment fragment = new SoruekleFragment();
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
        View v = inflater.inflate(R.layout.fragment_soruekle, container, false);
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
        String[] dogrucevap = new String[]{"A", "B", "C", "D"};
        String[] zorlukseviyesi = new String[]{"1", "2", "3", "4", "5"};

        spinner1 = v.findViewById(R.id.spinner1);
        spinner2 = v.findViewById(R.id.spinner2);
        editQuestion = v.findViewById(R.id.editQuestion);
        editAnswer1 = v.findViewById(R.id.editAnswer1);
        editAnswer2 = v.findViewById(R.id.editAnswer2);
        editAnswer3 = v.findViewById(R.id.editAnswer3);
        editAnswer4 = v.findViewById(R.id.editAnswer4);
        btnSave = v.findViewById(R.id.btnSave);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dogrucevap);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, zorlukseviyesi);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question = editQuestion.getText().toString();
                answ1 = editAnswer1.getText().toString();
                answ2 = editAnswer2.getText().toString();
                answ3 = editAnswer3.getText().toString();
                answ4 = editAnswer4.getText().toString();
                if(!question.equals("") && !answ1.equals("") && !answ2.equals("") && !answ3.equals("") && !answ4.equals("")) {
                    cevabi = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
                    seviyesi = spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
                    newdata = question + (" ") + answ1 + (" ") + answ2 + (" ") + answ3 + (" ") + answ4 + (" ") + cevabi + (" ") + seviyesi;
                    sinavsorulari.add(new ListelemeModels(question,answ1,answ2,answ3,answ4,cevabi,seviyesi));
                    writeToFile(newdata,getContext());
                    Toast.makeText(getContext(), "SORU EKLENDİ",
                            Toast.LENGTH_SHORT).show();
//                readFromFile(getContext());
                }
                else {
                    Toast.makeText(getContext(), "BOŞLUK BIRAKMADIĞINIZDAN EMİN OLUN",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.append("-");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}