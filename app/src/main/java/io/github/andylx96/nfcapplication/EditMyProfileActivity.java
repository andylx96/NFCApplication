package io.github.andylx96.nfcapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditMyProfileActivity extends AppCompatActivity {
    private EditText editName, editInfo;
    private Button editSaveButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        editName = (EditText) findViewById(R.id.editFullName);
        editInfo = (EditText) findViewById(R.id.editInfo);
        editSaveButton = (Button) findViewById(R.id.editSaveButton);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String info = editInfo.getText().toString().trim();
                UserInfomation userInfomation = new UserInfomation(name, info);
                FirebaseUser user = firebaseAuth.getCurrentUser();

                databaseReference.child(user.getUid()).child("MyProfile").setValue(userInfomation);
                Toast.makeText(EditMyProfileActivity.this,"infosaved",Toast.LENGTH_LONG).show();

            }
        });

    }
}
