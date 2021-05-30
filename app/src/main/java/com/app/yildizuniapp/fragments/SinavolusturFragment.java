package com.app.yildizuniapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.adapters.SoruFiltreleAdapter;
import com.app.yildizuniapp.models.ListelemeModels;
import com.app.yildizuniapp.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.app.yildizuniapp.utils.Utils.filtrelenmissorular;
import static com.app.yildizuniapp.utils.Utils.sinavsorulari;
import static com.app.yildizuniapp.utils.Utils.zorlukduzeyi;

public class SinavolusturFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageView btnBack1;
    Spinner spinnerzorlukseviyesi;
    RecyclerView RecyclerViewSoruFiltrele;
    Button btnFiltrele, btnSinavOlustur;
    SoruFiltreleAdapter listeleadapter;
    EditText editSinavSuresi1,editSoruPuani1;
    SharedPreferences.Editor editor;

    public SinavolusturFragment() {

    }

    public static SinavolusturFragment newInstance(String param1, String param2) {
        SinavolusturFragment fragment = new SinavolusturFragment();
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
        View v = inflater.inflate(R.layout.fragment_sinavolustur, container, false);
        btnBack1 = v.findViewById(R.id.btnBack1);
        spinnerzorlukseviyesi = v.findViewById(R.id.spinnerzorlukseviyesi);
        RecyclerViewSoruFiltrele = v.findViewById(R.id.RecyclerViewSoruFiltrele);
        RecyclerViewSoruFiltrele.setLayoutManager(new LinearLayoutManager(getContext()));
        btnFiltrele = v.findViewById(R.id.btnFiltrele);
        btnSinavOlustur = v.findViewById(R.id.btnSinavOlustur);
        editSinavSuresi1 = v.findViewById(R.id.editSinavSuresi1);
        editSoruPuani1 = v.findViewById(R.id.editSoruPuani1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Utils.zorlukduzeyi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerzorlukseviyesi.setAdapter(adapter);
        final SharedPreferences sp = getActivity().getSharedPreferences("MySharedPref", Activity.MODE_PRIVATE);
        editor = sp.edit();
        String getsinavsuresi = sp.getString("saved_sinavsuresi","");
        String getsorupuani = sp.getString("saved_sorupuani","");
        if(getsinavsuresi !=null) {
            editSinavSuresi1.setText(getsinavsuresi);
        }

        if(getsorupuani !=null) {
            editSoruPuani1.setText(getsorupuani);
        }
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
        btnFiltrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtrelenmissorular = new ArrayList<>();
                for (int i = 0; i < sinavsorulari.size(); i++) {
                    if (sinavsorulari.get(i).getZorlukseviye().equals(spinnerzorlukseviyesi.getSelectedItem().toString())) {
                        filtrelenmissorular.add(sinavsorulari.get(i));
                    }

                }
                listeleadapter = new SoruFiltreleAdapter(getContext(), filtrelenmissorular);
                RecyclerViewSoruFiltrele.setAdapter(listeleadapter);

                if(filtrelenmissorular.size()>0) {
                    btnSinavOlustur.setVisibility(View.VISIBLE);
                }
                else {
                    btnSinavOlustur.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Dikkat")
                            .setMessage("Bu Zorluk Düzeyinde Soru Hazırlanmamış. Ayarlar Bölümünden Düzey Ekleyebilir, Soru Ekleme Bölümünden Soruları Ekleyebilirsiniz. ")
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                }
                            });
                    dialog.show();
                }

                btnSinavOlustur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //LİSTE TXT OLARAK KAYDEDİLECEK
                        writeToFile(filtrelenmissorular,getContext());
                        readFromFile(getContext());
                        //MAİL İLE GÖNDERME
                        //birinci deneme
//                        File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + "config.txt");
//                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                        sharingIntent.setType("text/*");
//                        sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
//                        startActivity(Intent.createChooser(sharingIntent, "Dosyayı paylaşın"));
                        //ikinci deneme
//                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                        emailIntent.setType("text/*");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"me@gmail.com"});
//                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//                                "Test Subject");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//                                "go on read the emails");
//                        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("config.txt")));
//                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                        Intent mailIntent = new Intent(Intent.ACTION_SEND);
                        mailIntent.setType("text/Message");
//                        mailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"@gmail.com"});
                        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "SINAV SORULARI");
                        for (int i=0; i<filtrelenmissorular.size();i++) {
                            mailIntent.putExtra(Intent.EXTRA_TEXT   , "SORU : " +filtrelenmissorular.get(i).getSoru()+ "\n"+ "A)" + filtrelenmissorular.get(i).getBirinci() + "\n"+ "B)" + filtrelenmissorular.get(i).getIkinci() + "\n"+ "C)" + filtrelenmissorular.get(i).getUcuncu() + "\n"+ "D)" + filtrelenmissorular.get(i).getDorduncu() + "\n"+ "DOĞRU CEVAP : " + filtrelenmissorular.get(i).getDogrucevap() + "\n"+ "ZORLUK SEVİYESİ : " + filtrelenmissorular.get(i).getZorlukseviye() );
                        }

//                        Uri uri = Uri.fromFile("config.txt");
                        mailIntent.putExtra(Intent.EXTRA_STREAM, "config.txt");
                        startActivity(Intent.createChooser(mailIntent, "Dosyayı paylaşın"));
                    }
                });
            }
        });
        return v;
    }


    private void writeToFile(List<ListelemeModels> data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            for(int i=0;i<filtrelenmissorular.size();i++) {
                outputStreamWriter.write("SORU : " + data.get(i).getSoru() + " A) " +data.get(i).getBirinci() + " B) " +data.get(i).getIkinci() + " C) " +data.get(i).getUcuncu() + " D) " +data.get(i).getDorduncu() + " DOĞRU CEVAP: " +data.get(i).getDogrucevap() + " ZORLUK SEVİYESİ: " +data.get(i).getZorlukseviye());
                outputStreamWriter.append("\n");
            }
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