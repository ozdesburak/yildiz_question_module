package com.app.yildizuniapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.yildizuniapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    EditText editemail1,editpassword1;
    String email,password;
    TextView txtdonthaveaccount;
    private FirebaseAuth mAuth;
    Button btnLogin;
    int count=0;

    public LoginFragment() {

    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        txtdonthaveaccount = v. findViewById(R.id.txtdonthaveaccount);
        editemail1 = v.findViewById(R.id.editemail1);
        editpassword1 = v.findViewById(R.id.editpassword1);
        btnLogin = v.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editemail1.getText().toString();
                password = editpassword1.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "GİRİŞ BAŞARILI",
                                            Toast.LENGTH_SHORT).show();
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    MenuFragment menuFragment = new MenuFragment();
                                    manager.beginTransaction().replace(
                                            R.id.fragment_frame,
                                            menuFragment,
                                            "category"
                                    ).addToBackStack("tag").commitAllowingStateLoss();
                                } else {
                                    count++;
                                    if(count==3) {
                                        new AlertDialog.Builder(getContext())
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Dikkat")
                                                .setMessage("Şifreni 3 kere hatalı girdin. Daha sonra tekrar dene.")
                                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                        getActivity().finish();
                                                    }
                                                })
                                                .show();
                                    }
                                    Toast.makeText(getContext(), "MAİLİN VEYA ŞİFREN HATALI",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        txtdonthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                RegisterFragment registerFragment = new RegisterFragment();
                manager.beginTransaction().replace(
                        R.id.fragment_frame,
                        registerFragment,
                        "category"
                ).addToBackStack("tag").commitAllowingStateLoss();
            }
        });
        return v;
    }
}