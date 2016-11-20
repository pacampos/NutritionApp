package com.fearnot.snapp.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    public static String TAG = "LoginFragment";
    Button loginButton;
    public ProgressDialog progress;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //get references
        loginButton = (Button) v.findViewById(R.id.loginButton1);
        loginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        final EditText usernameInput = (EditText) v.findViewById(R.id.usernameInput1);
        final EditText passwordInput = (EditText) v.findViewById(R.id.passwordInput1);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutritionSingleton.getInstance().setCurrProfile(null);
                NutritionSingleton.getInstance().setCurrDay(null);
                // Get rid of whitespace in username field
                String username = usernameInput.getText().toString().replaceAll("\\s+","");
//                ((MainActivity) getActivity()).signIn(username, passwordInput.getText().toString(), getContext());
                loginButton.setEnabled(false);
                loginButton.setBackgroundColor(Color.GRAY);
                progress = new ProgressDialog(getContext());
                progress.setMessage("Retrieving Profiles...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(username, passwordInput.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(getContext(), "Could not sign in, make sure all your info is correct and try again.",
                                            Toast.LENGTH_LONG).show();
                                    loginButton.setEnabled(true);
                                    loginButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                                    progress.hide();
                                    progress.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Signed In", Toast.LENGTH_SHORT).show();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.hide();
                                            progress.dismiss();
                                        }
                                    });
                                    NutritionSingleton.getInstance().SetUser(FirebaseAuth.getInstance().getCurrentUser(), getContext());
                                }
                            }
                        });
            }
        });

        return v;
    }
}
