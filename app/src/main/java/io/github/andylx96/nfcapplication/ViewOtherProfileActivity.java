package io.github.andylx96.nfcapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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


public class ViewOtherProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private TextView viewOtherTextView, viewOtherSelectedProfileTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //    private Spinner viewOtherSpinner;
    //    UserInfomation userInfomation;
    ProfileAccount myProfile;
    private ListView viewOtherList;
    private Spinner viewOtherSpinner;
    private int spinnerIndex, listViewIndex;
    ArrayAdapter<OtherInfomation> adapter;
    private ListView listView;
    //    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_profile);
        viewOtherTextView = (TextView) findViewById(R.id.otherProfleTextView);
        viewOtherSpinner = (Spinner) findViewById(R.id.selectOtherSpinner);
        viewOtherSpinner.setOnItemSelectedListener(this);
        viewOtherList = (ListView) findViewById(R.id.otherProfileList);

        viewOtherList.setOnItemClickListener(this);
       listView = (ListView) findViewById(R.id.otherProfileList);

        viewOtherSelectedProfileTextView = (TextView) findViewById(R.id.viewSelectedProfileText);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());

        databaseReference.child("MyProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


//                userInfomation = dataSnapshot.getValue(UserInfomation.class);

                myProfile = dataSnapshot.getValue(ProfileAccount.class);
                if (myProfile != null) {
                    if (myProfile.getOtherProfile() != null) {

//                        String returnString = "Name: " + myProfile.getOtherProfile().getMyProfile().getName() + "\n" + "Info: " + myProfile.getMyProfile().getInfo() + "\n";
                        String returnString = "";

                        for (int i = 0; i < myProfile.getOtherProfile().size(); i++) {
                            returnString = returnString + "Name: " + myProfile.getOtherProfile().get(i).getName() + "\n"
                                    + "Info: " + myProfile.getOtherProfile().get(i).getInfo() + "\n";
                            for (int j = 0; j < myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().size(); j++) {

                                returnString = returnString + myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().get(j).name + "\n" +
                                        myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().get(j).info + "\n";

                            }

                        }
                        viewOtherTextView.setText(returnString);

//                        mainMenuID.setText(myProfile.getMyProfile().getInfo() + "\n" + myProfile.getMyProfile().getName() + "\n" +
//                                myProfile.getMyProfile().getOtherInfomationArrayList().get(0).name + "\n" +
//                                myProfile.getMyProfile().getOtherInfomationArrayList().get(0).info + "\n");
                    } else {
                        viewOtherTextView.setText("My profile not found");
                    }
                } else {
                    myProfile = new ProfileAccount();
                    viewOtherTextView.setText(myProfile.getMyProfile().getInfo() + "\n" + myProfile.getMyProfile().getName());
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

                if (myProfile.getOtherProfile().size() > 0) {
                    if (myProfile.getOtherProfile().get(spinnerIndex).getOtherInfomationArrayList() != null) {

                        adapter =
                                new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_list_item_1, myProfile.getOtherProfile().get(spinnerIndex).getOtherInfomationArrayList());
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                        listView.setAdapter(adapter);


                        ArrayAdapter<UserInfomation> adapter2 =
                                new ArrayAdapter<UserInfomation>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myProfile.getOtherProfile());
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        viewOtherSpinner.setAdapter(adapter2);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        viewOtherTextView.setText(firebaseUser.getEmail() + "\n" + firebaseAuth.getCurrentUser().getUid() + "\n"
                + databaseReference.child("MyProfile"));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        spinnerIndex = i;

        String returnString = "";

        returnString = "Name: " + myProfile.getOtherProfile().get(i).getName() +
                "\nID: " + myProfile.getOtherProfile().get(i).getInfo();
//         for(int j = 0; j < myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().size();j++){
//
//                returnString = returnString + myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().get(j).name + "\n" +
//                        myProfile.getOtherProfile().get(i).getOtherInfomationArrayList().get(j).info + "\n";
//
//        }

        adapter =
                new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_list_item_1, myProfile.getOtherProfile().get(spinnerIndex).getOtherInfomationArrayList());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        listView.setAdapter(adapter);


        viewOtherTextView.setText(returnString);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listViewIndex = i;


        String returnString = "Selected: " + myProfile.getOtherProfile().get(spinnerIndex).getOtherInfomationArrayList().get(listViewIndex).name +
                "\nAccount ID: " + myProfile.getOtherProfile().get(spinnerIndex).getOtherInfomationArrayList().get(listViewIndex).info;
        viewOtherSelectedProfileTextView.setText(returnString);


    }
}

