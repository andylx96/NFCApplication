package io.github.andylx96.nfcapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {
private TextView mainMenuID;
private FirebaseAuth firebaseAuth;
private DatabaseReference databaseReference;
private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainMenuID = (TextView)findViewById(R.id.mainMenuID);
        editProfileButton = (Button)findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, EditMyProfileActivity.class));
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());

        databaseReference.child("MyProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                HashMap value = (HashMap)dataSnapshot.getValue();
//
//                mainMenuID.setText(mainMenuID.getText().toString() + "\n"
//                        +"Name:" + value.get("name")+"\n"+"Info:" +
//                value.get("info"));

                UserInfomation userInfomation = dataSnapshot.getValue(UserInfomation.class);

                mainMenuID.setText(userInfomation.getInfo()+"\n"+userInfomation.getName()+"\n"+
                        userInfomation.getOtherInfomationArrayList().get(0).name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mainMenuID.setText(firebaseUser.getEmail()+"\n"+firebaseAuth.getCurrentUser().getUid()+"\n"
                + databaseReference.child("MyProfile"));

    }
}
