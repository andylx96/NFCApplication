package io.github.andylx96.nfcapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {
private TextView mainMenuID;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainMenuID = (TextView)findViewById(R.id.mainMenuID);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mainMenuID.setText(firebaseUser.getEmail()+"\n"+firebaseAuth.getCurrentUser().getUid());
    }
}
