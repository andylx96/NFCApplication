package io.github.andylx96.nfcapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main3Activity extends AppCompatActivity {
    private Button regButton;
    private EditText regUsername, regPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        regButton = (Button) findViewById(R.id.RegViewButton);
        regUsername = (EditText) findViewById(R.id.RegTextUsername);
        regPassword = (EditText) findViewById(R.id.RegTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }


    private void RegisterUser() {


        email = regUsername.getText().toString().trim();
        password = regPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if valid
        progressDialog.setMessage("Registering ....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Main3Activity.this, "Reg Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(Main3Activity.this, MainMenuActivity.class));

                        } else {
                            Toast.makeText(Main3Activity.this, "Reg Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

//
    }


}
