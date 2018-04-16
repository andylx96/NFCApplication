package io.github.andylx96.nfcapplication;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private TextView mainMenuID, selectedText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
//    private Button editProfileButton, addOtherButton, viewOtherProfileButton, shareDataButton;
    private Spinner spinner;
    //    UserInfomation userInfomation;
    ProfileAccount myProfile;
    ListView listView;
    FloatingActionButton floatingActionButton;
    private ArrayAdapter<OtherInfomation> adapter2;
    private AdapterView.AdapterContextMenuInfo info;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_view_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
switch (item.getItemId()){
    case R.id.delete:
        myProfile.getMyProfile().getOtherInfomationArrayList().remove(info.position);
//        adapter2.notifyDataSetChanged();

        FirebaseUser user = firebaseAuth.getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());

        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child(user.getUid()).child("MyProfile").setValue(myProfile);

////                start
//
//                databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
//
//



        return true;
    case R.id.edit_id:
Toast.makeText(MainMenuActivity.this,"editClicked",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainMenuActivity.this
        );
        View mView = getLayoutInflater().inflate(R.layout.edit_text_layout,null);
        final EditText mTextName = (EditText)mView.findViewById(R.id.editTextName);
        final EditText mTextInfo = (EditText)mView.findViewById(R.id.editTextInfo);
        Button button = (Button)mView.findViewById(R.id.editTextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenuActivity.this, "working", Toast.LENGTH_SHORT).show();



                myProfile.getMyProfile().getOtherInfomationArrayList().get(info.position).setName(mTextInfo.getText().toString().trim());
                myProfile.getMyProfile().getOtherInfomationArrayList().get(info.position).setInfo(mTextName.getText().toString().trim());
//        adapter2.notifyDataSetChanged();

                FirebaseUser user = firebaseAuth.getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());

                databaseReference = FirebaseDatabase.getInstance().getReference();


                databaseReference.child(user.getUid()).child("MyProfile").setValue(myProfile);



            }
        });


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        return true;
        default:

            return super.onContextItemSelected(item);

}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainMenuID = (TextView) findViewById(R.id.mainMenuID);
        selectedText = (TextView)findViewById(R.id.textView3);
//        editProfileButton = (Button) findViewById(R.id.editProfileButton);
//        spinner = (Spinner) findViewById(R.id.spinner);
//        addOtherButton = (Button) findViewById(R.id.addOtherButton);
//        viewOtherProfileButton = (Button) findViewById(R.id.viewOtherProfileButton);
//        shareDataButton = (Button) findViewById(R.id.shareDataButton);
        listView = (ListView) findViewById(R.id.listView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        listView.setOnItemClickListener(this);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);
setSupportActionBar(toolbar);


registerForContextMenu(listView);

listView.setLongClickable(true);
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(MainMenuActivity.this,"Toast",Toast.LENGTH_SHORT).show();
        return false;
    }
});



        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.edit_profile:
                        startActivity(new Intent(MainMenuActivity.this, EditMyProfileActivity.class));

                        break;
                    case R.id.share_profile:
                        startActivity(new Intent(MainMenuActivity.this, ShareDataActivity.class));

                        break;
                    case R.id.view_other:
                        startActivity(new Intent(MainMenuActivity.this, ViewOtherProfileActivity.class));

                        break;
                }

                return false;
            }
        });



//        editProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainMenuActivity.this, EditMyProfileActivity.class));
//            }
//        });
//
//        viewOtherProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainMenuActivity.this, ViewOtherProfileActivity.class));
//
//            }
//        });
//        shareDataButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainMenuActivity.this, ShareDataActivity.class));
//
//            }
//        });


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());

        databaseReference.child("MyProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


//                userInfomation = dataSnapshot.getValue(UserInfomation.class);

                myProfile = dataSnapshot.getValue(ProfileAccount.class);
                if (myProfile != null) {
                    if (myProfile.getMyProfile() != null) {

                        String returnString = "Name: " + myProfile.getMyProfile().getName() + "\n" + "Info: " + myProfile.getMyProfile().getInfo() + "\n";

                        for (int i = 0; i < myProfile.getMyProfile().getOtherInfomationArrayList().size(); i++) {
                            returnString = returnString + "Name: " + myProfile.getMyProfile().getOtherInfomationArrayList().get(i).name + "\n"
                                    + "Info: " + myProfile.getMyProfile().getOtherInfomationArrayList().get(i).info + "\n";
                        }
//                        mainMenuID.setText(returnString);
                        mainMenuID.setText("Name: "+ myProfile.getMyProfile().getName()+"\nID: "+ myProfile.getMyProfile().getInfo());

                    } else {
                        mainMenuID.setText("My profile not found");
                    }
                } else {
                    myProfile = new ProfileAccount();
                    mainMenuID.setText(myProfile.getMyProfile().getInfo() + "\n" + myProfile.getMyProfile().getName());
                }

                ArrayAdapter<OtherInfomation> adapter =
                        new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myProfile.getMyProfile().getOtherInfomationArrayList());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//                spinner.setAdapter(adapter);


                adapter2 =
                        new ArrayAdapter<OtherInfomation>(getApplicationContext(), android.R.layout.simple_list_item_1, myProfile.getMyProfile().getOtherInfomationArrayList());
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                listView.setAdapter(adapter);

//                mainMenuID.setText(firebaseUser.getEmail() + "\n" + firebaseAuth.getCurrentUser().getUid() + "\n"
//                        + databaseReference.child("MyProfile"));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, AddOtherActivity.class));


            }
        });
//        addOtherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(MainMenuActivity.this, Main4Activity.class));
//
//
//            }
//        });


//        mainMenuID.setText(firebaseUser.getEmail() + "\n" + firebaseAuth.getCurrentUser().getUid() + "\n"
//                + databaseReference.child("MyProfile"));

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
selectedText.setText("Selected: "+ myProfile.getMyProfile().getOtherInfomationArrayList().get(i).name
+"\nAccount ID: " + myProfile.getMyProfile().getOtherInfomationArrayList().get(i).info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_main_menu, menu);


//        switch (menu.)

        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        drawer.closeDrawer(GravityCompat.START);
////        return true;
////    }

}
