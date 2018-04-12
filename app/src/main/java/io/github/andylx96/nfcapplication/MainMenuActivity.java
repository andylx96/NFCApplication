package io.github.andylx96.nfcapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
    private Button editProfileButton, addOtherButton, viewOtherProfileButton, shareDataButton;
    private Spinner spinner;
    //    UserInfomation userInfomation;
    ProfileAccount myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainMenuID = (TextView) findViewById(R.id.mainMenuID);
        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        addOtherButton = (Button) findViewById(R.id.addOtherButton);
        viewOtherProfileButton = (Button) findViewById(R.id.viewOtherProfileButton);
        shareDataButton = (Button) findViewById(R.id.shareDataButton);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, EditMyProfileActivity.class));
            }
        });

        viewOtherProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, ViewOtherProfileActivity.class));

            }
        });
        shareDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, ShareDataActivity.class));

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());

        databaseReference.child("MyProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


//                userInfomation = dataSnapshot.getValue(UserInfomation.class);

                myProfile = dataSnapshot.getValue(ProfileAccount.class);
                if (myProfile != null) {
                    if (myProfile.getMyProfile() != null) {

                        String returnString = "Name: " +myProfile.getMyProfile().getName() + "\n"+ "Info: " +myProfile.getMyProfile().getInfo() + "\n";

                        for (int i = 0; i < myProfile.getMyProfile().getOtherInfomationArrayList().size(); i++) {
                            returnString = returnString + "Name: " +myProfile.getMyProfile().getOtherInfomationArrayList().get(i).name + "\n"
                                    + "Info: " +myProfile.getMyProfile().getOtherInfomationArrayList().get(i).info + "\n";
                        }
                        mainMenuID.setText(returnString);

//                        mainMenuID.setText(myProfile.getMyProfile().getInfo() + "\n" + myProfile.getMyProfile().getName() + "\n" +
//                                myProfile.getMyProfile().getOtherInfomationArrayList().get(0).name + "\n" +
//                                myProfile.getMyProfile().getOtherInfomationArrayList().get(0).info + "\n");
                    } else {
                        mainMenuID.setText("My profile not found");
                    }
                } else {
                    myProfile = new ProfileAccount();
                    mainMenuID.setText(myProfile.getMyProfile().getInfo() + "\n" + myProfile.getMyProfile().getName());
                }


//                if (userInfomation != null) {
////                    mainMenuID.setText(userInfomation.getInfo() + "\n" + userInfomation.getName() + "\n" +
////                            userInfomation.getOtherInfomationArrayList().get(0).name + "\n" +
////                            userInfomation.getOtherInfomationArrayList().get(0).info + "\n");
////                } else {
////                    userInfomation = new UserInfomation();
////                    mainMenuID.setText(userInfomation.getInfo() + "\n" + userInfomation.getName());
////                }

//                ArrayAdapter<OtherInfomation> adapter =
//                        new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userInfomation.getOtherInfomationArrayList());
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                spinner.setAdapter(adapter);


                ArrayAdapter<OtherInfomation> adapter =
                        new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myProfile.getMyProfile().getOtherInfomationArrayList());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        addOtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, AddOtherActivity.class));


            }
        });


        mainMenuID.setText(firebaseUser.getEmail() + "\n" + firebaseAuth.getCurrentUser().getUid() + "\n"
                + databaseReference.child("MyProfile"));

    }

//    public UserInfomation getUserInfomation() {
//        return userInfomation;
//    }
}
