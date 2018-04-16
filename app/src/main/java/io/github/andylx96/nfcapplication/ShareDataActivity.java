package io.github.andylx96.nfcapplication;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ShareDataActivity extends Activity implements
        NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

//    TextView shareDataTextView;
//    EditText textOut;

    NfcAdapter nfcAdapter;
    //    Button button;
    NdefMessage ndefMessageout;
    UserInfomation userInfomation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);
//        shareDataTextView = (TextView) findViewById(R.id.shareDataTextView);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(ShareDataActivity.this,
                    "nfcAdapter==null, no NFC adapter exists",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ShareDataActivity.this,
                    "Set Callback(s)",
                    Toast.LENGTH_LONG).show();
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
                Parcelable[] parcelables =
                        intent.getParcelableArrayExtra(
                                NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
                NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
                NdefRecord NdefRecord_0 = inNdefRecords[0];
                String inMsg = new String(NdefRecord_0.getPayload());
//                shareDataTextView.setText(inMsg);


                FirebaseAuth firebaseAuth;
                DatabaseReference databaseReference;
                firebaseAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());


//

                Gson gson = new Gson();

                userInfomation = gson.fromJson(inMsg, UserInfomation.class);
//                shareDataTextView.setText(userInfomation.getName() + "\n" + userInfomation.getInfo());

//            textInfo.setText(inMsg);


                databaseReference.child("MyProfile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FirebaseAuth firebaseAuth;
                        DatabaseReference databaseReference;
                        firebaseAuth = FirebaseAuth.getInstance();
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser user = firebaseAuth.getCurrentUser();


                        ProfileAccount myProfileAccount = dataSnapshot.getValue(ProfileAccount.class);


                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        myProfileAccount.getOtherProfile().add(userInfomation);


                        databaseReference.child(user.getUid()).child("MyProfile").setValue(myProfileAccount);


                        Toast.makeText(ShareDataActivity.this, "infoRec", Toast.LENGTH_LONG).show();
//finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            else{
                Toast.makeText(ShareDataActivity.this, "No Action", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
            }
        });
        finish();

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {


        final Gson gson = new Gson();
        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());


        databaseReference.child("MyProfile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseAuth firebaseAuth;
                DatabaseReference databaseReference;
                firebaseAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = firebaseAuth.getCurrentUser();


                ProfileAccount myProfileAccount = dataSnapshot.getValue(ProfileAccount.class);


                gson.toJson(myProfileAccount.getMyProfile()
                );
                String jsonInString = gson.toJson(myProfileAccount.getMyProfile()
                );

                byte[] bytesOut = jsonInString.getBytes();


                NdefRecord ndefRecordOut = new NdefRecord(
                        NdefRecord.TNF_MIME_MEDIA,
                        "text/plain".getBytes(),
                        new byte[]{},
                        bytesOut);

                ndefMessageout = new NdefMessage(ndefRecordOut);

                Toast.makeText(ShareDataActivity.this, "infosaved", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        String stringOut = "HI";
//        byte[] bytesOut = stringOut.getBytes();
//
//        NdefRecord ndefRecordOut = new NdefRecord(
//                NdefRecord.TNF_MIME_MEDIA,
//                "text/plain".getBytes(),
//                new byte[] {},
//                bytesOut);
////
//        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);

//        Toast.makeText(ShareDataActivity.this, "infosaved", Toast.LENGTH_LONG).show();

        return ndefMessageout;

    }

}


//New Test

//
//package io.github.andylx96.nfcapplication;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.nfc.NdefMessage;
//import android.nfc.NdefRecord;
//import android.nfc.NfcAdapter;
//import android.nfc.NfcAdapter.CreateNdefMessageCallback;
//import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
//import android.nfc.NfcEvent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//
//public class ShareDataActivity extends Activity implements
//        CreateNdefMessageCallback, OnNdefPushCompleteCallback {
//
//
////    TextView textInfo;
////    EditText textOut;
//
//    //    NfcAdapter nfcAdapter;
////    Button button;
//    FirebaseAuth user;
//
//    TextView shareDataTextView;
//
//    NfcAdapter nfcAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share_data);
//        shareDataTextView = (TextView) findViewById(R.id.shareDataTextView);
//
//
//        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if (nfcAdapter == null) {
//            Toast.makeText(ShareDataActivity.this,
//                    "nfcAdapter==null, no NFC adapter exists",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(ShareDataActivity.this,
//                    "Set Callback(s)",
//                    Toast.LENGTH_LONG).show();
//            nfcAdapter.setNdefPushMessageCallback(this, this);
//            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
//        }
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        if (action != null) {
//            if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
//                Parcelable[] parcelables =
//                        intent.getParcelableArrayExtra(
//                                NfcAdapter.EXTRA_NDEF_MESSAGES);
//                NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
//                NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
//                NdefRecord NdefRecord_0 = inNdefRecords[0];
//                String inMsg = new String(NdefRecord_0.getPayload());
//                shareDataTextView.setText(inMsg);
//
//
//                Gson gson = new Gson();
//
//                ProfileAccount profileAccount = gson.fromJson(inMsg, ProfileAccount.class);
//                shareDataTextView.setText(profileAccount.getMyProfile().getName() + "\n" + profileAccount.getMyProfile().getInfo());
//
////            textInfo.setText(inMsg);
//
//
//            }
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//    }
//
//    @Override
//    public void onNdefPushComplete(NfcEvent event) {
//
//        final String eventString = "onNdefPushComplete\n" + event.toString();
//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(),
//                        eventString,
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//
//
////        Gson gson;
////        JSONObject
//        Gson gson = new Gson();
//        ProfileAccount profileAccount = new ProfileAccount();
//        gson.toJson(profileAccount);
//        String jsonInString = gson.toJson(profileAccount);
//
//        byte[] bytesOut = jsonInString.getBytes();
//
//
////        String stringOut = textOut.getText().toString();
////        byte[] bytesOut = stringOut.getBytes();
//
//        NdefRecord ndefRecordOut = new NdefRecord(
//                NdefRecord.TNF_MIME_MEDIA,
//                "text/plain".getBytes(),
//                new byte[]{},
//                bytesOut);
//
//        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
//        return ndefMessageout;
//
//    }
//
//}