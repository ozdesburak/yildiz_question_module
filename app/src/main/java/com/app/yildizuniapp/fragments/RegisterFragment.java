package com.app.yildizuniapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.yildizuniapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    EditText editname, editsurname, editphone, editemail, editpassword, editpasswordagain;
    String name, surname, phone, email, password, passwordagain;
    ImageView btnBack;
    Button btnUyeOl;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        btnBack = v.findViewById(R.id.btnBack);
        editname = v.findViewById(R.id.editname);
        editsurname = v.findViewById(R.id.editsurname);
        editphone = v.findViewById(R.id.editphone);
        editemail = v.findViewById(R.id.editemail);
        editpassword = v.findViewById(R.id.editpassword);
        editpasswordagain = v.findViewById(R.id.editpasswordagain);

        btnUyeOl = v.findViewById(R.id.btnUyeOl);
        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();

        btnUyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editname.getText().toString();
                surname = editsurname.getText().toString();
                phone = editphone.getText().toString();
                email = editemail.getText().toString();
                password = editpassword.getText().toString();
                passwordagain = editpasswordagain.getText().toString();
                if (!name.equals("") && !surname.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !passwordagain.equals("")) {
                    if (password.equals(passwordagain)) {
                        if (password.length() > 7) {
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                new AlertDialog.Builder(getContext())
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .setTitle("Hoşgeldin")
                                                        .setMessage("Kayıt Başarılı")
                                                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                                                LoginFragment loginFragment = new LoginFragment();
                                                                manager.beginTransaction().replace(
                                                                        R.id.fragment_frame,
                                                                        loginFragment,
                                                                        "category"
                                                                ).addToBackStack("tag").commitAllowingStateLoss();
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                task.getException().getMessage();
                                                Toast.makeText(getContext(), "Bu Mail Zaten Kayıtlı", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            new AlertDialog.Builder(getContext())
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Dikkat")
                                    .setMessage("Şifreniz en az 7 karakterli olmalıdır.")
                                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();

                        }

                    } else {
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Dikkat")
                                .setMessage("Şifrelerin aynı olduğundan emin olun.")
                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Dikkat")
                            .setMessage("Bütün bilgileri doldurduğunuzdan emin olun.")
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                LoginFragment loginFragment = new LoginFragment();
                manager.beginTransaction().replace(
                        R.id.fragment_frame,
                        loginFragment,
                        "category"
                ).addToBackStack("tag").commitAllowingStateLoss();
            }
        });
        return v;
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Kayıt başarısız.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        }
                    }
                });
    }
}