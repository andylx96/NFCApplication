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

public class AddOtherActivity extends AppCompatActivity {
    private Button addOtherButton2;
    private EditText addOtherName2, addOtherInfo2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other);
        addOtherButton2 = (Button)findViewById(R.id.addOtherButton2);
        addOtherName2 = (EditText)findViewById(R.id.addOtherName2);
        addOtherInfo2 = (EditText)findViewById(R.id.addOtherInfo2);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        addOtherButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =                addOtherName2.getText().toString().trim();
                String info = addOtherInfo2.getText().toString().trim();

                UserInfomation userInfomation = new UserInfomation(name, info);
                userInfomation.getOtherInfomationArrayList().add(new OtherInfomation(name,info));

                FirebaseUser user = firebaseAuth.getCurrentUser();


//                databaseReference.child(user.getUid()).child("MyProfile").setValue(userInfomation);
                databaseReference.child(user.getUid()).child("MyProfile").setValue(userInfomation);



                Toast.makeText(AddOtherActivity.this,"infosaved",Toast.LENGTH_LONG).show();



            }
        });

    }
}
