package io.github.andylx96.nfcapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMyProfileActivity extends AppCompatActivity {
    private EditText editName, editInfo;
    private Button editSaveButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProfileAccount myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        editName = (EditText) findViewById(R.id.editFullName);
//        editInfo = (EditText) findViewById(R.id.editInfo);
        editSaveButton = (Button) findViewById(R.id.editSaveButton);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editName.getText().toString().trim();
//                String info = editInfo.getText().toString().trim();




//                UserInfomation userInfomation =
//                ProfileAccount profileAccount = new ProfileAccount("test");
//                profileAccount.setMyProfile(new UserInfomation(name, firebaseAuth.getCurrentUser().getUid()));

                final FirebaseUser user = firebaseAuth.getCurrentUser();


                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());

                databaseReference.child("MyProfile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        myProfile = dataSnapshot.getValue(ProfileAccount.class);
                        if (myProfile != null) {
                            if (myProfile.getOtherProfile() != null) {

                                myProfile.getMyProfile().setName(name);
//                                String returnString ="";
                                myProfile.getMyProfile().setInfo(user.getUid());
//                                databaseReference.setValue(myProfile);

                            }
                        } else {

                            myProfile = new ProfileAccount();
                            myProfile.getMyProfile().setName(name);

//                            databaseReference.setValue(myProfile);

                        }

                        databaseReference = FirebaseDatabase.getInstance().getReference();
//                        myProfileAccount.getOtherProfile().add(userInfomation);


                        databaseReference.child(user.getUid()).child("MyProfile").setValue(myProfile);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });




                Toast.makeText(EditMyProfileActivity.this,"infosaved",Toast.LENGTH_LONG).show();

            }
        });

    }
}
